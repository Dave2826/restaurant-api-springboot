package com.david.restaurantapi.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
// REST Endpoint
public class AuthController {

    // Dependency Injection
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
}
