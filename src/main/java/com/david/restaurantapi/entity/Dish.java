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
 * Representa un plato disponible en el men&uacute; del restaurante.
 * <p>
 * Cada plato tiene un nombre, descripci&oacute;n, precio, categor&iacute;a
 * y estado de disponibilidad. Un plato puede aparecer en varios
 * {@link OrderItem} a trav&eacute;s de diferentes &oacute;rdenes.
 * </p>
 *
 * @author David
 * @version 1.0
 */
@Entity
@Table(name = "dishes")
public class Dish {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /**
     * Identificador &uacute;nico del plato.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del plato.
     */
    @Column(nullable = false, length = 150)
    private String name;

    /**
     * Descripci&oacute;n detallada del plato.
     */
    @Column(length = 500)
    private String description;

    /**
     * Precio del plato.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Categor&iacute;a del plato (Ej. Entrada, Plato fuerte, Postre).
     */
    @Column(length = 100)
    private String category;

    /**
     * Indica si el plato est&aacute; disponible para ordenar.
     */
    @Column(nullable = false)
    private Boolean available;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /**
     * Lista de elementos de orden que hacen referencia a este plato.
     */
    @OneToMany(mappedBy = "dish")
    private List<OrderItem> orderItems = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Dish() {
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    /**
     * Obtiene el identificador &uacute;nico del plato.
     *
     * @return Identificador del plato.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador &uacute;nico del plato.
     *
     * @param id Identificador del plato.
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
     * Establece el nombre del plato.
     *
     * @param name Nombre del plato.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la descripci&oacute;n del plato.
     *
     * @return Descripci&oacute;n del plato.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripci&oacute;n del plato.
     *
     * @param description Descripci&oacute;n del plato.
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
     * Establece el precio del plato.
     *
     * @param price Precio del plato.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Obtiene la categor&iacute;a del plato.
     *
     * @return Categor&iacute;a del plato.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Establece la categor&iacute;a del plato.
     *
     * @param category Categor&iacute;a del plato.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Indica si el plato est&aacute; disponible.
     *
     * @return {@code true} si est&aacute; disponible, {@code false} en caso contrario.
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * Establece la disponibilidad del plato.
     *
     * @param available {@code true} si est&aacute; disponible, {@code false} en caso contrario.
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * Obtiene la lista de elementos de orden que referencian este plato.
     *
     * @return Lista de elementos de orden.
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Establece la lista de elementos de orden que referencian este plato.
     *
     * @param orderItems Lista de elementos de orden.
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
