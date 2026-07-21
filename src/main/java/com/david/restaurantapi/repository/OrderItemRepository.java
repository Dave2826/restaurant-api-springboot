package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.OrderItem;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de administrar
 * los productos de las ordenes.
 *
 * @author David Morales Guerrero
 */
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

    /**
     * Busca los productos de una orden.
     *
     * @param orderId Id de la orden.
     * @return Lista de productos encontrados.
     */
    List<OrderItem> findByOrderId(Integer orderId);
}
