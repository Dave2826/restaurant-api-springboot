package com.david.restaurantapi.controller;

import com.david.restaurantapi.entity.Pedido;
import com.david.restaurantapi.service.PedidoService;
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
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Integer id) {
        return pedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Guarda un nuevo pedido en el sistema.
     *
     * @param pedido el pedido a guardar
     * @return el pedido guardado
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (pedidoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
