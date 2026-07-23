package com.david.restaurantapi.mapper;

import com.david.restaurantapi.domain.OrderItem;
import org.mapstruct.Mapper;

/**
 * Mapper encargado de convertir entre la entidad
 * y el dominio de los productos de las ordenes.
 *
 * @author David Morales Guerrero
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItem toDomain(com.david.restaurantapi.entity.OrderItem orderItem);

    com.david.restaurantapi.entity.OrderItem toEntity(OrderItem orderItem);
}