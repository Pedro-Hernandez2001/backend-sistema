package com.example.backend_sistema.controller;

import com.example.backend_sistema.model.Mesa;
import com.example.backend_sistema.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin(origins = "*")
public class MesaController {

    @Autowired
    private MesaRepository mesaRepository;

    // Obtener todas las mesas
    @GetMapping
    public ResponseEntity<List<Mesa>> getAllMesas() {
        try {
            System.out.println("🔍 === OBTENIENDO TODAS LAS MESAS ===");

            List<Mesa> mesas = mesaRepository.findAll();
            System.out.println("📊 Total de mesas en BD: " + mesas.size());

            for (Mesa mesa : mesas) {
                System.out.println("  - ID: " + mesa.getId() + " | Número: " + mesa.getNumero() +
                        " | Capacidad: " + mesa.getCapacidad() + " | Estado: " + mesa.getEstado());
            }

            System.out.println("✅ Enviando " + mesas.size() + " mesas al frontend");
            return ResponseEntity.ok(mesas);
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo mesas: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtener mesa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mesa> getMesaById(@PathVariable Long id) {
        try {
            Optional<Mesa> mesaOpt = mesaRepository.findById(id);

            if (mesaOpt.isEmpty()) {
                System.out.println("❌ Mesa no encontrada con ID: " + id);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(mesaOpt.get());
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo mesa: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Crear nueva mesa
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse> createMesa(@Valid @RequestBody CreateMesaRequest request) {
        try {
            System.out.println("🆕 === CREANDO NUEVA MESA ===");
            System.out.println("Datos recibidos:");
            System.out.println("  - Número: " + request.getNumero());
            System.out.println("  - Capacidad: " + request.getCapacidad());
            System.out.println("  - Ubicación: " + request.getUbicacion());
            System.out.println("  - Estado: " + request.getEstado());
            System.out.println("  - Descripción: " + request.getDescripcion());

            // Verificar que no exista una mesa con ese número
            if (mesaRepository.existsByNumero(request.getNumero())) {
                System.out.println("❌ Mesa ya existe con número: " + request.getNumero());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe una mesa con ese número"));
            }

            // Crear nueva mesa
            Mesa nuevaMesa = new Mesa();
            nuevaMesa.setNumero(request.getNumero());
            nuevaMesa.setCapacidad(request.getCapacidad());
            nuevaMesa.setUbicacion(request.getUbicacion());
            nuevaMesa.setEstado(request.getEstado());
            nuevaMesa.setDescripcion(request.getDescripcion());
            nuevaMesa.setFechaCreacion(LocalDateTime.now());
            nuevaMesa.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando en base de datos...");
            Mesa mesaGuardada = mesaRepository.save(nuevaMesa);

            System.out.println("✅ MESA GUARDADA EXITOSAMENTE:");
            System.out.println("  - ID asignado: " + mesaGuardada.getId());
            System.out.println("  - Número: " + mesaGuardada.getNumero());
            System.out.println("  - Capacidad: " + mesaGuardada.getCapacidad());

            return ResponseEntity.ok(new ApiResponse(true, "Mesa creada exitosamente", mesaGuardada));

        } catch (Exception e) {
            System.out.println("❌ ERROR CREANDO MESA: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Actualizar mesa
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> updateMesa(@PathVariable Long id, @Valid @RequestBody UpdateMesaRequest request) {
        try {
            System.out.println("📝 === ACTUALIZANDO MESA ID: " + id + " ===");

            Optional<Mesa> mesaOpt = mesaRepository.findById(id);

            if (mesaOpt.isEmpty()) {
                System.out.println("❌ Mesa no encontrada con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Mesa no encontrada"));
            }

            Mesa mesa = mesaOpt.get();
            System.out.println("📋 Mesa encontrada: " + mesa.getNumero());

            // Verificar si el nuevo número ya existe (si es diferente al actual)
            if (!mesa.getNumero().equals(request.getNumero()) &&
                    mesaRepository.existsByNumero(request.getNumero())) {
                System.out.println("❌ Número de mesa ya existe: " + request.getNumero());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe una mesa con ese número"));
            }

            // Actualizar datos
            mesa.setNumero(request.getNumero());
            mesa.setCapacidad(request.getCapacidad());
            mesa.setUbicacion(request.getUbicacion());
            mesa.setEstado(request.getEstado());
            mesa.setDescripcion(request.getDescripcion());
            mesa.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando cambios en base de datos...");
            Mesa mesaActualizada = mesaRepository.save(mesa);

            System.out.println("✅ MESA ACTUALIZADA EXITOSAMENTE:");
            System.out.println("  - ID: " + mesaActualizada.getId());
            System.out.println("  - Número: " + mesaActualizada.getNumero());

            return ResponseEntity.ok(new ApiResponse(true, "Mesa actualizada exitosamente", mesaActualizada));

        } catch (Exception e) {
            System.out.println("❌ ERROR ACTUALIZANDO MESA: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Eliminar mesa
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteMesa(@PathVariable Long id) {
        try {
            System.out.println("🗑️ === ELIMINANDO MESA ID: " + id + " ===");

            Optional<Mesa> mesaOpt = mesaRepository.findById(id);

            if (mesaOpt.isEmpty()) {
                System.out.println("❌ Mesa no encontrada con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Mesa no encontrada"));
            }

            Mesa mesa = mesaOpt.get();
            System.out.println("📋 Mesa a eliminar: " + mesa.getNumero());

            System.out.println("💾 Eliminando de base de datos...");
            mesaRepository.delete(mesa);

            System.out.println("✅ MESA ELIMINADA EXITOSAMENTE: " + mesa.getNumero());
            return ResponseEntity.ok(new ApiResponse(true, "Mesa eliminada exitosamente"));

        } catch (Exception e) {
            System.out.println("❌ ERROR ELIMINANDO MESA: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Cambiar estado de la mesa
    @PutMapping("/{id}/estado")
    @Transactional
    public ResponseEntity<ApiResponse> cambiarEstadoMesa(@PathVariable Long id, @RequestBody CambiarEstadoMesaRequest request) {
        try {
            System.out.println("🔄 === CAMBIANDO ESTADO MESA ID: " + id + " ===");
            System.out.println("Nuevo estado: " + request.getEstado());

            Optional<Mesa> mesaOpt = mesaRepository.findById(id);

            if (mesaOpt.isEmpty()) {
                System.out.println("❌ Mesa no encontrada con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Mesa no encontrada"));
            }

            Mesa mesa = mesaOpt.get();
            System.out.println("📋 Mesa encontrada: " + mesa.getNumero() + " (Estado actual: " + mesa.getEstado() + ")");

            mesa.setEstado(request.getEstado());
            mesa.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando cambio de estado...");
            Mesa mesaActualizada = mesaRepository.save(mesa);

            System.out.println("✅ ESTADO CAMBIADO EXITOSAMENTE:");
            System.out.println("  - Mesa: " + mesaActualizada.getNumero());
            System.out.println("  - Estado: " + mesaActualizada.getEstado());

            return ResponseEntity.ok(new ApiResponse(true, "Estado actualizado exitosamente", mesaActualizada));

        } catch (Exception e) {
            System.out.println("❌ ERROR CAMBIANDO ESTADO: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // DTOs para las requests
    public static class CreateMesaRequest {
        private String numero;
        private Integer capacidad;
        private String ubicacion;
        private Mesa.EstadoMesa estado = Mesa.EstadoMesa.disponible;
        private String descripcion;

        // Getters y Setters
        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }

        public Integer getCapacidad() { return capacidad; }
        public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

        public String getUbicacion() { return ubicacion; }
        public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

        public Mesa.EstadoMesa getEstado() { return estado; }
        public void setEstado(Mesa.EstadoMesa estado) { this.estado = estado; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    }

    public static class UpdateMesaRequest {
        private String numero;
        private Integer capacidad;
        private String ubicacion;
        private Mesa.EstadoMesa estado;
        private String descripcion;

        // Getters y Setters
        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }

        public Integer getCapacidad() { return capacidad; }
        public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

        public String getUbicacion() { return ubicacion; }
        public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

        public Mesa.EstadoMesa getEstado() { return estado; }
        public void setEstado(Mesa.EstadoMesa estado) { this.estado = estado; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    }

    public static class CambiarEstadoMesaRequest {
        private Mesa.EstadoMesa estado;

        public Mesa.EstadoMesa getEstado() { return estado; }
        public void setEstado(Mesa.EstadoMesa estado) { this.estado = estado; }
    }

    // Clase de respuesta genérica
    public static class ApiResponse {
        private boolean success;
        private String message;
        private Object data;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public ApiResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }
    }
}