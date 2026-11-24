package com.david.restaurantapi.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.david.restaurantapi.auth.FirebaseTokenVerifier.FirebaseIdentity;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.Reason;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.TokenRejectedException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias de la politica de {@link FirebaseTokenVerifier}.
 *
 * Estas pruebas usan FirebaseAuth y FirebaseToken simulados: validan la
 * politica de dominio, la exigencia de correo verificado, el rechazo de
 * tokens y el uso de la comprobacion de revocacion. No demuestran la
 * verificacion criptografica real de Firebase, que solo se puede comprobar
 * contra el SDK con credenciales reales.
 */
class FirebaseTokenVerifierTest {

    private static final String DOMAIN = "tecdesoftware.edu.mx";
    private static final String TOKEN = "id-token-de-prueba";

    private FirebaseAuth firebaseAuth;
    private FirebaseTokenVerifier verifier;

    @BeforeEach
    void setUp() {
        firebaseAuth = mock(FirebaseAuth.class);
        verifier = new FirebaseTokenVerifier(firebaseAuth, DOMAIN, true);
    }

    private FirebaseToken token(String uid, String email, boolean emailVerified) {
        FirebaseToken token = mock(FirebaseToken.class);
        when(token.getUid()).thenReturn(uid);
        when(token.getEmail()).thenReturn(email);
        when(token.isEmailVerified()).thenReturn(emailVerified);
        return token;
    }

    private TokenRejectedException rejection(String idToken) {
        return catchThrowableOfType(
                () -> verifier.verify(idToken), TokenRejectedException.class);
    }

    @Test
    @DisplayName("acepta un token del dominio institucional con correo verificado")
    void acceptsInstitutionalVerifiedUser() throws Exception {
        when(firebaseAuth.verifyIdToken(TOKEN, true)).thenReturn(
                token("uid-123", "david.morales@tecdesoftware.edu.mx", true));

        FirebaseIdentity identity = verifier.verify(TOKEN);

        assertThat(identity.uid()).isEqualTo("uid-123");
        assertThat(identity.email()).isEqualTo("david.morales@tecdesoftware.edu.mx");
        assertThat(identity.emailVerified()).isTrue();
    }

    @Test
    @DisplayName("verifica el token con comprobacion de revocacion activa")
    void verifiesTokenWithRevocationCheck() throws Exception {
        when(firebaseAuth.verifyIdToken(TOKEN, true)).thenReturn(
                token("uid-123", "david.morales@tecdesoftware.edu.mx", true));

        verifier.verify(TOKEN);

        verify(firebaseAuth).verifyIdToken(TOKEN, true);
    }

    @Test
    @DisplayName("normaliza el correo a minusculas antes de evaluar el dominio")
    void normalizesEmailBeforeDomainCheck() throws Exception {
        when(firebaseAuth.verifyIdToken(TOKEN, true)).thenReturn(
                token("uid-123", "  David.Morales@TecDeSoftware.edu.MX ", true));

        FirebaseIdentity identity = verifier.verify(TOKEN);

        assertThat(identity.email()).isEqualTo("david.morales@tecdesoftware.edu.mx");
    }

    @Test
    @DisplayName("rechaza un correo de otro dominio")
    void rejectsForeignDomain() throws Exception {
        when(firebaseAuth.verifyIdToken(TOKEN, true)).thenReturn(
                token("uid-1", "alguien@gmail.com", true));

        TokenRejectedException exception = rejection(TOKEN);

        assertThat(exception.getReason()).isEqualTo(Reason.DOMAIN_NOT_ALLOWED);
    }

    @Test
    @DisplayName("rechaza un token sin correo asociado")
    void rejectsTokenWithoutEmail() throws Exception {
        when(firebaseAuth.verifyIdToken(TOKEN, true)).thenReturn(
                token("uid-1", null, true));

        TokenRejectedException exception = rejection(TOKEN);

        assertThat(exception.getReason()).isEqualTo(Reason.DOMAIN_NOT_ALLOWED);
    }

    @Test
    @DisplayName("rechaza un correo institucional sin verificar")
    void rejectsUnverifiedEmail() throws Exception {
        when(firebaseAuth.verifyIdToken(TOKEN, true)).thenReturn(
                token("uid-1", "david.morales@tecdesoftware.edu.mx", false));

        TokenRejectedException exception = rejection(TOKEN);

        assertThat(exception.getReason()).isEqualTo(Reason.EMAIL_NOT_VERIFIED);
    }

    @Test
    @DisplayName("rechaza el token cuando Firebase Auth lanza una excepcion")
    void rejectsTokenWhenFirebaseRejectsIt() throws Exception {
        when(firebaseAuth.verifyIdToken(anyString(), anyBoolean()))
                .thenThrow(mock(FirebaseAuthException.class));

        TokenRejectedException exception = rejection(TOKEN);

        assertThat(exception.getReason()).isEqualTo(Reason.INVALID_TOKEN);
    }

    @Test
    @DisplayName("rechaza un token vacio sin consultar Firebase")
    void rejectsBlankTokenWithoutCallingFirebase() {
        TokenRejectedException exception = rejection("   ");

        assertThat(exception.getReason()).isEqualTo(Reason.INVALID_TOKEN);
        verifyNoInteractions(firebaseAuth);
    }

    @Test
    @DisplayName("permite correo sin verificar cuando la politica lo desactiva")
    void allowsUnverifiedEmailWhenPolicyDisabled() throws Exception {
        FirebaseTokenVerifier permissive =
                new FirebaseTokenVerifier(firebaseAuth, "@tecdesoftware.edu.mx", false);
        when(firebaseAuth.verifyIdToken(TOKEN, true)).thenReturn(
                token("uid-1", "david.morales@tecdesoftware.edu.mx", false));

        FirebaseIdentity identity = permissive.verify(TOKEN);

        assertThat(identity.emailVerified()).isFalse();
    }
}
