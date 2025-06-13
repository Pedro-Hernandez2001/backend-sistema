package com.example.backend_sistema.model;


import jakarta.persistence.*;

@Entity
@Table(name = "bebidas")
public class Bebida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private String tamaño;
    private String alcohol;
    private String imagen;
    private Boolean artesanal;
    private String categoria;

    // Constructor vacío
    public Bebida() {}

    // Constructor con todos los campos
    public Bebida(Long id, String nombre, String descripcion, Double precio, String tamaño, String alcohol, String imagen, Boolean artesanal, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tamaño = tamaño;
        this.alcohol = alcohol;
        this.imagen = imagen;
        this.artesanal = artesanal;
        this.categoria = categoria;
    }

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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getArtesanal() {
        return artesanal;
    }

    public void setArtesanal(Boolean artesanal) {
        this.artesanal = artesanal;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
