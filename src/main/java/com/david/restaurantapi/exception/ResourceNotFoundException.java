package com.david.restaurantapi.exception;

/**
 * Excepcion lanzada cuando un recurso solicitado no existe.
 * El manejador global la traduce a una respuesta HTTP 404 con el
 * formato estandar de {@code ErrorResponse}.
 *
 * @author David Morales Guerrero
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
