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
 * Representa un plato disponible en el menu
 * del restaurante.
 *
 * Contiene informacion como el nombre,
 * descripcion, categoria y precio.
 *
 * @author David Morales Guerrero
 */
@Entity
@Table(name = "dishes")
public class Dish {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador del plato. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre del plato. */
    @Column(nullable = false, length = 150)
    private String name;

    /** Descripcion del plato. */
    @Column(length = 500)
    private String description;

    /** Precio del plato. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** Categoria del plato. */
    @Column(length = 100)
    private String category;

    /** Indica si el plato esta disponible. */
    @Column(nullable = false)
    private Boolean available;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Ordenes que incluyen este plato. */
    @OneToMany(mappedBy = "dish")
    private List<OrderItem> orderItems = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public Dish() {
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
     * Obtiene las ordenes que incluyen este plato.
     *
     * @return Lista de ordenes.
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Asigna las ordenes que incluyen este plato.
     *
     * @param orderItems Lista de ordenes.
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
