package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.DetallePedido;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de administrar
 * los detalles de los pedidos.
 *
 * @author David Morales Guerrero
 */
public interface DetallePedidoRepository extends CrudRepository<DetallePedido, Integer> {

    List<DetallePedido> findByPedidoId(Integer pedidoId);
}