package com.david.restaurantapi.domain;

import java.math.BigDecimal;

/**
 * Representa un producto dentro de una orden.
 *
 * Contiene la cantidad, el precio unitario
 * y el subtotal del producto.
 *
 * @author David Morales Guerrero
 */
public class OrderItem {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador del producto. */
    private Integer id;

    /** Cantidad del producto. */
    private Integer quantity;

    /** Precio unitario del producto. */
    private BigDecimal unitPrice;

    /** Subtotal del producto. */
    private BigDecimal subtotal;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Orden del producto. */
    private Order order;

    /** Plato del producto. */
    private Dish dish;

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public OrderItem() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id Identificador del producto.
     * @param quantity Cantidad del producto.
     * @param unitPrice Precio unitario del producto.
     * @param subtotal Subtotal del producto.
     */
    public OrderItem(Integer id, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    /**
     * Obtiene el id del producto.
     *
     * @return Id del producto.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Asigna el id del producto.
     *
     * @param id Id del producto.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la cantidad del producto.
     *
     * @return Cantidad del producto.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Asigna la cantidad del producto.
     *
     * @param quantity Cantidad del producto.
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
     * Obtiene la orden del producto.
     *
     * @return Orden del producto.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Asigna la orden del producto.
     *
     * @param order Orden del producto.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Obtiene el plato del producto.
     *
     * @return Plato del producto.
     */
    public Dish getDish() {
        return dish;
    }

    /**
     * Asigna el plato del producto.
     *
     * @param dish Plato del producto.
     */
    public void setDish(Dish dish) {
        this.dish = dish;
    }
}