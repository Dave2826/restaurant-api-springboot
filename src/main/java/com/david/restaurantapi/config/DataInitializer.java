package com.david.restaurantapi.config;

import com.david.restaurantapi.entity.DetallePedido;
import com.david.restaurantapi.entity.Mesa;
import com.david.restaurantapi.entity.Pedido;
import com.david.restaurantapi.entity.Platillo;
import com.david.restaurantapi.entity.Usuario;
import com.david.restaurantapi.repository.DetallePedidoRepository;
import com.david.restaurantapi.repository.MesaRepository;
import com.david.restaurantapi.repository.PedidoRepository;
import com.david.restaurantapi.repository.PlatilloRepository;
import com.david.restaurantapi.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(MesaRepository mesaRepository,
                           PlatilloRepository platilloRepository,
                           PedidoRepository pedidoRepository,
                           DetallePedidoRepository detallePedidoRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.mesaRepository = mesaRepository;
        this.platilloRepository = platilloRepository;
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
        initializeMesas();
        initializePlatillos();
        initializePedidos();
        initializeDetallesPedido();
    }

    private void initializeUsers() {
        if (usuarioRepository.count() > 0) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("1234"));
        admin.setRole("ADMIN");

        usuarioRepository.save(admin);
    }

    private void initializeMesas() {
        if (mesaRepository.count() > 0) {
            return;
        }

        mesaRepository.saveAll(List.of(
            createMesa(1, 4, "Terraza"),
            createMesa(2, 2, "Interior"),
            createMesa(3, 6, "VIP"),
            createMesa(4, 6, "Terraza"),
            createMesa(5, 4, "Interior"),
            createMesa(6, 8, "Jardín"),
            createMesa(7, 2, "Terraza"),
            createMesa(8, 8, "Interior"),
            createMesa(9, 4, "VIP"),
            createMesa(10, 6, "Jardín")
        ));
    }

    private Mesa createMesa(int numeroMesa, int capacidad, String ubicacion) {
        Mesa mesa = new Mesa();
        mesa.setNumeroMesa(numeroMesa);
        mesa.setCapacidad(capacidad);
        mesa.setUbicacion(ubicacion);
        return mesa;
    }

    private void initializePlatillos() {
        if (platilloRepository.count() > 0) {
            return;
        }

        platilloRepository.saveAll(List.of(
            createPlatillo("Hamburguesa Clásica", "Carne de res con queso, lechuga y tomate.", "180.00", "Hamburguesas", true),
            createPlatillo("Hamburguesa BBQ", "Carne premium con salsa BBQ, aros de cebolla y queso derretido.", "220.00", "Hamburguesas", true),
            createPlatillo("Hamburguesa Vegetariana", "Falafel, lechuga, tomate y aderezo de yogur.", "160.00", "Hamburguesas", true),
            createPlatillo("Pizza Pepperoni", "Pizza con pepperoni y queso mozzarella.", "240.00", "Pizzas", true),
            createPlatillo("Pizza Margarita", "Pizza clásica con albahaca fresca, mozzarella y salsa de tomate.", "220.00", "Pizzas", true),
            createPlatillo("Pizza Hawaiana", "Pizza con jamón, piña y queso mozzarella.", "250.00", "Pizzas", true),
            createPlatillo("Tacos al Pastor", "Tres tacos al pastor con piña, cebolla y cilantro.", "120.00", "Tacos", true),
            createPlatillo("Tacos de Carnitas", "Tres tacos de carnitas con cebolla, cilantro y salsa verde.", "130.00", "Tacos", true),
            createPlatillo("Tacos de Pollo", "Tres tacos de pollo con cebolla, cilantro y salsa roja.", "110.00", "Tacos", true),
            createPlatillo("Ensalada César", "Lechuga romana, pollo, crutones, parmesano y aderezo César.", "150.00", "Ensaladas", true),
            createPlatillo("Ensalada Griega", "Lechuga, tomate, pepino, aceitunas, queso feta y vinagreta.", "140.00", "Ensaladas", true),
            createPlatillo("Refresco", "Bebida gaseosa de 600 ml.", "35.00", "Bebidas", true),
            createPlatillo("Agua Natural", "Agua purificada de 600 ml.", "20.00", "Bebidas", true),
            createPlatillo("Pastel de Chocolate", "Rebanada de pastel de chocolate con cobertura.", "90.00", "Postres", true),
            createPlatillo("Flan Napolitano", "Flan cremoso con caramelo.", "75.00", "Postres", true)
        ));
    }

    private Platillo createPlatillo(String nombre, String descripcion, String precio, String categoria, boolean disponible) {
        Platillo platillo = new Platillo();
        platillo.setNombre(nombre);
        platillo.setDescripcion(descripcion);
        platillo.setPrecio(new BigDecimal(precio));
        platillo.setCategoria(categoria);
        platillo.setDisponible(disponible);
        return platillo;
    }

    private void initializePedidos() {
        if (pedidoRepository.count() > 0) {
            return;
        }

        List<Mesa> mesas = new java.util.ArrayList<>();
        mesaRepository.findAll().forEach(mesas::add);

        pedidoRepository.saveAll(List.of(
            createPedido(mesas.get(0), "COMPLETED", "430.00", LocalDateTime.now().minusHours(5)),
            createPedido(mesas.get(1), "COMPLETED", "275.00", LocalDateTime.now().minusHours(4)),
            createPedido(mesas.get(2), "CANCELLED", "615.00", LocalDateTime.now().minusHours(3)),
            createPedido(mesas.get(3), "COMPLETED", "450.00", LocalDateTime.now().minusHours(2)),
            createPedido(mesas.get(4), "COMPLETED", "290.00", LocalDateTime.now().minusHours(1)),
            createPedido(mesas.get(5), "PENDIENTE", "730.00", LocalDateTime.now().plusHours(1)),
            createPedido(mesas.get(6), "COMPLETED", "245.00", LocalDateTime.now().minusMinutes(30)),
            createPedido(mesas.get(7), "CANCELLED", "240.00", LocalDateTime.now().minusMinutes(45)),
            createPedido(mesas.get(8), "PENDIENTE", "610.00", LocalDateTime.now().plusHours(2)),
            createPedido(mesas.get(9), "COMPLETED", "540.00", LocalDateTime.now().minusMinutes(15))
        ));
    }

    private Pedido createPedido(Mesa mesa, String estado, String total, LocalDateTime fecha) {
        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setFechaPedido(fecha);
        pedido.setEstado(estado);
        pedido.setTotal(new BigDecimal(total));
        return pedido;
    }

    private void initializeDetallesPedido() {
        if (detallePedidoRepository.count() > 0) {
            return;
        }

        List<Pedido> pedidos = new java.util.ArrayList<>();
        pedidoRepository.findAll().forEach(pedidos::add);

        List<Platillo> platillos = new java.util.ArrayList<>();
        platilloRepository.findAll().forEach(platillos::add);

        detallePedidoRepository.saveAll(List.of(
            // Pedido 1 (COMPLETED, Mesa 1)
            createDetalle(pedidos.get(0), platillos.get(0), 2, "180.00", "360.00"),
            createDetalle(pedidos.get(0), platillos.get(11), 2, "35.00", "70.00"),

            // Pedido 2 (COMPLETED, Mesa 2)
            createDetalle(pedidos.get(1), platillos.get(3), 1, "240.00", "240.00"),
            createDetalle(pedidos.get(1), platillos.get(11), 1, "35.00", "35.00"),

            // Pedido 3 (CANCELLED, Mesa 3)
            createDetalle(pedidos.get(2), platillos.get(6), 3, "120.00", "360.00"),
            createDetalle(pedidos.get(2), platillos.get(9), 1, "150.00", "150.00"),
            createDetalle(pedidos.get(2), platillos.get(11), 3, "35.00", "105.00"),

            // Pedido 4 (COMPLETED, Mesa 4)
            createDetalle(pedidos.get(3), platillos.get(1), 1, "220.00", "220.00"),
            createDetalle(pedidos.get(3), platillos.get(10), 1, "140.00", "140.00"),
            createDetalle(pedidos.get(3), platillos.get(13), 1, "90.00", "90.00"),

            // Pedido 5 (COMPLETED, Mesa 5)
            createDetalle(pedidos.get(4), platillos.get(5), 1, "250.00", "250.00"),
            createDetalle(pedidos.get(4), platillos.get(12), 2, "20.00", "40.00"),

            // Pedido 6 (PENDIENTE, Mesa 6)
            createDetalle(pedidos.get(5), platillos.get(2), 2, "160.00", "320.00"),
            createDetalle(pedidos.get(5), platillos.get(7), 2, "130.00", "260.00"),
            createDetalle(pedidos.get(5), platillos.get(14), 2, "75.00", "150.00"),

            // Pedido 7 (COMPLETED, Mesa 7)
            createDetalle(pedidos.get(6), platillos.get(6), 1, "120.00", "120.00"),
            createDetalle(pedidos.get(6), platillos.get(11), 1, "35.00", "35.00"),
            createDetalle(pedidos.get(6), platillos.get(13), 1, "90.00", "90.00"),

            // Pedido 8 (CANCELLED, Mesa 8)
            createDetalle(pedidos.get(7), platillos.get(4), 1, "220.00", "220.00"),
            createDetalle(pedidos.get(7), platillos.get(12), 1, "20.00", "20.00"),

            // Pedido 9 (PENDIENTE, Mesa 9)
            createDetalle(pedidos.get(8), platillos.get(3), 1, "240.00", "240.00"),
            createDetalle(pedidos.get(8), platillos.get(8), 2, "110.00", "220.00"),
            createDetalle(pedidos.get(8), platillos.get(9), 1, "150.00", "150.00"),

            // Pedido 10 (COMPLETED, Mesa 10)
            createDetalle(pedidos.get(9), platillos.get(0), 1, "180.00", "180.00"),
            createDetalle(pedidos.get(9), platillos.get(5), 1, "250.00", "250.00"),
            createDetalle(pedidos.get(9), platillos.get(11), 1, "35.00", "35.00"),
            createDetalle(pedidos.get(9), platillos.get(14), 1, "75.00", "75.00")
        ));
    }

    private DetallePedido createDetalle(Pedido pedido, Platillo platillo, int cantidad, String precioUnitario, String subtotal) {
        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setPlatillo(platillo);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(new BigDecimal(precioUnitario));
        detalle.setSubtotal(new BigDecimal(subtotal));
        return detalle;
    }
}
