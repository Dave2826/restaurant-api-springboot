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
 * Represents a physical table in the restaurant.
 * <p>
 * Each table has a unique number, a seating capacity, and an optional
 * location description. A table can have multiple orders associated
 * with it over time.
 * </p>
 *
 * @author David
 */
@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    // -------------------------------------------------------------------
    // Attributes
    // -------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "table_number", nullable = false, unique = true)
    private Integer tableNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column
    private String location;

    // -------------------------------------------------------------------
    // Relationships
    // -------------------------------------------------------------------

    @OneToMany(mappedBy = "restaurantTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // -------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------

    public RestaurantTable() {
    }

    // -------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
