package com.david.restaurantapi.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Verifica ID Tokens emitidos por Firebase Authentication y aplica la politica
 * de acceso institucional.
 *
 * La identidad del usuario (uid, correo) se obtiene exclusivamente del token
 * ya verificado por el SDK oficial; nunca de datos enviados por el cliente.
 * El rol tampoco viaja en el token de Firebase: se decide en el backend.
 *
 * @author David Morales Guerrero
 */
@Component
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class FirebaseTokenVerifier {

    private final FirebaseAuth firebaseAuth;
    private final String allowedDomain;
    private final boolean requireVerifiedEmail;

    /**
     * @param firebaseAuth         servicio de Firebase Auth
     * @param allowedDomain        dominio institucional permitido (con o sin @)
     * @param requireVerifiedEmail exige que el correo este verificado
     */
    public FirebaseTokenVerifier(FirebaseAuth firebaseAuth,
            @Value("${firebase.allowed-domain}") String allowedDomain,
            @Value("${firebase.require-verified-email:true}") boolean requireVerifiedEmail) {
        this.firebaseAuth = firebaseAuth;
        this.allowedDomain = normalizeDomain(allowedDomain);
        this.requireVerifiedEmail = requireVerifiedEmail;
    }

    /**
     * Valida criptograficamente el ID Token y su politica de acceso.
     *
     * @param idToken token recibido del cliente
     * @return identidad verificada, lista para emitir el JWT propio
     * @throws TokenRejectedException si el token es invalido o no cumple la
     *                                politica institucional
     */
    public FirebaseIdentity verify(String idToken) {
        if (idToken == null || idToken.isBlank()) {
            throw new TokenRejectedException(Reason.INVALID_TOKEN,
                    "Token de Firebase ausente");
        }

        FirebaseToken token;
        try {
            // checkRevoked = true: rechaza ademas los tokens revocados, no solo
            // los expirados o mal firmados.
            token = firebaseAuth.verifyIdToken(idToken, true);
        } catch (FirebaseAuthException | IllegalArgumentException exception) {
            // No se propaga el detalle ni se registra el token en logs.
            throw new TokenRejectedException(Reason.INVALID_TOKEN,
                    "Token de Firebase invalido");
        }

        String email = normalizeEmail(token.getEmail());
        boolean emailVerified = token.isEmailVerified();

        if (!belongsToAllowedDomain(email)) {
            throw new TokenRejectedException(Reason.DOMAIN_NOT_ALLOWED,
                    "El correo no pertenece al dominio institucional");
        }

        if (requireVerifiedEmail && !emailVerified) {
            throw new TokenRejectedException(Reason.EMAIL_NOT_VERIFIED,
                    "El correo de la cuenta no esta verificado");
        }

        return new FirebaseIdentity(token.getUid(), email, emailVerified);
    }

    private boolean belongsToAllowedDomain(String email) {
        return !allowedDomain.isEmpty() && email.endsWith("@" + allowedDomain);
    }

    private static String normalizeDomain(String domain) {
        if (domain == null) {
            return "";
        }
        String trimmed = domain.trim().toLowerCase(Locale.ROOT);
        return trimmed.startsWith("@") ? trimmed.substring(1) : trimmed;
    }

    private static String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * Datos de identidad derivados del token verificado.
     *
     * @param uid           identificador estable de Firebase
     * @param email         correo normalizado en minusculas
     * @param emailVerified indica si Firebase considera el correo verificado
     */
    public record FirebaseIdentity(String uid, String email, boolean emailVerified) {
    }

    /** Motivos por los que se rechaza un token. */
    public enum Reason {
        INVALID_TOKEN,
        DOMAIN_NOT_ALLOWED,
        EMAIL_NOT_VERIFIED
    }

    /**
     * Rechazo de un ID Token. El mensaje es apto para el cliente: no incluye
     * el token ni detalles internos de la verificacion.
     */
    public static class TokenRejectedException extends RuntimeException {

        private final Reason reason;

        public TokenRejectedException(Reason reason, String message) {
            super(message);
            this.reason = reason;
        }

        public Reason getReason() {
            return reason;
        }
    }
}
