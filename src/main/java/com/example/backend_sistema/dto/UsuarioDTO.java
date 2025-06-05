// src/main/java/com/example/backend_sistema/dto/UsuarioDTO.java
package com.example.backend_sistema.dto;

import com.example.backend_sistema.model.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioDTO {

    private Long id;
    private String usuario;
    private String nombre;
    private String telefono;
    private String rol;
    private String estado;
    private String turno;
    private LocalDate fechaIngreso;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructores
    public UsuarioDTO() {}

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.usuario = usuario.getUsuario();
        this.nombre = usuario.getNombre();
        this.telefono = usuario.getTelefono();
        this.rol = usuario.getRol() != null ? usuario.getRol().name() : null;
        this.estado = usuario.getEstado() != null ? usuario.getEstado().name() : null;
        this.turno = usuario.getTurno() != null ? usuario.getTurno().name() : null;
        this.fechaIngreso = usuario.getFechaIngreso();
        this.fechaCreacion = usuario.getFechaCreacion();
        this.fechaActualizacion = usuario.getFechaActualizacion();
    }

    // Getters y Setters completos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", rol='" + rol + '\'' +
                ", estado='" + estado + '\'' +
                ", turno='" + turno + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}