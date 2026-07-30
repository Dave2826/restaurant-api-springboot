package com.david.restaurantapi.dto;

import com.david.restaurantapi.entity.DetallePedido;
import com.david.restaurantapi.entity.Mesa;
import com.david.restaurantapi.entity.Pedido;
import com.david.restaurantapi.entity.Platillo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Pedido creado con sus detalles")
public class PedidoConDetallesResponse {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    private LocalDateTime fechaPedido;
    private String estado;
    private BigDecimal total;
    private Mesa mesa;

    @Schema(description = "Detalles del pedido con informacion del platillo")
    private List<DetalleInfo> detalles;

    public PedidoConDetallesResponse(Pedido pedido) {
        this.id = pedido.getId();
        this.fechaPedido = pedido.getFechaPedido();
        this.estado = pedido.getEstado();
        this.total = pedido.getTotal();
        this.mesa = pedido.getMesa();
        this.detalles = pedido.getDetalles().stream()
                .map(DetalleInfo::new)
                .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<DetalleInfo> getDetalles() {
        return detalles;
    }

    @Schema(description = "Producto individual dentro del pedido")
    public static class DetalleInfo {

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private Integer id;

        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;
        private Platillo platillo;

        public DetalleInfo(DetallePedido dp) {
            this.id = dp.getId();
            this.cantidad = dp.getCantidad();
            this.precioUnitario = dp.getPrecioUnitario();
            this.subtotal = dp.getSubtotal();
            this.platillo = dp.getPlatillo();
        }

        public Integer getId() {
            return id;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public Platillo getPlatillo() {
            return platillo;
        }
    }
}
