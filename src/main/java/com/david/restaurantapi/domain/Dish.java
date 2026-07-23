package com.david.restaurantapi.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un plato del menu del restaurante.
 *
 * Contiene el nombre, la descripcion,
 * la categoria y el precio del plato.
 *
 * @author David Morales Guerrero
 */
public class Dish {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador del plato. */
    private Integer id;

    /** Nombre del plato. */
    private String name;

    /** Descripcion del plato. */
    private String description;

    /** Precio del plato. */
    private BigDecimal price;

    /** Categoria del plato. */
    private String category;

    /** Disponibilidad del plato. */
    private Boolean available;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Ordenes del plato. */
    private List<OrderItem> orderItems = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public Dish() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id Identificador del plato.
     * @param name Nombre del plato.
     * @param description Descripcion del plato.
     * @param price Precio del plato.
     * @param category Categoria del plato.
     * @param available Disponibilidad del plato.
     */
    public Dish(Integer id, String name, String description, BigDecimal price,
                String category, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available = available;
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    /**
     * Obtiene el id del plato.
     *
     * @return Id del plato.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Asigna el id del plato.
     *
     * @param id Id del plato.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del plato.
     *
     * @return Nombre del plato.
     */
    public String getName() {
        return name;
    }

    /**
     * Asigna el nombre del plato.
     *
     * @param name Nombre del plato.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la descripcion del plato.
     *
     * @return Descripcion del plato.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Asigna la descripcion del plato.
     *
     * @param description Descripcion del plato.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene el precio del plato.
     *
     * @return Precio del plato.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Asigna el precio del plato.
     *
     * @param price Precio del plato.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Obtiene la categoria del plato.
     *
     * @return Categoria del plato.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Asigna la categoria del plato.
     *
     * @param category Categoria del plato.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Obtiene la disponibilidad del plato.
     *
     * @return Disponibilidad del plato.
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * Asigna la disponibilidad del plato.
     *
     * @param available Disponibilidad del plato.
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * Obtiene las ordenes del plato.
     *
     * @return Lista de ordenes.
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Asigna las ordenes del plato.
     *
     * @param orderItems Lista de ordenes.
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}