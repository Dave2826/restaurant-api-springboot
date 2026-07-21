package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Order;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de administrar
 * la informacion de las ordenes.
 *
 * @author David Morales Guerrero
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {

    /**
     * Busca las ordenes de una mesa.
     *
     * @param restaurantTableId Id de la mesa.
     * @return Lista de ordenes encontradas.
     */
    List<Order> findByRestaurantTableId(Integer restaurantTableId);
}
