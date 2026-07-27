package com.david.restaurantapi.service;

import com.david.restaurantapi.entity.Platillo;
import com.david.restaurantapi.repository.PlatilloRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de administrar
 * la informacion de los platillos.
 *
 * @author David Morales Guerrero
 */
@Service
public class PlatilloService {

    private final PlatilloRepository platilloRepository;

    public PlatilloService(PlatilloRepository platilloRepository) {
        this.platilloRepository = platilloRepository;
    }

    /**
     * Obtiene todos los platillos del sistema.
     *
     * @return lista de todos los platillos
     */
    public Iterable<Platillo> findAll() {
        return platilloRepository.findAll();
    }

    /**
     * Busca un platillo por su identificador.
     *
     * @param id identificador del platillo
     * @return un Optional con el platillo encontrado, o vacio si no existe
     */
    public Optional<Platillo> findById(Integer id) {
        return platilloRepository.findById(id);
    }

    /**
     * Guarda un nuevo platillo en el sistema.
     *
     * @param platillo el platillo a guardar
     * @return el platillo guardado
     */
    public Platillo save(Platillo platillo) {
        return platilloRepository.save(platillo);
    }

    /**
     * Elimina un platillo por su identificador.
     *
     * @param id identificador del platillo a eliminar
     */
    public void deleteById(Integer id) {
        platilloRepository.deleteById(id);
    }
}
