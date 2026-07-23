package com.david.restaurantapi.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una mesa del restaurante.
 *
 * Contiene el numero de mesa, la capacidad
 * y la ubicacion de la mesa.
 *
 * @author David Morales Guerrero
 */
public class RestaurantTable {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador de la mesa. */
    private Integer id;

    /** Numero de la mesa. */
    private Integer tableNumber;

    /** Capacidad de la mesa. */
    private Integer capacity;

    /** Ubicacion de la mesa. */
    private String location;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Ordenes de la mesa. */
    private List<Order> orders = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public RestaurantTable() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id Identificador de la mesa.
     * @param tableNumber Numero de la mesa.
     * @param capacity Capacidad de la mesa.
     * @param location Ubicacion de la mesa.
     */
    public RestaurantTable(Integer id, Integer tableNumber, Integer capacity, String location) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.location = location;
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
     * Obtiene las ordenes de la mesa.
     *
     * @return Lista de ordenes.
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Asigna las ordenes de la mesa.
     *
     * @param orders Lista de ordenes.
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}