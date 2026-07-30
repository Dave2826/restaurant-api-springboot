package com.david.restaurantapi.service;

import com.david.restaurantapi.entity.DetallePedido;
import com.david.restaurantapi.repository.DetallePedidoRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de administrar
 * los detalles de los pedidos.
 *
 * @author David Morales Guerrero
 */
@Service
// CRUD / Repository
public class DetallePedidoService {

    // Dependency Injection
    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    /**
     * Obtiene todos los detalles de pedido del sistema.
     *
     * @return lista de todos los detalles de pedido
     */
    public Iterable<DetallePedido> findAll() {
        return detallePedidoRepository.findAll();
    }

    /**
     * Busca un detalle de pedido por su identificador.
     *
     * @param id identificador del detalle de pedido
     * @return un Optional con el detalle encontrado, o vacio si no existe
     */
    // Optional
    public Optional<DetallePedido> findById(Integer id) {
        return detallePedidoRepository.findById(id);
    }

    /**
     * Guarda un nuevo detalle de pedido en el sistema.
     *
     * @param detallePedido el detalle de pedido a guardar
     * @return el detalle de pedido guardado
     */
    public DetallePedido save(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    /**
     * Elimina un detalle de pedido por su identificador.
     *
     * @param id identificador del detalle de pedido a eliminar
     */
    public void deleteById(Integer id) {
        detallePedidoRepository.deleteById(id);
    }
}
