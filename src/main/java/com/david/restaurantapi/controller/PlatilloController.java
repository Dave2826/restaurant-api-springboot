package com.david.restaurantapi.controller;

import com.david.restaurantapi.entity.Platillo;
import com.david.restaurantapi.service.PlatilloService;
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
    @GetMapping("/{id}")
    public ResponseEntity<Platillo> findById(@PathVariable Integer id) {
        return platilloService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Guarda un nuevo platillo en el sistema.
     *
     * @param platillo el platillo a guardar
     * @return el platillo guardado
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (platilloService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        platilloService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
