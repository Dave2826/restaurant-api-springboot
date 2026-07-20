package com.david.restaurantapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dish available on the restaurant menu.
 * <p>
 * Each dish has a name, description, price, category, and availability
 * status. A dish can appear in multiple {@link OrderItem} entries across
 * different orders.
 * </p>
 *
 * @author David
 */
@Entity
@Table(name = "dishes")
public class Dish {

    // -------------------------------------------------------------------
    // Attributes
    // -------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column
    private String category;

    @Column(nullable = false)
    private Boolean available;

    // -------------------------------------------------------------------
    // Relationships
    // -------------------------------------------------------------------

    @OneToMany(mappedBy = "dish")
    private List<OrderItem> orderItems = new ArrayList<>();

    // -------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------

    public Dish() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
