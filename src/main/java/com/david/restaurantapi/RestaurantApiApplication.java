package com.david.restaurantapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicaci&oacute;n Restaurant API.
 * <p>
 * Esta clase inicia el contexto de Spring Boot, habilitando la
 * auto-configuraci&oacute;n, el escaneo de componentes y la vinculaci&oacute;n
 * de propiedades para todo el proyecto.
 * </p>
 *
 * @author David
 * @version 1.0
 */
@SpringBootApplication
public class RestaurantApiApplication {

    // ----------------------------------------------------
    // M&eacute;todo principal
    // ----------------------------------------------------

    /**
     * M&eacute;todo principal que arranca la aplicaci&oacute;n Spring Boot.
     *
     * @param args Argumentos de l&iacute;nea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(RestaurantApiApplication.class, args);
    }
}
