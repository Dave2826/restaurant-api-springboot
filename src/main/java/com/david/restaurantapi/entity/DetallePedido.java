package com.david.restaurantapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * Representa un producto dentro de un pedido.
 *
 * Contiene la cantidad, el precio unitario
 * y el subtotal del producto.
 *
 * @author David Morales Guerrero
 */
// JPA / Hibernate
@Entity
@Table(name = "order_items")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DetallePedido {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    // Primary Key
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador generado automaticamente por la base de datos")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Cantidad del producto. */
    @Schema(example = "2", description = "Cantidad de unidades solicitadas")
    @Column(nullable = false)
    private Integer cantidad;

    /** Precio unitario del producto. */
    @Schema(example = "180.00", description = "Precio del platillo individual al momento del pedido")
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    /** Subtotal del detalle. */
    @Schema(example = "360.00", description = "Cantidad multiplicada por precio unitario")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    // Foreign Key / ManyToOne
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Pedido pedido;

    /**
     * Platillo solicitado.
     */
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Platillo platillo;

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public DetallePedido() {
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @JsonIgnore
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @JsonIgnore
    public Platillo getPlatillo() {
        return platillo;
    }

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }
}
