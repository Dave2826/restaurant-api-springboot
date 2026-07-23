package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Pedido;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de administrar
 * la informacion de los pedidos.
 *
 * @author David Morales Guerrero
 */
public interface PedidoRepository extends CrudRepository<Pedido, Integer> {

    List<Pedido> findByMesaId(Integer mesaId);
}