package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Mesa;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de administrar
 * la informacion de las mesas.
 *
 * @author David Morales Guerrero
 */
// Repository / JPA
public interface MesaRepository extends CrudRepository<Mesa, Integer> {

    // Optional / Query Method
    Optional<Mesa> findByNumeroMesa(Integer numeroMesa);
}