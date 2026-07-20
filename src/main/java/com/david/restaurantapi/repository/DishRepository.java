package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Dish;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring Data repository for the {@link Dish} entity.
 * <p>
 * Provides standard CRUD operations on dishes available in the menu.
 * </p>
 *
 * @author David
 */
public interface DishRepository extends CrudRepository<Dish, Integer> {
}
