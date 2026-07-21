package com.david.restaurantapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una orden realizada por un cliente
 * en una mesa del restaurante.
 *
 * Contiene la fecha, el estado, el total y
 * los productos que la componen.
 *
 * @author David Morales Guerrero
 */
@Entity
@Table(name = "orders")
public class Order {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador de la orden. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Fecha y hora de la orden. */
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    /** Estado actual de la orden. */
    @Column(nullable = false, length = 50)
    private String status;

    /** Total a pagar de la orden. */
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Mesa donde se realizo la orden. */
    @ManyToOne
    @JoinColumn(name = "restaurant_table_id", nullable = false)
    private RestaurantTable restaurantTable;

    /** Productos incluidos en la orden. */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public Order() {
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
