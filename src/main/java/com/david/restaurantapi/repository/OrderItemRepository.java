package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.OrderItem;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring Data repository for the {@link OrderItem} entity.
 * <p>
 * Provides standard CRUD operations with additional query methods
 * for looking up items by their parent order.
 * </p>
 *
 * @author David
 */
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderId(Integer orderId);
}
