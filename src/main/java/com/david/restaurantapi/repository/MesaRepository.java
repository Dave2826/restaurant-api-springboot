package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Mesa;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de administrar
 * la informacion de las mesas.
 *
 * @author David Morales Guerrero
 */
public interface MesaRepository extends CrudRepository<Mesa, Integer> {
}