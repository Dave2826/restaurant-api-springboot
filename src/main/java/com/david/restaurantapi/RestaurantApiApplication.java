package com.david.restaurantapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicacion Restaurant API.
 *
 * Inicia el contexto de Spring Boot y permite
 * la configuracion automatica del proyecto.
 *
 * @author David Morales Guerrero
 */
@SpringBootApplication
public class RestaurantApiApplication {

    // ----------------------------------------------------
    // Metodo principal
    // ----------------------------------------------------

    /**
     * Metodo principal que arranca la aplicacion.
     *
     * @param args Argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(RestaurantApiApplication.class, args);
    }
}
