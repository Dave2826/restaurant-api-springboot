package com.david.restaurantapi.config;

import com.david.restaurantapi.entity.Mesa;
import com.david.restaurantapi.entity.Platillo;
import com.david.restaurantapi.repository.DetallePedidoRepository;
import com.david.restaurantapi.repository.MesaRepository;
import com.david.restaurantapi.repository.PedidoRepository;
import com.david.restaurantapi.repository.PlatilloRepository;
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
        platillo1.setPrecio(new java.math.BigDecimal("180.00"));
        platillo1.setCategoria("Hamburguesas");
        platillo1.setDisponible(true);

        Platillo platillo2 = new Platillo();
        platillo2.setNombre("Pizza Pepperoni");
        platillo2.setDescripcion("Pizza con pepperoni y queso mozzarella.");
        platillo2.setPrecio(new java.math.BigDecimal("240.00"));
        platillo2.setCategoria("Pizzas");
        platillo2.setDisponible(true);

        Platillo platillo3 = new Platillo();
        platillo3.setNombre("Tacos al Pastor");
        platillo3.setDescripcion("Tres tacos al pastor con piña.");
        platillo3.setPrecio(new java.math.BigDecimal("120.00"));
        platillo3.setCategoria("Tacos");
        platillo3.setDisponible(true);

        Platillo platillo4 = new Platillo();
        platillo4.setNombre("Ensalada César");
        platillo4.setDescripcion("Lechuga, pollo y aderezo César.");
        platillo4.setPrecio(new java.math.BigDecimal("150.00"));
        platillo4.setCategoria("Ensaladas");
        platillo4.setDisponible(true);

        Platillo platillo5 = new Platillo();
        platillo5.setNombre("Refresco");
        platillo5.setDescripcion("Bebida gaseosa de 600 ml.");
        platillo5.setPrecio(new java.math.BigDecimal("35.00"));
        platillo5.setCategoria("Bebidas");
        platillo5.setDisponible(true);

        platilloRepository.saveAll(List.of(platillo1, platillo2, platillo3, platillo4, platillo5));
    }
}