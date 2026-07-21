package com.david.restaurantapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * Representa un elemento individual dentro de una orden de cliente.
 * <p>
 * Cada elemento hace referencia a un {@link Dish} y registra la cantidad
 * solicitada, el precio unitario al momento de la orden y el subtotal
 * calculado.
 * </p>
 *
 * @author David
 * @version 1.0
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /**
     * Identificador &uacute;nico del elemento de orden.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Cantidad solicitada de este producto.
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Precio unitario del producto al momento de la orden.
     */
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    /**
     * Subtotal calculado (cantidad por precio unitario).
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /**
     * Orden a la que pertenece este elemento.
     */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Plato solicitado en este elemento de orden.
     */
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /**
     * Constructor por defecto requerido por JPA.
     */
    public OrderItem() {
    }

    // ----------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------

    /**
     * Obtiene el identificador &uacute;nico del elemento.
     *
     * @return Identificador del elemento.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador &uacute;nico del elemento.
     *
     * @param id Identificador del elemento.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la cantidad solicitada.
     *
     * @return Cantidad solicitada.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Establece la cantidad solicitada.
     *
     * @param quantity Cantidad solicitada.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Obtiene el precio unitario del producto.
     *
     * @return Precio unitario.
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param unitPrice Precio unitario.
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Obtiene el subtotal del elemento.
     *
     * @return Subtotal del elemento.
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * Establece el subtotal del elemento.
     *
     * @param subtotal Subtotal del elemento.
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Obtiene la orden a la que pertenece este elemento.
     *
     * @return Orden asociada.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Establece la orden a la que pertenece este elemento.
     *
     * @param order Orden asociada.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Obtiene el plato asociado a este elemento.
     *
     * @return Plato solicitado.
     */
    public Dish getDish() {
        return dish;
    }

    /**
     * Establece el plato asociado a este elemento.
     *
     * @param dish Plato solicitado.
     */
    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
