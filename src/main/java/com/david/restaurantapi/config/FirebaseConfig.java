package com.david.restaurantapi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configura el SDK de Firebase Admin para verificar ID Tokens.
 *
 * La configuracion esta desactivada por defecto (`firebase.enabled=false`), de
 * modo que el backend arranca y el login legado funciona sin credenciales de
 * Firebase. Las credenciales se resuelven con Google Application Default
 * Credentials (variable GOOGLE_APPLICATION_CREDENTIALS), nunca con un archivo
 * incluido en el repositorio o en la aplicacion.
 *
 * @author David Morales Guerrero
 */
@Configuration
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class FirebaseConfig {

    /**
     * Serializa la inicializacion: dos creaciones de contexto consecutivas
     * (por ejemplo en tests) no deben registrar la misma App dos veces.
     */
    private static final Object INIT_LOCK = new Object();

    /**
     * Devuelve la App por defecto ya registrada o crea una nueva.
     *
     * `destroyMethod = ""` desactiva el cierre inferido por Spring: la App es
     * un singleton de proceso y debe sobrevivir a la destruccion del contexto.
     *
     * @param projectId proyecto Firebase que debe coincidir con el emisor de
     *                  los ID Tokens recibidos
     * @return instancia por defecto de FirebaseApp
     * @throws IOException si no hay credenciales disponibles (ADC)
     */
    @Bean(destroyMethod = "")
    public FirebaseApp firebaseApp(@Value("${firebase.project-id}") String projectId)
            throws IOException {
        synchronized (INIT_LOCK) {
            for (FirebaseApp existing : FirebaseApp.getApps()) {
                if (FirebaseApp.DEFAULT_APP_NAME.equals(existing.getName())) {
                    return existing;
                }
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setProjectId(projectId)
                    .build();

            return FirebaseApp.initializeApp(options);
        }
    }

    /**
     * Expone el servicio de autenticacion de Firebase asociado a la App.
     *
     * @param firebaseApp App inicializada previamente
     * @return FirebaseAuth de la App por defecto
     */
    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
