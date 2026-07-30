package com.david.restaurantapi.repository;

import com.david.restaurantapi.entity.Usuario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de administrar
 * la informacion de los usuarios.
 *
 * @author David Morales Guerrero
 */
// Repository / JPA
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    // Optional / Query Method
    Optional<Usuario> findByUsername(String username);
}
