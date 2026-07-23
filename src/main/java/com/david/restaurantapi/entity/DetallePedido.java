package com.david.restaurantapi.entity;

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
@Entity
@Table(name = "order_items")
public class DetallePedido {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador del detalle. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Cantidad del producto. */
    @Column(nullable = false)
    private Integer cantidad;

    /** Precio unitario del producto. */
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    /** Subtotal del detalle. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Pedido al que pertenece. */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Pedido pedido;

    /** Platillo solicitado. */
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Platillo getPlatillo() {
        return platillo;
    }

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }
}