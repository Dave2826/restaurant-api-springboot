package com.david.restaurantapi.mapper;

import com.david.restaurantapi.domain.RestaurantTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper encargado de convertir entre la entidad
 * y el dominio de las mesas del restaurante.
 *
 * @author David Morales Guerrero
 */
@Mapper(componentModel = "spring")
public interface MesaMapper {

    @Mapping(target = "tableNumber", source = "numeroMesa")
    @Mapping(target = "capacity", source = "capacidad")
    @Mapping(target = "location", source = "ubicacion")
    @Mapping(target = "orders", source = "pedidos")
    RestaurantTable toDomain(com.david.restaurantapi.entity.Mesa mesa);

    @Mapping(target = "numeroMesa", source = "tableNumber")
    @Mapping(target = "capacidad", source = "capacity")
    @Mapping(target = "ubicacion", source = "location")
    @Mapping(target = "pedidos", source = "orders")
    com.david.restaurantapi.entity.Mesa toEntity(RestaurantTable restaurantTable);
}