package com.david.restaurantapi.service;

import com.david.restaurantapi.entity.Mesa;
import com.david.restaurantapi.repository.MesaRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de administrar
 * la informacion de las mesas.
 *
 * @author David Morales Guerrero
 */
@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    /**
     * Obtiene todas las mesas del sistema.
     *
     * @return lista de todas las mesas
     */
    public Iterable<Mesa> findAll() {
        return mesaRepository.findAll();
    }

    /**
     * Busca una mesa por su identificador.
     *
     * @param id identificador de la mesa
     * @return un Optional con la mesa encontrada, o vacio si no existe
     */
    public Optional<Mesa> findById(Integer id) {
        return mesaRepository.findById(id);
    }

    /**
     * Guarda una nueva mesa en el sistema.
     *
     * @param mesa la mesa a guardar
     * @return la mesa guardada
     */
    public Mesa save(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    /**
     * Elimina una mesa por su identificador.
     *
     * @param id identificador de la mesa a eliminar
     */
    public void deleteById(Integer id) {
        mesaRepository.deleteById(id);
    }
}
