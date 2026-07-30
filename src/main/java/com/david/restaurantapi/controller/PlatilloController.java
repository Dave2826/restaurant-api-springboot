package com.david.restaurantapi.controller;

import com.david.restaurantapi.entity.Platillo;
import com.david.restaurantapi.service.PlatilloService;
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
 * las operaciones relacionadas con los platillos.
 *
 * @author David Morales Guerrero
 */
@RestController
@RequestMapping("/api/platillos")
public class PlatilloController {

    private final PlatilloService platilloService;

    public PlatilloController(PlatilloService platilloService) {
        this.platilloService = platilloService;
    }

    /**
     * Obtiene todos los platillos del sistema.
     *
     * @return lista de todos los platillos
     */
    @Operation(summary = "Obtener todos los platillos", description = "Devuelve la lista completa de platillos registrados en el restaurante.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de platillos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<Iterable<Platillo>> findAll() {
        return ResponseEntity.ok(platilloService.findAll());
    }

    /**
     * Busca un platillo por su identificador.
     *
     * @param id identificador del platillo
     * @return el platillo encontrado o 404 si no existe
     */
    @Operation(summary = "Buscar platillo por ID", description = "Devuelve un platillo segun su identificador unico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Platillo encontrado"),
        @ApiResponse(responseCode = "404", description = "Platillo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Platillo> findById(@PathVariable Integer id) {
        return platilloService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca platillos por su categoria.
     *
     * @param categoria categoria de los platillos a buscar
     * @return lista de platillos de la categoria indicada
     */
    @Operation(summary = "Buscar platillos por categoria", description = "Devuelve todos los platillos que pertenecen a una categoria especifica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de platillos obtenida correctamente")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Platillo>> findByCategoria(@PathVariable String categoria) {
        List<Platillo> platillos = platilloService.findByCategoria(categoria);
        return ResponseEntity.ok(platillos);
    }

    /**
     * Guarda un nuevo platillo en el sistema.
     *
     * @param platillo el platillo a guardar
     * @return el platillo guardado
     */
    @Operation(summary = "Guardar nuevo platillo", description = "Registra un nuevo platillo en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Platillo creado correctamente")
    })
    @PostMapping
    public ResponseEntity<Platillo> save(@RequestBody Platillo platillo) {
        Platillo saved = platilloService.save(platillo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Elimina un platillo por su identificador.
     *
     * @param id identificador del platillo a eliminar
     * @return 204 si se elimino correctamente, 404 si no existe
     */
    @Operation(summary = "Eliminar platillo por ID", description = "Elimina un platillo del sistema segun su identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Platillo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Platillo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (platilloService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        platilloService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
