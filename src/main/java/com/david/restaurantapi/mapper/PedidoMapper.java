package com.david.restaurantapi.mapper;

import com.david.restaurantapi.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper encargado de convertir entre la entidad
 * y el dominio de los pedidos del restaurante.
 *
 * @author David Morales Guerrero
 */
@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "orderDate", source = "fechaPedido")
    @Mapping(target = "status", source = "estado")
    @Mapping(target = "totalAmount", source = "total")
    @Mapping(target = "restaurantTable", source = "mesa")
    @Mapping(target = "items", source = "detalles")
    Order toDomain(com.david.restaurantapi.entity.Pedido pedido);

    @Mapping(target = "fechaPedido", source = "orderDate")
    @Mapping(target = "estado", source = "status")
    @Mapping(target = "total", source = "totalAmount")
    @Mapping(target = "mesa", source = "restaurantTable")
    @Mapping(target = "detalles", source = "items")
    com.david.restaurantapi.entity.Pedido toEntity(Order order);
}