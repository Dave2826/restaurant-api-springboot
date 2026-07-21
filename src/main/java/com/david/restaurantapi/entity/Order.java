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
 * Representa una orden realizada por un cliente en una mesa del restaurante.
 * <p>
 * Cada orden pertenece a una {@link RestaurantTable} y contiene uno o m&aacute;s
 * {@link OrderItem}. El estado (status) indica la etapa actual de la orden,
 * por ejemplo: PENDING, IN_PROGRESS, COMPLETED, CANCELLED.
 * </p>
 *
 * @author David
 * @version 1.0
 */
@Entity
@Table(name = "orders")
public class Order {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /**
     * Identificador &uacute;nico de la orden.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Fecha y hora en que se registr&oacute; la orden.
     */
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    /**
     * Estado actual de la orden (PENDING, IN_PROGRESS, COMPLETED, CANCELLED).
     */
    @Column(nullable = false, length = 50)
    private String status;

    /**
     * Monto total de la orden.
     */
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /**
     * Mesa del restaurante a la que pertenece esta orden.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_table_id", nullable = false)
    private RestaurantTable restaurantTable;

    /**
     * Lista de elementos que componen esta orden.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Order() {
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    /**
     * Obtiene el identificador &uacute;nico de la orden.
     *
     * @return Identificador de la orden.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador &uacute;nico de la orden.
     *
     * @param id Identificador de la orden.
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
     * Establece la fecha y hora de la orden.
     *
     * @param orderDate Fecha y hora de la orden.
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Obtiene el estado actual de la orden.
     *
     * @return Estado de la orden.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Establece el estado actual de la orden.
     *
     * @param status Estado de la orden.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtiene el monto total de la orden.
     *
     * @return Monto total de la orden.
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Establece el monto total de la orden.
     *
     * @param totalAmount Monto total de la orden.
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Obtiene la mesa asociada a esta orden.
     *
     * @return Mesa del restaurante.
     */
    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    /**
     * Establece la mesa asociada a esta orden.
     *
     * @param restaurantTable Mesa del restaurante.
     */
    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    /**
     * Obtiene la lista de elementos de la orden.
     *
     * @return Lista de elementos de la orden.
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Establece la lista de elementos de la orden.
     *
     * @param items Lista de elementos de la orden.
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
