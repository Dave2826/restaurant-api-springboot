package com.david.restaurantapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa un usuario del sistema
 * con credenciales de acceso.
 *
 * @author David Morales Guerrero
 */
@Entity
@Table(name = "users")
public class Usuario {

    // ----------------------------------------------------
    // Atributos
    // ----------------------------------------------------

    /** Identificador del usuario. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre de usuario unico para iniciar sesion. */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /** Contrasena del usuario almacenada como BCrypt hash. */
    @Column(nullable = false, length = 255)
    private String password;

    /** Rol del usuario para control de acceso. */
    @Column(nullable = false, length = 20)
    private String role;

    // ----------------------------------------------------
    // Constructores
    // ----------------------------------------------------

    /** Constructor vacio. */
    public Usuario() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
