package com.david.restaurantapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una mesa fisica del restaurante.
 *
 * Contiene informacion como el numero de mesa,
 * la capacidad de comensales y la ubicacion.
 *
 * @author David Morales Guerrero
 */
@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador de la mesa. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Numero unico de la mesa. */
    @Column(name = "table_number", nullable = false, unique = true)
    private Integer tableNumber;

    /** Cantidad maxima de comensales. */
    @Column(nullable = false)
    private Integer capacity;

    /** Ubicacion dentro del restaurante. */
    @Column(length = 100)
    private String location;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Ordenes asociadas a esta mesa. */
    @OneToMany(mappedBy = "restaurantTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public RestaurantTable() {
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    /**
     * Obtiene el id de la mesa.
     *
     * @return Id de la mesa.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Asigna el id de la mesa.
     *
     * @param id Id de la mesa.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el numero de la mesa.
     *
     * @return Numero de la mesa.
     */
    public Integer getTableNumber() {
        return tableNumber;
    }

    /**
     * Asigna el numero de la mesa.
     *
     * @param tableNumber Numero de la mesa.
     */
    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * Obtiene la capacidad de la mesa.
     *
     * @return Capacidad de la mesa.
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Asigna la capacidad de la mesa.
     *
     * @param capacity Capacidad de la mesa.
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Obtiene la ubicacion de la mesa.
     *
     * @return Ubicacion de la mesa.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Asigna la ubicacion de la mesa.
     *
     * @param location Ubicacion de la mesa.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Obtiene las ordenes asociadas a esta mesa.
     *
     * @return Lista de ordenes.
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Asigna las ordenes asociadas a esta mesa.
     *
     * @param orders Lista de ordenes.
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
