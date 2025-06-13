package com.example.backend_sistema.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "postre")
public class Postre {

    @Id
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String porcion;
    private String calorias;
    private String imagen;
    private Integer dulzura;
    private Boolean casero;
    private String categoria;

    // Constructor vacío
    public Postre() {
    }

    // Getters y setters
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
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
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
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
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
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}