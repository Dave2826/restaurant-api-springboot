package com.david.restaurantapi.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una orden del restaurante.
 *
 * Contiene la fecha, el estado, el total
 * y los productos de la orden.
 *
 * @author David Morales Guerrero
 */
public class Order {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador de la orden. */
    private Integer id;

    /** Fecha y hora de la orden. */
    private LocalDateTime orderDate;

    /** Estado de la orden. */
    private String status;

    /** Total de la orden. */
    private BigDecimal totalAmount;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Mesa de la orden. */
    private RestaurantTable restaurantTable;

    /** Productos de la orden. */
    private List<OrderItem> items = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public Order() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id Identificador de la orden.
     * @param orderDate Fecha y hora de la orden.
     * @param status Estado de la orden.
     * @param totalAmount Total de la orden.
     */
    public Order(Integer id, LocalDateTime orderDate, String status, BigDecimal totalAmount) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    /**
     * Obtiene el id de la orden.
     *
     * @return Id de la orden.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Asigna el id de la orden.
     *
     * @param id Id de la orden.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha y hora de la orden.
     *
     * @return Fecha y hora de la orden.
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Asigna la fecha y hora de la orden.
     *
     * @param orderDate Fecha y hora de la orden.
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Obtiene el estado de la orden.
     *
     * @return Estado de la orden.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Asigna el estado de la orden.
     *
     * @param status Estado de la orden.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtiene el total de la orden.
     *
     * @return Total de la orden.
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Asigna el total de la orden.
     *
     * @param totalAmount Total de la orden.
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Obtiene la mesa de la orden.
     *
     * @return Mesa de la orden.
     */
    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    /**
     * Asigna la mesa de la orden.
     *
     * @param restaurantTable Mesa de la orden.
     */
    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    /**
     * Obtiene los productos de la orden.
     *
     * @return Lista de productos.
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Asigna los productos de la orden.
     *
     * @param items Lista de productos.
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}