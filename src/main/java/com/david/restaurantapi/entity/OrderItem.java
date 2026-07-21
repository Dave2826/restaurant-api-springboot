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
 * Representa un producto dentro de una orden.
 *
 * Contiene la cantidad solicitada, el precio
 * unitario y el subtotal del producto.
 *
 * @author David Morales Guerrero
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador del producto en la orden. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Cantidad solicitada. */
    @Column(nullable = false)
    private Integer quantity;

    /** Precio unitario del producto. */
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    /** Subtotal del producto. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Orden a la que pertenece. */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /** Plato solicitado. */
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public OrderItem() {
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    /**
     * Obtiene el id del producto en la orden.
     *
     * @return Id del producto.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Asigna el id del producto en la orden.
     *
     * @param id Id del producto.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la cantidad solicitada.
     *
     * @return Cantidad solicitada.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Asigna la cantidad solicitada.
     *
     * @param quantity Cantidad solicitada.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Obtiene el precio unitario.
     *
     * @return Precio unitario.
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Asigna el precio unitario.
     *
     * @param unitPrice Precio unitario.
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Obtiene el subtotal del producto.
     *
     * @return Subtotal del producto.
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * Asigna el subtotal del producto.
     *
     * @param subtotal Subtotal del producto.
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Obtiene la orden a la que pertenece.
     *
     * @return Orden asociada.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Asigna la orden a la que pertenece.
     *
     * @param order Orden asociada.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Obtiene el plato solicitado.
     *
     * @return Plato solicitado.
     */
    public Dish getDish() {
        return dish;
    }

    /**
     * Asigna el plato solicitado.
     *
     * @param dish Plato solicitado.
     */
    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
