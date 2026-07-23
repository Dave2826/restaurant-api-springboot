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
 * Representa un pedido realizado por un cliente
 * en una mesa del restaurante.
 *
 * Contiene la fecha, el estado, el total y
 * los productos que lo componen.
 *
 * @author David Morales Guerrero
 */
@Entity
@Table(name = "orders")
public class Pedido {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador del pedido. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Fecha y hora del pedido. */
    @Column(name = "order_date", nullable = false)
    private LocalDateTime fechaPedido;

    /** Estado del pedido. */
    @Column(nullable = false, length = 50)
    private String estado;

    /** Total del pedido. */
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Mesa del pedido. */
    @ManyToOne
    @JoinColumn(name = "restaurant_table_id", nullable = false)
    private Mesa mesa;

    /** Detalles del pedido. */
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public Pedido() {
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

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
}