package com.david.restaurantapi.config;

import com.david.restaurantapi.entity.DetallePedido;
import com.david.restaurantapi.entity.Mesa;
import com.david.restaurantapi.entity.Pedido;
import com.david.restaurantapi.entity.Platillo;
import com.david.restaurantapi.repository.DetallePedidoRepository;
import com.david.restaurantapi.repository.MesaRepository;
import com.david.restaurantapi.repository.PedidoRepository;
import com.david.restaurantapi.repository.PlatilloRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializa los datos de prueba
 * al iniciar la aplicacion.
 *
 * @author David Morales Guerrero
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final MesaRepository mesaRepository;
    private final PlatilloRepository platilloRepository;
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public DataInitializer(MesaRepository mesaRepository,
                           PlatilloRepository platilloRepository,
                           PedidoRepository pedidoRepository,
                           DetallePedidoRepository detallePedidoRepository) {
        this.mesaRepository = mesaRepository;
        this.platilloRepository = platilloRepository;
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeMesas();
        initializePlatillos();
        initializePedidos();
        initializeDetallesPedido();
    }

    private void initializeMesas() {
        if (mesaRepository.count() > 0) {
            return;
        }

        Mesa mesa1 = new Mesa();
        mesa1.setNumeroMesa(1);
        mesa1.setCapacidad(4);
        mesa1.setUbicacion("Terraza");

        Mesa mesa2 = new Mesa();
        mesa2.setNumeroMesa(2);
        mesa2.setCapacidad(2);
        mesa2.setUbicacion("Interior");

        Mesa mesa3 = new Mesa();
        mesa3.setNumeroMesa(3);
        mesa3.setCapacidad(6);
        mesa3.setUbicacion("VIP");

        mesaRepository.saveAll(List.of(mesa1, mesa2, mesa3));
    }

    private void initializePlatillos() {
        if (platilloRepository.count() > 0) {
            return;
        }

        Platillo platillo1 = new Platillo();
        platillo1.setNombre("Hamburguesa Clásica");
        platillo1.setDescripcion("Carne de res con queso, lechuga y tomate.");
        platillo1.setPrecio(new BigDecimal("180.00"));
        platillo1.setCategoria("Hamburguesas");
        platillo1.setDisponible(true);

        Platillo platillo2 = new Platillo();
        platillo2.setNombre("Pizza Pepperoni");
        platillo2.setDescripcion("Pizza con pepperoni y queso mozzarella.");
        platillo2.setPrecio(new BigDecimal("240.00"));
        platillo2.setCategoria("Pizzas");
        platillo2.setDisponible(true);

        Platillo platillo3 = new Platillo();
        platillo3.setNombre("Tacos al Pastor");
        platillo3.setDescripcion("Tres tacos al pastor con piña.");
        platillo3.setPrecio(new BigDecimal("120.00"));
        platillo3.setCategoria("Tacos");
        platillo3.setDisponible(true);

        Platillo platillo4 = new Platillo();
        platillo4.setNombre("Ensalada César");
        platillo4.setDescripcion("Lechuga, pollo y aderezo César.");
        platillo4.setPrecio(new BigDecimal("150.00"));
        platillo4.setCategoria("Ensaladas");
        platillo4.setDisponible(true);

        Platillo platillo5 = new Platillo();
        platillo5.setNombre("Refresco");
        platillo5.setDescripcion("Bebida gaseosa de 600 ml.");
        platillo5.setPrecio(new BigDecimal("35.00"));
        platillo5.setCategoria("Bebidas");
        platillo5.setDisponible(true);

        platilloRepository.saveAll(List.of(platillo1, platillo2, platillo3, platillo4, platillo5));
    }

    private void initializePedidos() {
        if (pedidoRepository.count() > 0) {
            return;
        }

        List<Mesa> mesas = new java.util.ArrayList<>();
        mesaRepository.findAll().forEach(mesas::add);

        Pedido pedido1 = new Pedido();
        pedido1.setMesa(mesas.get(0));
        pedido1.setFechaPedido(LocalDateTime.now().minusHours(2));
        pedido1.setEstado("COMPLETED");
        pedido1.setTotal(new BigDecimal("430.00"));

        Pedido pedido2 = new Pedido();
        pedido2.setMesa(mesas.get(1));
        pedido2.setFechaPedido(LocalDateTime.now().minusHours(1));
        pedido2.setEstado("COMPLETED");
        pedido2.setTotal(new BigDecimal("275.00"));

        Pedido pedido3 = new Pedido();
        pedido3.setMesa(mesas.get(2));
        pedido3.setFechaPedido(LocalDateTime.now());
        pedido3.setEstado("COMPLETED");
        pedido3.setTotal(new BigDecimal("615.00"));

        pedidoRepository.saveAll(List.of(pedido1, pedido2, pedido3));
    }

    private void initializeDetallesPedido() {
        if (detallePedidoRepository.count() > 0) {
            return;
        }

        List<Pedido> pedidos = new java.util.ArrayList<>();
        pedidoRepository.findAll().forEach(pedidos::add);

        List<Platillo> platillos = new java.util.ArrayList<>();
        platilloRepository.findAll().forEach(platillos::add);

        DetallePedido detalle1 = new DetallePedido();
        detalle1.setPedido(pedidos.get(0));
        detalle1.setPlatillo(platillos.get(0));
        detalle1.setCantidad(2);
        detalle1.setPrecioUnitario(new BigDecimal("180.00"));
        detalle1.setSubtotal(new BigDecimal("360.00"));

        DetallePedido detalle2 = new DetallePedido();
        detalle2.setPedido(pedidos.get(0));
        detalle2.setPlatillo(platillos.get(4));
        detalle2.setCantidad(2);
        detalle2.setPrecioUnitario(new BigDecimal("35.00"));
        detalle2.setSubtotal(new BigDecimal("70.00"));

        DetallePedido detalle3 = new DetallePedido();
        detalle3.setPedido(pedidos.get(1));
        detalle3.setPlatillo(platillos.get(1));
        detalle3.setCantidad(1);
        detalle3.setPrecioUnitario(new BigDecimal("240.00"));
        detalle3.setSubtotal(new BigDecimal("240.00"));

        DetallePedido detalle4 = new DetallePedido();
        detalle4.setPedido(pedidos.get(1));
        detalle4.setPlatillo(platillos.get(4));
        detalle4.setCantidad(1);
        detalle4.setPrecioUnitario(new BigDecimal("35.00"));
        detalle4.setSubtotal(new BigDecimal("35.00"));

        DetallePedido detalle5 = new DetallePedido();
        detalle5.setPedido(pedidos.get(2));
        detalle5.setPlatillo(platillos.get(2));
        detalle5.setCantidad(3);
        detalle5.setPrecioUnitario(new BigDecimal("120.00"));
        detalle5.setSubtotal(new BigDecimal("360.00"));

        DetallePedido detalle6 = new DetallePedido();
        detalle6.setPedido(pedidos.get(2));
        detalle6.setPlatillo(platillos.get(3));
        detalle6.setCantidad(1);
        detalle6.setPrecioUnitario(new BigDecimal("150.00"));
        detalle6.setSubtotal(new BigDecimal("150.00"));

        DetallePedido detalle7 = new DetallePedido();
        detalle7.setPedido(pedidos.get(2));
        detalle7.setPlatillo(platillos.get(4));
        detalle7.setCantidad(3);
        detalle7.setPrecioUnitario(new BigDecimal("35.00"));
        detalle7.setSubtotal(new BigDecimal("105.00"));

        detallePedidoRepository.saveAll(List.of(detalle1, detalle2, detalle3, detalle4, detalle5, detalle6, detalle7));
    }
}