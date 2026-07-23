package com.david.restaurantapi.mapper;

import com.david.restaurantapi.domain.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper encargado de convertir entre la entidad
 * y el dominio de los platillos del restaurante.
 *
 * @author David Morales Guerrero
 */
@Mapper(componentModel = "spring")
public interface PlatilloMapper {

    @Mapping(target = "name", source = "nombre")
    @Mapping(target = "description", source = "descripcion")
    @Mapping(target = "price", source = "precio")
    @Mapping(target = "category", source = "categoria")
    @Mapping(target = "available", source = "disponible")
    @Mapping(target = "orderItems", source = "detallesPedido")
    Dish toDomain(com.david.restaurantapi.entity.Platillo platillo);

    @Mapping(target = "nombre", source = "name")
    @Mapping(target = "descripcion", source = "description")
    @Mapping(target = "precio", source = "price")
    @Mapping(target = "categoria", source = "category")
    @Mapping(target = "disponible", source = "available")
    @Mapping(target = "detallesPedido", source = "orderItems")
    com.david.restaurantapi.entity.Platillo toEntity(Dish dish);
}