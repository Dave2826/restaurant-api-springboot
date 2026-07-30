package com.david.restaurantapi.controller;

import com.david.restaurantapi.entity.Pedido;
import com.david.restaurantapi.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador encargado de administrar
 * las operaciones relacionadas con los pedidos.
 *
 * @author David Morales Guerrero
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * Obtiene todos los pedidos del sistema.
     *
     * @return lista de todos los pedidos
     */
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve la lista completa de pedidos registrados en el restaurante.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<Iterable<Pedido>> findAll() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    /**
     * Busca un pedido por su identificador.
     *
     * @param id identificador del pedido
     * @return el pedido encontrado o 404 si no existe
     */
    @Operation(summary = "Buscar pedido por ID", description = "Devuelve un pedido segun su identificador unico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Integer id) {
        return pedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca pedidos por su estado.
     *
     * @param estado estado de los pedidos a buscar
     * @return lista de pedidos con el estado indicado
     */
    @Operation(summary = "Buscar pedidos por estado", description = "Devuelve todos los pedidos que tienen un estado especifico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> findByEstado(@PathVariable String estado) {
        List<Pedido> pedidos = pedidoService.findByEstado(estado);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Guarda un nuevo pedido en el sistema.
     *
     * @param pedido el pedido a guardar
     * @return el pedido guardado
     */
    @Operation(summary = "Guardar nuevo pedido", description = "Registra un nuevo pedido en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido creado correctamente")
    })
    @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody Pedido pedido) {
        Pedido saved = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Elimina un pedido por su identificador.
     *
     * @param id identificador del pedido a eliminar
     * @return 204 si se elimino correctamente, 404 si no existe
     */
    @Operation(summary = "Eliminar pedido por ID", description = "Elimina un pedido del sistema segun su identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pedido eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (pedidoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
