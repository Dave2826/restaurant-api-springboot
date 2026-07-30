package com.david.restaurantapi.auth;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO que contiene las credenciales
 * de inicio de sesion del usuario.
 *
 * @author David Morales Guerrero
 */
public class LoginRequest {

    @Schema(description = "Usuario registrado en el sistema", example = "admin")
    private String username;

    @Schema(description = "Contraseña del usuario", example = "1234")
    private String password;

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
