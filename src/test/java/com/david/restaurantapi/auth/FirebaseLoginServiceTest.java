package com.david.restaurantapi.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.david.restaurantapi.auth.FirebaseLoginService.FirebaseUnavailableException;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.FirebaseIdentity;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.Reason;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.TokenRejectedException;
import com.david.restaurantapi.entity.Usuario;
import com.david.restaurantapi.repository.UsuarioRepository;
import com.david.restaurantapi.security.JwtUtil;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Pruebas unitarias del puente Firebase → JWT.
 *
 * FirebaseTokenVerifier, el repositorio, el codificador y JwtUtil están
 * simulados: se valida el contrato del servicio (aprovisionamiento, rol,
 * reutilización de usuarios y respuestas), no la verificación criptográfica
 * real de Firebase.
 */
class FirebaseLoginServiceTest {

    private static final String TOKEN = "id-token-de-prueba";
    private static final String UID = "uid-firebase-123";
    private static final String EMAIL = "david.morales@tecdesoftware.edu.mx";
    private static final String BACKEND_JWT = "jwt-del-backend";

    private FirebaseTokenVerifier verifier;
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private FirebaseLoginService service;

    @BeforeEach
    void setUp() {
        verifier = mock(FirebaseTokenVerifier.class);
        usuarioRepository = mock(UsuarioRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtil = mock(JwtUtil.class);

        @SuppressWarnings("unchecked")
        ObjectProvider<FirebaseTokenVerifier> provider = mock(ObjectProvider.class);
        when(provider.getIfAvailable()).thenReturn(verifier);

        service = new FirebaseLoginService(provider, usuarioRepository, passwordEncoder, jwtUtil);
    }

    private FirebaseLoginRequest request(String idToken) {
        FirebaseLoginRequest request = new FirebaseLoginRequest();
        request.setIdToken(idToken);
        return request;
    }

    @Test
    @DisplayName("crea el usuario con el UID de Firebase y rol USER en el primer acceso")
    void provisionsUserOnFirstLogin() {
        when(verifier.verify(TOKEN)).thenReturn(new FirebaseIdentity(UID, EMAIL, true));
        when(usuarioRepository.findByUsername(UID)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$hash-inutilizable");
        when(jwtUtil.generateToken(UID)).thenReturn(BACKEND_JWT);
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, String> response = service.login(request(TOKEN));

        assertThat(response).containsEntry("token", BACKEND_JWT);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        Usuario guardado = captor.getValue();

        assertThat(guardado.getUsername()).isEqualTo(UID);
        assertThat(guardado.getRole()).isEqualTo("USER");
        assertThat(guardado.getPassword()).isEqualTo("$2a$10$hash-inutilizable");
        assertThat(guardado.getPassword()).isNotEqualTo(EMAIL);
    }

    @Test
    @DisplayName("no duplica usuarios existentes y reutiliza la fila")
    void reusesExistingUser() {
        Usuario existente = new Usuario();
        existente.setUsername(UID);
        existente.setRole("USER");

        when(verifier.verify(TOKEN)).thenReturn(new FirebaseIdentity(UID, EMAIL, true));
        when(usuarioRepository.findByUsername(UID)).thenReturn(Optional.of(existente));
        when(jwtUtil.generateToken(UID)).thenReturn(BACKEND_JWT);

        Map<String, String> response = service.login(request(TOKEN));

        assertThat(response).containsEntry("token", BACKEND_JWT);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("responde 503 cuando la integración con Firebase está deshabilitada")
    void failsWhenFirebaseDisabled() {
        @SuppressWarnings("unchecked")
        ObjectProvider<FirebaseTokenVerifier> provider = mock(ObjectProvider.class);
        when(provider.getIfAvailable()).thenReturn(null);

        FirebaseLoginService servicioSinFirebase =
                new FirebaseLoginService(provider, usuarioRepository, passwordEncoder, jwtUtil);

        FirebaseUnavailableException exception = catchThrowableOfType(
                () -> servicioSinFirebase.login(request(TOKEN)),
                FirebaseUnavailableException.class);

        assertThat(exception).hasMessageContaining("Firebase");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("rechaza un token vacío antes de consultar Firebase")
    void rejectsBlankToken() {
        TokenRejectedException exception =
                catchThrowableOfType(() -> service.login(request("  ")), TokenRejectedException.class);

        assertThat(exception.getReason()).isEqualTo(Reason.INVALID_TOKEN);
        verify(verifier, never()).verify(anyString());
    }

    @Test
    @DisplayName("propaga el rechazo de política sin crear usuarios")
    void propagatesPolicyRejection() {
        when(verifier.verify(TOKEN)).thenThrow(
                new TokenRejectedException(Reason.EMAIL_NOT_VERIFIED, "Correo sin verificar"));

        TokenRejectedException exception =
                catchThrowableOfType(() -> service.login(request(TOKEN)), TokenRejectedException.class);

        assertThat(exception.getReason()).isEqualTo(Reason.EMAIL_NOT_VERIFIED);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
