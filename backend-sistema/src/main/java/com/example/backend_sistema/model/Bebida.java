package com.example.backend_sistema.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bebidas")
public class Bebida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_bebida", nullable = false, length = 100)
    private String nombreBebida;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "precio_mxn", nullable = false, precision = 8, scale = 2)
    private BigDecimal precioMxn;

    @Column(nullable = false, length = 50)
    private String tamaño;

    private Integer calorias;

    @Column(name = "contenido_alcoholico")
    private Boolean contenidoAlcoholico = false;

    @Column(name = "producto_artesanal")
    private Boolean productoArtesanal = false;

    @Column(name = "disponible_en_menu")
    private Boolean disponibleEnMenu = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    // Constructores
    public Bebida() {}

    public Bebida(String nombreBebida, String descripcion, BigDecimal precioMxn, String tamaño) {
        this.nombreBebida = nombreBebida;
        this.descripcion = descripcion;
        this.precioMxn = precioMxn;
        this.tamaño = tamaño;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreBebida() { return nombreBebida; }
    public void setNombreBebida(String nombreBebida) { this.nombreBebida = nombreBebida; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecioMxn() { return precioMxn; }
    public void setPrecioMxn(BigDecimal precioMxn) { this.precioMxn = precioMxn; }

    public String getTamaño() { return tamaño; }
    public void setTamaño(String tamaño) { this.tamaño = tamaño; }

    public Integer getCalorias() { return calorias; }
    public void setCalorias(Integer calorias) { this.calorias = calorias; }

    public Boolean getContenidoAlcoholico() { return contenidoAlcoholico; }
    public void setContenidoAlcoholico(Boolean contenidoAlcoholico) { this.contenidoAlcoholico = contenidoAlcoholico; }

    public Boolean getProductoArtesanal() { return productoArtesanal; }
    public void setProductoArtesanal(Boolean productoArtesanal) { this.productoArtesanal = productoArtesanal; }

    public Boolean getDisponibleEnMenu() { return disponibleEnMenu; }
    public void setDisponibleEnMenu(Boolean disponibleEnMenu) { this.disponibleEnMenu = disponibleEnMenu; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "Bebida{" +
                "id=" + id +
                ", nombreBebida='" + nombreBebida + '\'' +
                ", precioMxn=" + precioMxn +
                ", tamaño='" + tamaño + '\'' +
                ", calorias=" + calorias +
                ", contenidoAlcoholico=" + contenidoAlcoholico +
                ", productoArtesanal=" + productoArtesanal +
                ", disponibleEnMenu=" + disponibleEnMenu +
                '}';
    }
}