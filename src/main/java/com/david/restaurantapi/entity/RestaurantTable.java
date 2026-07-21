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
 * Representa una mesa f&iacute;sica dentro del restaurante.
 * <p>
 * Cada mesa tiene un n&uacute;mero &uacute;nico, una capacidad de comensales
 * y una ubicaci&oacute;n opcional. Una mesa puede tener varias &oacute;rdenes
 * asociadas a lo largo del tiempo.
 * </p>
 *
 * @author David
 * @version 1.0
 */
@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /**
     * Identificador &uacute;nico de la mesa.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * N&uacute;mero &uacute;nico de la mesa dentro del restaurante.
     */
    @Column(name = "table_number", nullable = false, unique = true)
    private Integer tableNumber;

    /**
     * Capacidad m&aacute;xima de comensales que puede albergar la mesa.
     */
    @Column(nullable = false)
    private Integer capacity;

    /**
     * Ubicaci&oacute;n f&iacute;sica de la mesa dentro del restaurante.
     */
    @Column(length = 100)
    private String location;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /**
     * Lista de &oacute;rdenes asociadas a esta mesa.
     */
    @OneToMany(mappedBy = "restaurantTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /**
     * Constructor por defecto requerido por JPA.
     */
    public RestaurantTable() {
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    /**
     * Obtiene el identificador &uacute;nico de la mesa.
     *
     * @return Identificador de la mesa.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador &uacute;nico de la mesa.
     *
     * @param id Identificador de la mesa.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el n&uacute;mero &uacute;nico de la mesa.
     *
     * @return N&uacute;mero de mesa.
     */
    public Integer getTableNumber() {
        return tableNumber;
    }

    /**
     * Establece el n&uacute;mero &uacute;nico de la mesa.
     *
     * @param tableNumber N&uacute;mero de mesa.
     */
    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * Obtiene la capacidad m&aacute;xima de comensales.
     *
     * @return Capacidad de la mesa.
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Establece la capacidad m&aacute;xima de comensales.
     *
     * @param capacity Capacidad de la mesa.
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Obtiene la ubicaci&oacute;n f&iacute;sica de la mesa.
     *
     * @return Ubicaci&oacute;n de la mesa.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Establece la ubicaci&oacute;n f&iacute;sica de la mesa.
     *
     * @param location Ubicaci&oacute;n de la mesa.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Obtiene la lista de &oacute;rdenes asociadas a esta mesa.
     *
     * @return Lista de &oacute;rdenes.
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Establece la lista de &oacute;rdenes asociadas a esta mesa.
     *
     * @param orders Lista de &oacute;rdenes.
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
