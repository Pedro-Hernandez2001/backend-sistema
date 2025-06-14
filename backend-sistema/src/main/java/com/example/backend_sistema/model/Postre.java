package com.example.backend_sistema.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "postres")
public class Postre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_postre", nullable = false, length = 100)
    private String nombrePostre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio_mxn", nullable = false, precision = 8, scale = 2)
    private BigDecimal precioMxn;

    @Column(nullable = false, length = 50)
    private String porcion;

    private Integer calorias;

    @Column(name = "nivel_dulzura", length = 10)
    @Enumerated(EnumType.STRING)
    private NivelDulzura nivelDulzura = NivelDulzura.Medio;

    @Column(name = "hecho_en_casa")
    private Boolean hechoEnCasa = false;

    @Column(name = "disponible_en_menu")
    private Boolean disponibleEnMenu = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    // Constructores
    public Postre() {}

    public Postre(String nombrePostre, String descripcion, BigDecimal precioMxn, String porcion) {
        this.nombrePostre = nombrePostre;
        this.descripcion = descripcion;
        this.precioMxn = precioMxn;
        this.porcion = porcion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePostre() {
        return nombrePostre;
    }

    public void setNombrePostre(String nombrePostre) {
        this.nombrePostre = nombrePostre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioMxn() {
        return precioMxn;
    }

    public void setPrecioMxn(BigDecimal precioMxn) {
        this.precioMxn = precioMxn;
    }

    public String getPorcion() {
        return porcion;
    }

    public void setPorcion(String porcion) {
        this.porcion = porcion;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public NivelDulzura getNivelDulzura() {
        return nivelDulzura;
    }

    public void setNivelDulzura(NivelDulzura nivelDulzura) {
        this.nivelDulzura = nivelDulzura;
    }

    public Boolean getHechoEnCasa() {
        return hechoEnCasa;
    }

    public void setHechoEnCasa(Boolean hechoEnCasa) {
        this.hechoEnCasa = hechoEnCasa;
    }

    public Boolean getDisponibleEnMenu() {
        return disponibleEnMenu;
    }

    public void setDisponibleEnMenu(Boolean disponibleEnMenu) {
        this.disponibleEnMenu = disponibleEnMenu;
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

    // Enum para el nivel de dulzura
    public enum NivelDulzura {
        Bajo, Medio, Alto
    }

    @Override
    public String toString() {
        return "Postre{" +
                "id=" + id +
                ", nombrePostre='" + nombrePostre + '\'' +
                ", precioMxn=" + precioMxn +
                ", porcion='" + porcion + '\'' +
                ", nivelDulzura=" + nivelDulzura +
                ", hechoEnCasa=" + hechoEnCasa +
                ", disponibleEnMenu=" + disponibleEnMenu +
                '}';
    }
}