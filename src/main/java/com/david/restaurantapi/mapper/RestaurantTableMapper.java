package com.david.restaurantapi.mapper;

import com.david.restaurantapi.domain.RestaurantTable;
import org.mapstruct.Mapper;

/**
 * Mapper encargado de convertir entre la entidad
 * y el dominio de las mesas del restaurante.
 *
 * @author David Morales Guerrero
 */
@Mapper(componentModel = "spring")
public interface RestaurantTableMapper {

    RestaurantTable toDomain(com.david.restaurantapi.entity.RestaurantTable restaurantTable);

    com.david.restaurantapi.entity.RestaurantTable toEntity(RestaurantTable restaurantTable);
}