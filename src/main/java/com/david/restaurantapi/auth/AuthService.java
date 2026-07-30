package com.david.restaurantapi.auth;

import com.david.restaurantapi.security.JwtUtil;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de la autenticacion
 * de usuarios y generacion de tokens JWT.
 *
 * @author David Morales Guerrero
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Autentica al usuario con las credenciales proporcionadas
     * y genera un token JWT.
     *
     * @param request credenciales de inicio de sesion
     * @return mapa con el token JWT generado
     */
    public Map<String, String> login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        String token = jwtUtil.generateToken(request.getUsername());
        return Map.of("token", token);
    }
}
