package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Dish;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de administrar
 * la informacion de los platos.
 *
 * @author David Morales Guerrero
 */
public interface DishRepository extends CrudRepository<Dish, Integer> {
}
