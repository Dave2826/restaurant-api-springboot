package com.david.restaurantapi.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.david.restaurantapi.auth.FirebaseLoginService.FirebaseUnavailableException;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.Reason;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.TokenRejectedException;
import com.david.restaurantapi.exception.GlobalExceptionHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Pruebas del contrato HTTP de {@code POST /auth/firebase}: ruta, cuerpo,
 * forma de la respuesta y traduccion de errores a codigos HTTP.
 *
 * El servicio esta simulado; el controlador y el manejador global de
 * excepciones son los reales. Las peticiones piden JSON explicitamente, igual
 * que el cliente Flutter, porque el proyecto tambien registra un conversor XML.
 */
class AuthControllerFirebaseTest {

    private FirebaseLoginService firebaseLoginService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        firebaseLoginService = mock(FirebaseLoginService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(mock(AuthService.class), firebaseLoginService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("POST /auth/firebase devuelve el JWT del backend")
    void returnsBackendJwt() throws Exception {
        when(firebaseLoginService.login(any(FirebaseLoginRequest.class)))
                .thenReturn(Map.of("token", "jwt-backend"));

        mockMvc.perform(post("/auth/firebase")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idToken\":\"id-token\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-backend"));
    }

    @Test
    @DisplayName("POST /auth/firebase responde 503 si Firebase no está habilitado")
    void returnsServiceUnavailable() throws Exception {
        when(firebaseLoginService.login(any(FirebaseLoginRequest.class)))
                .thenThrow(new FirebaseUnavailableException(
                        FirebaseLoginService.FIREBASE_DISABLED_MESSAGE));

        mockMvc.perform(post("/auth/firebase")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idToken\":\"id-token\"}"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message")
                        .value(FirebaseLoginService.FIREBASE_DISABLED_MESSAGE));
    }

    @Test
    @DisplayName("POST /auth/firebase responde 403 ante un correo sin verificar")
    void returnsForbiddenOnPolicyRejection() throws Exception {
        when(firebaseLoginService.login(any(FirebaseLoginRequest.class)))
                .thenThrow(new TokenRejectedException(
                        Reason.EMAIL_NOT_VERIFIED, "El correo de la cuenta no esta verificado"));

        mockMvc.perform(post("/auth/firebase")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idToken\":\"id-token\"}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message")
                        .value("El correo de la cuenta no esta verificado"));
    }
}
