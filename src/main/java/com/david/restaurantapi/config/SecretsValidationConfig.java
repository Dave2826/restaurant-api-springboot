package com.david.restaurantapi.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Valida que las credenciales obligatorias de PostgreSQL esten definidas y no
 * vacias antes de que se inicialice el DataSource.
 *
 * Se implementa como BeanFactoryPostProcessor estatico para que la validacion
 * ocurra durante el arranque, antes de crear los beans singleton, y produzca un
 * error claro en lugar de un fallo de autenticacion de base de datos.
 *
 * @author David Morales Guerrero
 */
@Configuration
public class SecretsValidationConfig {

    private static final String USERNAME_PROPERTY = "DB_USERNAME";
    private static final String PASSWORD_PROPERTY = "DB_PASSWORD";

    @Bean
    public static BeanFactoryPostProcessor requiredSecretsValidator(Environment environment) {
        return beanFactory -> {
            requireNonBlank(environment, USERNAME_PROPERTY);
            requireNonBlank(environment, PASSWORD_PROPERTY);
        };
    }

    private static void requireNonBlank(Environment environment, String name) {
        String value = environment.getProperty(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(name + " must be defined and non-empty");
        }
    }
}
