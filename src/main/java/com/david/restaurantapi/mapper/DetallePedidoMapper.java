package com.david.restaurantapi.mapper;

import com.david.restaurantapi.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper encargado de convertir entre la entidad
 * y el dominio de los detalles de pedido.
 *
 * @author David Morales Guerrero
 */
@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {

    @Mapping(target = "quantity", source = "cantidad")
    @Mapping(target = "unitPrice", source = "precioUnitario")
    @Mapping(target = "order", source = "pedido")
    @Mapping(target = "dish", source = "platillo")
    OrderItem toDomain(com.david.restaurantapi.entity.DetallePedido detallePedido);

    @Mapping(target = "cantidad", source = "quantity")
    @Mapping(target = "precioUnitario", source = "unitPrice")
    @Mapping(target = "pedido", source = "order")
    @Mapping(target = "platillo", source = "dish")
    com.david.restaurantapi.entity.DetallePedido toEntity(OrderItem orderItem);
}