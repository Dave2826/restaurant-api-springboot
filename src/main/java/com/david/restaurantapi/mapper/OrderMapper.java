package com.david.restaurantapi.mapper;

import com.david.restaurantapi.domain.Order;
import org.mapstruct.Mapper;

/**
 * Mapper encargado de convertir entre la entidad
 * y el dominio de las ordenes del restaurante.
 *
 * @author David Morales Guerrero
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toDomain(com.david.restaurantapi.entity.Order order);

    com.david.restaurantapi.entity.Order toEntity(Order order);
}