package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.OrderItem;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio Spring Data para la entidad {@link OrderItem}.
 * <p>
 * Proporciona operaciones CRUD est&aacute;ndar sobre los elementos
 * de las &oacute;rdenes, adem&aacute;s de m&eacute;todos de b&uacute;squeda
 * personalizados.
 * </p>
 *
 * @author David
 * @version 1.0
 */
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

    /**
     * Busca todos los elementos pertenecientes a una orden.
     *
     * @param orderId Identificador de la orden.
     * @return Lista de elementos encontrados.
     */
    List<OrderItem> findByOrderId(Integer orderId);
}
