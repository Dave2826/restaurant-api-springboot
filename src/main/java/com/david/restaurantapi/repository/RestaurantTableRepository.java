package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.RestaurantTable;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio Spring Data para la entidad {@link RestaurantTable}.
 * <p>
 * Proporciona operaciones CRUD est&aacute;ndar sobre la tabla de mesas
 * del restaurante.
 * </p>
 *
 * @author David
 * @version 1.0
 */
public interface RestaurantTableRepository extends CrudRepository<RestaurantTable, Integer> {
}
