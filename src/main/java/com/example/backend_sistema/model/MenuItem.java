
package com.example.backend_sistema.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "categoria_id")
    private Integer categoriaId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMenu tipo;

    @Column(nullable = false)
    private Boolean disponible = true;

    @Column(name = "imagen_url", columnDefinition = "TEXT")
    private String imagenUrl;

    // Campos específicos para comida
    private Integer picante;

    @Column(columnDefinition = "JSON")
    private String ingredientes; // JSON string

    private Boolean especialidad;

    // Campos específicos para bebidas
    @Column(length = 20)
    private String tamaño;

    @Column(length = 20)
    private String alcohol;

    private Boolean artesanal;

    // Campos específicos para postres
    @Column(length = 50)
    private String porcion;

    @Column(length = 20)
    private String calorias;

    private Integer dulzura;

    private Boolean casero;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    // Constructores
    public MenuItem() {}

    public MenuItem(String nombre, String descripcion, BigDecimal precio, TipoMenu tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public TipoMenu getTipo() {
        return tipo;
    }

    public void setTipo(TipoMenu tipo) {
        this.tipo = tipo;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    // Getters y Setters específicos para comida
    public Integer getPicante() {
        return picante;
    }

    public void setPicante(Integer picante) {
        this.picante = picante;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Boolean getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Boolean especialidad) {
        this.especialidad = especialidad;
    }

    // Getters y Setters específicos para bebidas
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

    public Boolean getArtesanal() {
        return artesanal;
    }

    public void setArtesanal(Boolean artesanal) {
        this.artesanal = artesanal;
    }

    // Getters y Setters específicos para postres
    public String getPorcion() {
        return porcion;
    }

    public void setPorcion(String porcion) {
        this.porcion = porcion;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String calorias) {
        this.calorias = calorias;
    }

    public Integer getDulzura() {
        return dulzura;
    }

    public void setDulzura(Integer dulzura) {
        this.dulzura = dulzura;
    }

    public Boolean getCasero() {
        return casero;
    }

    public void setCasero(Boolean casero) {
        this.casero = casero;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    // Enum para el tipo de menú
    public enum TipoMenu {
        comida, bebida, postre
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo=" + tipo +
                ", precio=" + precio +
                ", disponible=" + disponible +
                '}';
    }
}
