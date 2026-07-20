package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Order;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring Data repository for the {@link Order} entity.
 * <p>
 * Provides standard CRUD operations with additional query methods
 * for looking up orders by their associated restaurant table.
 * </p>
 *
 * @author David
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findByRestaurantTableId(Integer restaurantTableId);
}
