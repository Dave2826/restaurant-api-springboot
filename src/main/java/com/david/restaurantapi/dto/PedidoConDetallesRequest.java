package com.david.restaurantapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Peticion para crear un pedido con sus detalles en una sola operacion")
public class PedidoConDetallesRequest {

    @Schema(example = "2026-07-30T14:30:00", description = "Fecha y hora del pedido")
    private LocalDateTime fechaPedido;

    @Schema(example = "PENDIENTE", description = "Estado del pedido")
    private String estado;

    @Schema(example = "540.00", description = "Total del pedido")
    private BigDecimal total;

    @Schema(example = "1", description = "Identificador de la mesa")
    private Integer mesaId;

    @Schema(description = "Lista de productos que componen el pedido")
    private List<DetalleEnPedidoRequest> detalles;

    public PedidoConDetallesRequest() {
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getMesaId() {
        return mesaId;
    }

    public void setMesaId(Integer mesaId) {
        this.mesaId = mesaId;
    }

    public List<DetalleEnPedidoRequest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleEnPedidoRequest> detalles) {
        this.detalles = detalles;
    }
}
