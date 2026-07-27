package com.david.restaurantapi.controller;

import com.david.restaurantapi.entity.Mesa;
import com.david.restaurantapi.service.MesaService;
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
    @GetMapping("/{id}")
    public ResponseEntity<Mesa> findById(@PathVariable Integer id) {
        return mesaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Guarda una nueva mesa en el sistema.
     *
     * @param mesa la mesa a guardar
     * @return la mesa guardada
     */
    @PostMapping
    public ResponseEntity<Mesa> save(@RequestBody Mesa mesa) {
        Mesa saved = mesaService.save(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Elimina una mesa por su identificador.
     *
     * @param id identificador de la mesa a eliminar
     * @return 204 si se elimino correctamente, 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (mesaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mesaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
