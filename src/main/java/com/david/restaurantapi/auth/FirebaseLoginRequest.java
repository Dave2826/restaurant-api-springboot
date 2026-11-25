package com.david.restaurantapi.auth;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO con el ID Token emitido por Firebase Authentication.
 *
 * El cliente no envía identidad ni rol: solo el token, que el backend verifica
 * criptográficamente antes de confiar en cualquier dato.
 *
 * @author David Morales Guerrero
 */
public class FirebaseLoginRequest {

    @Schema(
            description = "ID Token de Firebase obtenido tras el inicio de sesión en el cliente",
            example = "eyJhbGciOiJSUzI1NiIsImtpZCI6...")
    private String idToken;

    public FirebaseLoginRequest() {
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
