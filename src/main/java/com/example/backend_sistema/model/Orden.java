package com.example.backend_sistema.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mesa_id")
    @JsonProperty("mesa_id")
    private Integer mesaId;

    @Column(name = "mesero_id")
    @JsonProperty("mesero_id")
    private Integer meseroId;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private BigDecimal total;

    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;

    // Enum interno para el estado
    public enum Estado {
        pendiente, en_preparacion, lista, entregada, cancelada
    }

    // ===== Getters y Setters =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMesaId() {
        return mesaId;
    }

    public void setMesaId(Integer mesaId) {
        this.mesaId = mesaId;
    }

    public Integer getMeseroId() {
        return meseroId;
    }

    public void setMeseroId(Integer meseroId) {
        this.meseroId = meseroId;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
