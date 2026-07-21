package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Order;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio Spring Data para la entidad {@link Order}.
 * <p>
 * Proporciona operaciones CRUD est&aacute;ndar sobre las &oacute;rdenes
 * del restaurante, adem&aacute;s de m&eacute;todos de b&uacute;squeda
 * personalizados.
 * </p>
 *
 * @author David
 * @version 1.0
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {

    /**
     * Busca todas las &oacute;rdenes asociadas a una mesa.
     *
     * @param restaurantTableId Identificador de la mesa.
     * @return Lista de &oacute;rdenes encontradas.
     */
    List<Order> findByRestaurantTableId(Integer restaurantTableId);
}
