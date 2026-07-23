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
 * Representa un platillo disponible en el menu
 * del restaurante.
 *
 * Contiene el nombre, la descripcion,
 * la categoria y el precio del platillo.
 *
 * @author David Morales Guerrero
 */
@Entity
@Table(name = "dishes")
public class Platillo {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador del platillo. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre del platillo. */
    @Column(nullable = false, length = 150)
    private String nombre;

    /** Descripcion del platillo. */
    @Column(length = 500)
    private String descripcion;

    /** Precio del platillo. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    /** Categoria del platillo. */
    @Column(length = 100)
    private String categoria;

    /** Indica si el platillo esta disponible. */
    @Column(nullable = false)
    private Boolean disponible;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Detalles de pedido que incluyen este platillo. */
    @OneToMany(mappedBy = "platillo")
    private List<DetallePedido> detallesPedido = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public Platillo() {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(List<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }
}