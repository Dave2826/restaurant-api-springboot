package com.david.restaurantapi.controller;

import com.david.restaurantapi.entity.Mesa;
import com.david.restaurantapi.service.MesaService;
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
 * las operaciones relacionadas con las mesas.
 *
 * @author David Morales Guerrero
 */
@RestController
@RequestMapping("/api/mesas")
@SecurityRequirement(name = "bearerAuth")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    /**
     * Obtiene todas las mesas del sistema.
     *
     * @return lista de todas las mesas
     */
    @Operation(summary = "Obtener todas las mesas", description = "Devuelve la lista completa de mesas registradas en el restaurante, incluyendo su numero, capacidad y ubicacion.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de mesas obtenida correctamente",
                     content = @Content(mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = Mesa.class))))
    })
    @GetMapping
    public ResponseEntity<Iterable<Mesa>> findAll() {
        return ResponseEntity.ok(mesaService.findAll());
    }

    /**
     * Busca una mesa por su identificador.
     *
     * @param id identificador de la mesa
     * @return la mesa encontrada o 404 si no existe
     */
    @Operation(summary = "Buscar mesa por ID", description = "Devuelve una mesa especifica utilizando su identificador unico interno.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mesa encontrada"),
        @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Mesa> findById(@PathVariable Integer id) {
        return mesaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca una mesa por su numero de mesa.
     *
     * @param numeroMesa numero de la mesa a buscar
     * @return la mesa encontrada o 404 si no existe
     */
    @Operation(summary = "Buscar mesa por numero", description = "Devuelve una mesa especifica utilizando su numero de mesa asignado en el restaurante.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mesa encontrada"),
        @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
    @GetMapping("/numero/{numeroMesa}")
    public ResponseEntity<Mesa> findByNumeroMesa(@PathVariable Integer numeroMesa) {
        return mesaService.findByNumeroMesa(numeroMesa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Guarda una nueva mesa en el sistema.
     *
     * @param mesa la mesa a guardar
     * @return la mesa guardada
     */
    @Operation(summary = "Guardar nueva mesa", description = "Registra una nueva mesa en el sistema. El identificador (id) se genera automaticamente por la base de datos, por lo que no debe enviarse en la solicitud.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mesa creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida, verifique los datos enviados")
    })
    @PostMapping
    public ResponseEntity<Mesa> save(@RequestBody Mesa mesa) {
        mesa.setId(null);
        Mesa saved = mesaService.save(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Elimina una mesa por su identificador.
     *
     * @param id identificador de la mesa a eliminar
     * @return 204 si se elimino correctamente, 404 si no existe
     */
    @Operation(summary = "Eliminar mesa por ID", description = "Elimina una mesa del sistema utilizando su identificador unico. No se puede eliminar si tiene pedidos asociados.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Mesa eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (mesaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mesaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
