package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Dish;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio Spring Data para la entidad {@link Dish}.
 * <p>
 * Proporciona operaciones CRUD est&aacute;ndar sobre los platos
 * disponibles en el men&uacute; del restaurante.
 * </p>
 *
 * @author David
 * @version 1.0
 */
public interface DishRepository extends CrudRepository<Dish, Integer> {
}
