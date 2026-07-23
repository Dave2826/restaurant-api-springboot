package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Platillo;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de administrar
 * la informacion de los platillos.
 *
 * @author David Morales Guerrero
 */
public interface PlatilloRepository extends CrudRepository<Platillo, Integer> {
}