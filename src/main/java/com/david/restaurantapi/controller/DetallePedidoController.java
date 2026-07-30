package com.david.restaurantapi.controller;

import com.david.restaurantapi.entity.DetallePedido;
import com.david.restaurantapi.service.DetallePedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
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
    @Operation(summary = "Obtener todos los detalles de pedido", description = "Devuelve la lista completa de detalles de pedido registrados en el restaurante, incluyendo cantidad, precio unitario y subtotal.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de detalles obtenida correctamente",
                     content = @Content(mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = DetallePedido.class))))
    })
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
    @Operation(summary = "Buscar detalle de pedido por ID", description = "Devuelve un detalle de pedido especifico utilizando su identificador unico interno.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
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
    @Operation(summary = "Guardar nuevo detalle de pedido", description = "Registra un nuevo detalle de pedido en el sistema. El identificador (id) se genera automaticamente por la base de datos, por lo que no debe enviarse en la solicitud.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Detalle creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida, verifique los datos enviados")
    })
    @PostMapping
    public ResponseEntity<DetallePedido> save(@RequestBody DetallePedido detallePedido) {
        detallePedido.setId(null);
        DetallePedido saved = detallePedidoService.save(detallePedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Elimina un detalle de pedido por su identificador.
     *
     * @param id identificador del detalle de pedido a eliminar
     * @return 204 si se elimino correctamente, 404 si no existe
     */
    @Operation(summary = "Eliminar detalle de pedido por ID", description = "Elimina un detalle de pedido del sistema utilizando su identificador unico.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Detalle eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (detallePedidoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        detallePedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
