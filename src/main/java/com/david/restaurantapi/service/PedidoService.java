package com.david.restaurantapi.service;

import com.david.restaurantapi.dto.DetalleEnPedidoRequest;
import com.david.restaurantapi.dto.PedidoConDetallesRequest;
import com.david.restaurantapi.dto.PedidoConDetallesResponse;
import com.david.restaurantapi.entity.DetallePedido;
import com.david.restaurantapi.entity.Mesa;
import com.david.restaurantapi.entity.Pedido;
import com.david.restaurantapi.entity.Platillo;
import com.david.restaurantapi.repository.PedidoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio encargado de administrar
 * la informacion de los pedidos.
 *
 * @author David Morales Guerrero
 */
@Service
// CRUD / Repository
public class PedidoService {

    // Dependency Injection
    private final PedidoRepository pedidoRepository;
    private final MesaService mesaService;
    private final PlatilloService platilloService;

    public PedidoService(PedidoRepository pedidoRepository,
                         MesaService mesaService,
                         PlatilloService platilloService) {
        this.pedidoRepository = pedidoRepository;
        this.mesaService = mesaService;
        this.platilloService = platilloService;
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
    // Optional
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

    // Cascade / Maestro → Detalle / Persistencia automática
    @Transactional
    public PedidoConDetallesResponse guardarConDetalles(PedidoConDetallesRequest request) {
        Mesa mesa = mesaService.findById(request.getMesaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Mesa no encontrada con id: " + request.getMesaId()));

        Pedido pedido = new Pedido();
        pedido.setFechaPedido(request.getFechaPedido());
        pedido.setEstado(request.getEstado());
        pedido.setTotal(request.getTotal());
        pedido.setMesa(mesa);

        for (DetalleEnPedidoRequest dto : request.getDetalles()) {
            Platillo platillo = platilloService.findById(dto.getPlatilloId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Platillo no encontrado con id: " + dto.getPlatilloId()));

            DetallePedido detalle = new DetallePedido();
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
            detalle.setSubtotal(dto.getSubtotal());
            detalle.setPlatillo(platillo);
            detalle.setPedido(pedido);
            // Cascade: al agregar a la lista, JPA persiste automaticamente
            pedido.getDetalles().add(detalle);
        }

        // Unico save: CascadeType.ALL propaga a DetallePedido
        Pedido saved = pedidoRepository.save(pedido);
        return new PedidoConDetallesResponse(saved);
    }
}
