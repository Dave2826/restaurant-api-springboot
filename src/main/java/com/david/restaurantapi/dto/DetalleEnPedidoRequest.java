package com.david.restaurantapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Detalle de producto dentro del pedido")
public class DetalleEnPedidoRequest {

    @Schema(example = "2", description = "Cantidad de unidades del platillo")
    private Integer cantidad;

    @Schema(example = "180.00", description = "Precio unitario del platillo")
    private BigDecimal precioUnitario;

    @Schema(example = "360.00", description = "Subtotal = cantidad * precioUnitario")
    private BigDecimal subtotal;

    @Schema(example = "1", description = "Identificador del platillo")
    private Integer platilloId;

    public DetalleEnPedidoRequest() {
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getPlatilloId() {
        return platilloId;
    }

    public void setPlatilloId(Integer platilloId) {
        this.platilloId = platilloId;
    }
}
