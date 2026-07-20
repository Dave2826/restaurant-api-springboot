package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.RestaurantTable;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring Data repository for the {@link RestaurantTable} entity.
 * <p>
 * Provides standard CRUD operations on restaurant tables.
 * </p>
 *
 * @author David
 */
public interface RestaurantTableRepository extends CrudRepository<RestaurantTable, Integer> {
}
