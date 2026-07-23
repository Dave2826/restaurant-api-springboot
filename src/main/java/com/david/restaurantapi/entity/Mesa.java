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
public class Mesa {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador de la mesa. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Numero de la mesa. */
    @Column(name = "table_number", nullable = false, unique = true)
    private Integer numeroMesa;

    /** Capacidad de la mesa. */
    @Column(nullable = false)
    private Integer capacidad;

    /** Ubicacion de la mesa. */
    @Column(length = 100)
    private String ubicacion;

    // ----------------------------------------------------
    // Relaciones
    // ----------------------------------------------------

    /** Pedidos de la mesa. */
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public Mesa() {
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

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}