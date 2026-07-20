package com.david.restaurantapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Restaurant API Spring Boot application.
 * <p>
 * Bootstraps the application context, enabling auto-configuration,
 * component scanning, and property binding for the entire project.
 * </p>
 */
@SpringBootApplication
public class RestaurantApiApplication {

    // -------------------------------------------------------------------
    // Main method
    // -------------------------------------------------------------------

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApiApplication.class, args);
    }
}
