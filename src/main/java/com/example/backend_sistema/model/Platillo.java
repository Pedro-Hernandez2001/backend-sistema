package com.example.backend_sistema.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "platillos")
public class Platillo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private double precio;

    @Column(length = 1000)
    private String ingredientes; // Guardaremos como JSON o CSV

    private String imagen;
    private int picante;
    private boolean especialidad;
    private String categoria; // antojitos, platillosPrincipales, caldos


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getPicante() {
        return picante;
    }

    public void setPicante(int picante) {
        this.picante = picante;
    }

    public boolean isEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(boolean especialidad) {
        this.especialidad = especialidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<String> getIngredientesList() {
        // Puedes convertir ingredientes guardados en JSON o CSV a lista aquí
        // Ejemplo simple si es CSV:
        return List.of(ingredientes.split(","));
    }
}
