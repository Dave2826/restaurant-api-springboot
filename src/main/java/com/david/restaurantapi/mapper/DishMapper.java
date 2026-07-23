package com.david.restaurantapi.mapper;

import com.david.restaurantapi.domain.Dish;
import org.mapstruct.Mapper;

/**
 * Mapper encargado de convertir entre la entidad
 * y el dominio de los platos del restaurante.
 *
 * @author David Morales Guerrero
 */
@Mapper(componentModel = "spring")
public interface DishMapper {

    Dish toDomain(com.david.restaurantapi.entity.Dish dish);

    com.david.restaurantapi.entity.Dish toEntity(Dish dish);
}