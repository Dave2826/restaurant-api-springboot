package com.david.restaurantapi.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador encargado de la autenticacion
 * de usuarios en el sistema.
 *
 * @author David Morales Guerrero
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Operaciones de autenticación y generación de tokens JWT")
// REST Endpoint
public class AuthController {

    // Dependency Injection
    private final AuthService authService;
    private final FirebaseLoginService firebaseLoginService;

    public AuthController(AuthService authService, FirebaseLoginService firebaseLoginService) {
        this.authService = authService;
        this.firebaseLoginService = firebaseLoginService;
    }

    /**
     * Inicia sesion y devuelve un token JWT.
     *
     * @param request credenciales del usuario
     * @return token JWT si las credenciales son correctas
     */
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario mediante sus credenciales y genera un token JWT para acceder a recursos protegidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa, token JWT generado"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    // HTTP POST
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        Map<String, String> response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Inicia sesion con Firebase Authentication y devuelve el JWT del backend.
     *
     * @param request ID Token emitido por Firebase en el cliente
     * @return token JWT propio, con la misma forma que {@code /auth/login}
     */
    @Operation(
            summary = "Iniciar sesión con Firebase",
            description = "Verifica el ID Token de Firebase, aprovisiona al usuario en el backend "
                    + "si es su primer acceso y genera un token JWT propio para acceder a la API.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa, token JWT generado"),
        @ApiResponse(responseCode = "401", description = "ID Token inválido o expirado"),
        @ApiResponse(responseCode = "403", description = "Dominio no institucional o correo sin verificar"),
        @ApiResponse(responseCode = "503", description = "Integración con Firebase no habilitada")
    })
    @PostMapping("/firebase")
    public ResponseEntity<Map<String, String>> loginWithFirebase(
            @RequestBody FirebaseLoginRequest request) {
        Map<String, String> response = firebaseLoginService.login(request);
        return ResponseEntity.ok(response);
    }
}
