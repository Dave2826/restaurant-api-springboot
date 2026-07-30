package com.david.restaurantapi.service;

import com.david.restaurantapi.entity.Pedido;
import com.david.restaurantapi.repository.PedidoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de administrar
 * la informacion de los pedidos.
 *
 * @author David Morales Guerrero
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Obtiene todos los pedidos del sistema.
     *
     * @return lista de todos los pedidos
     */
    public Iterable<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    /**
     * Busca un pedido por su identificador.
     *
     * @param id identificador del pedido
     * @return un Optional con el pedido encontrado, o vacio si no existe
     */
    public Optional<Pedido> findById(Integer id) {
        return pedidoRepository.findById(id);
    }

    /**
     * Busca pedidos por su estado.
     *
     * @param estado estado de los pedidos a buscar
     * @return lista de pedidos con el estado indicado
     */
    public List<Pedido> findByEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    /**
     * Guarda un nuevo pedido en el sistema.
     *
     * @param pedido el pedido a guardar
     * @return el pedido guardado
     */
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    /**
     * Elimina un pedido por su identificador.
     *
     * @param id identificador del pedido a eliminar
     */
    public void deleteById(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
