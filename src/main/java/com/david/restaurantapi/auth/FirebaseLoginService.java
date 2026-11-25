package com.david.restaurantapi.auth;

import com.david.restaurantapi.auth.FirebaseTokenVerifier.FirebaseIdentity;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.Reason;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.TokenRejectedException;
import com.david.restaurantapi.entity.Usuario;
import com.david.restaurantapi.repository.UsuarioRepository;
import com.david.restaurantapi.security.JwtUtil;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Puente entre Firebase Authentication y el JWT propio del backend.
 *
 * Firebase es la autoridad de identidad y de contraseñas. Spring Boot verifica
 * el ID Token con el SDK oficial y, si el usuario todavía no existe en la tabla
 * {@code users}, lo aprovisiona con el UID de Firebase como {@code username}.
 *
 * Nunca se guarda en PostgreSQL la contraseña de Firebase: el registro local
 * recibe una contraseña aleatoria inservible que impide el login legado por
 * username/password para esas cuentas. El rol se asigna siempre en el servidor.
 *
 * @author David Morales Guerrero
 */
@Service
public class FirebaseLoginService {

    /** Rol concedido a toda cuenta provisionada desde Firebase. */
    static final String PROVISIONED_ROLE = "USER";

    /** Mensaje devuelto cuando la integración con Firebase está deshabilitada. */
    static final String FIREBASE_DISABLED_MESSAGE =
            "La autenticación con Firebase no está habilitada en el servidor";

    private final ObjectProvider<FirebaseTokenVerifier> verifierProvider;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final SecureRandom secureRandom = new SecureRandom();

    public FirebaseLoginService(ObjectProvider<FirebaseTokenVerifier> verifierProvider,
                                UsuarioRepository usuarioRepository,
                                PasswordEncoder passwordEncoder,
                                JwtUtil jwtUtil) {
        this.verifierProvider = verifierProvider;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Autentica al usuario a partir de su ID Token de Firebase y emite el JWT
     * propio que exige el resto de la API.
     *
     * @param request DTO con el ID Token
     * @return mapa con la clave {@code token}, igual que {@code /auth/login}
     * @throws TokenRejectedException      si el token es inválido o no cumple la
     *                                     política institucional
     * @throws FirebaseUnavailableException si la integración está deshabilitada
     */
    public Map<String, String> login(FirebaseLoginRequest request) {
        if (request == null || request.getIdToken() == null || request.getIdToken().isBlank()) {
            throw new TokenRejectedException(Reason.INVALID_TOKEN, "Token de Firebase ausente");
        }

        FirebaseTokenVerifier verifier = verifierProvider.getIfAvailable();

        if (verifier == null) {
            throw new FirebaseUnavailableException(FIREBASE_DISABLED_MESSAGE);
        }

        FirebaseIdentity identity = verifier.verify(request.getIdToken());

        Usuario usuario = findOrProvision(identity);

        return Map.of("token", jwtUtil.generateToken(usuario.getUsername()));
    }

    /**
     * Devuelve el usuario asociado al UID de Firebase o lo crea si es su
     * primera autenticación.
     */
    private Usuario findOrProvision(FirebaseIdentity identity) {
        return usuarioRepository.findByUsername(identity.uid())
                .orElseGet(() -> provision(identity));
    }

    private Usuario provision(FirebaseIdentity identity) {
        Usuario usuario = new Usuario();
        usuario.setUsername(identity.uid());
        usuario.setPassword(passwordEncoder.encode(randomPassword()));
        usuario.setRole(PROVISIONED_ROLE);

        try {
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException conflict) {
            // Otra petición simultánea creó la misma fila: se reutiliza.
            return usuarioRepository.findByUsername(identity.uid())
                    .orElseThrow(() -> conflict);
        }
    }

    /**
     * Contraseña aleatoria inutilizable: cumple la restricción NOT NULL del
     * esquema sin almacenar ni replicar la contraseña real de Firebase.
     */
    private String randomPassword() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * La integración con Firebase Admin no está disponible en el servidor
     * (configuración deshabilitada o credenciales ausentes).
     */
    public static class FirebaseUnavailableException extends RuntimeException {

        public FirebaseUnavailableException(String message) {
            super(message);
        }
    }
}
