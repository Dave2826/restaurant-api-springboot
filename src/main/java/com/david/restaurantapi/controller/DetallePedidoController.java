package com.david.restaurantapi.controller;

import com.david.restaurantapi.entity.DetallePedido;
import com.david.restaurantapi.service.DetallePedidoService;
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
 * las operaciones relacionadas con los detalles de pedido.
 *
 * @author David Morales Guerrero
 */
@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    /**
     * Obtiene todos los detalles de pedido del sistema.
     *
     * @return lista de todos los detalles de pedido
     */
    @GetMapping
    public ResponseEntity<Iterable<DetallePedido>> findAll() {
        return ResponseEntity.ok(detallePedidoService.findAll());
    }

    /**
     * Busca un detalle de pedido por su identificador.
     *
     * @param id identificador del detalle de pedido
     * @return el detalle de pedido encontrado o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> findById(@PathVariable Integer id) {
        return detallePedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Guarda un nuevo detalle de pedido en el sistema.
     *
     * @param detallePedido el detalle de pedido a guardar
     * @return el detalle de pedido guardado
     */
    @PostMapping
    public ResponseEntity<DetallePedido> save(@RequestBody DetallePedido detallePedido) {
        DetallePedido saved = detallePedidoService.save(detallePedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Elimina un detalle de pedido por su identificador.
     *
     * @param id identificador del detalle de pedido a eliminar
     * @return 204 si se elimino correctamente, 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (detallePedidoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        detallePedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
