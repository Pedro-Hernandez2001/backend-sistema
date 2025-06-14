package com.example.backend_sistema.controller;

import com.example.backend_sistema.model.Bebida;
import com.example.backend_sistema.repository.BebidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bebidas")
@CrossOrigin(origins = "*")
public class BebidaController {

    @Autowired
    private BebidaRepository bebidaRepository;

    // ENDPOINT DE PRUEBA SIMPLE
    @GetMapping("/test")
    public ResponseEntity<ApiResponse> test() {
        try {
            System.out.println("🧪 === ENDPOINT TEST BEBIDAS FUNCIONANDO ===");
            long count = bebidaRepository.count();
            return ResponseEntity.ok(new ApiResponse(true, "✅ API Bebidas funcionando! Total en BD: " + count));
        } catch (Exception e) {
            System.out.println("❌ Error en test: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "❌ Error: " + e.getMessage()));
        }
    }

    // Obtener todas las bebidas - SIEMPRE DEVUELVE JSON
    @GetMapping
    public ResponseEntity<ApiResponse> getAllBebidas() {
        try {
            System.out.println("🔍 === OBTENIENDO TODAS LAS BEBIDAS ===");
            System.out.println("📡 Petición recibida en /api/bebidas");

            List<Bebida> bebidas = bebidaRepository.findAll();
            System.out.println("📊 Total de bebidas en BD: " + bebidas.size());

            for (Bebida bebida : bebidas) {
                System.out.println("  - ID: " + bebida.getId() + " | Nombre: " + bebida.getNombreBebida());
            }

            System.out.println("✅ Enviando " + bebidas.size() + " bebidas al frontend");
            return ResponseEntity.ok(new ApiResponse(true, "Bebidas obtenidas exitosamente", bebidas));
            
        } catch (Exception e) {
            System.out.println("❌ ERROR OBTENIENDO BEBIDAS: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error: " + e.getMessage(), new ArrayList<>()));
        }
    }

    // Obtener bebida por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBebidaById(@PathVariable Long id) {
        try {
            Optional<Bebida> bebidaOpt = bebidaRepository.findById(id);

            if (bebidaOpt.isEmpty()) {
                System.out.println("❌ Bebida no encontrada con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Bebida no encontrada"));
            }

            return ResponseEntity.ok(new ApiResponse(true, "Bebida encontrada", bebidaOpt.get()));
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo bebida: " + e.getMessage());
            return ResponseEntity.ok(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }

    // Obtener bebidas disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse> getBebidasDisponibles() {
        try {
            System.out.println("🔍 === OBTENIENDO BEBIDAS DISPONIBLES ===");
            List<Bebida> bebidas = bebidaRepository.findByDisponibleEnMenu(true);
            System.out.println("📊 Bebidas disponibles: " + bebidas.size());
            return ResponseEntity.ok(new ApiResponse(true, "Bebidas disponibles obtenidas", bebidas));
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo bebidas disponibles: " + e.getMessage());
            return ResponseEntity.ok(new ApiResponse(false, "Error: " + e.getMessage(), new ArrayList<>()));
        }
    }

    // Crear nueva bebida
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse> createBebida(@Valid @RequestBody CreateBebidaRequest request) {
        try {
            System.out.println("🆕 === CREANDO NUEVA BEBIDA ===");
            System.out.println("Datos recibidos:");
            System.out.println("  - Nombre: " + request.getNombreBebida());
            System.out.println("  - Precio: $" + request.getPrecioMxn());
            System.out.println("  - Tamaño: " + request.getTamaño());
            System.out.println("  - Calorías: " + request.getCalorias());
            System.out.println("  - Contenido alcohólico: " + request.getContenidoAlcoholico());
            System.out.println("  - Producto artesanal: " + request.getProductoArtesanal());

            // Verificar que no exista una bebida con ese nombre
            if (bebidaRepository.existsByNombreBebida(request.getNombreBebida())) {
                System.out.println("❌ Bebida ya existe con nombre: " + request.getNombreBebida());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe una bebida con ese nombre"));
            }

            // Crear nueva bebida
            Bebida nuevaBebida = new Bebida();
            nuevaBebida.setNombreBebida(request.getNombreBebida());
            nuevaBebida.setDescripcion(request.getDescripcion());
            nuevaBebida.setPrecioMxn(request.getPrecioMxn());
            nuevaBebida.setTamaño(request.getTamaño());
            nuevaBebida.setCalorias(request.getCalorias());
            nuevaBebida.setContenidoAlcoholico(request.getContenidoAlcoholico());
            nuevaBebida.setProductoArtesanal(request.getProductoArtesanal());
            nuevaBebida.setDisponibleEnMenu(request.getDisponibleEnMenu());
            nuevaBebida.setFechaCreacion(LocalDateTime.now());
            nuevaBebida.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando en base de datos...");
            Bebida bebidaGuardada = bebidaRepository.save(nuevaBebida);

            System.out.println("✅ BEBIDA GUARDADA EXITOSAMENTE:");
            System.out.println("  - ID asignado: " + bebidaGuardada.getId());
            System.out.println("  - Nombre: " + bebidaGuardada.getNombreBebida());
            System.out.println("  - Precio: $" + bebidaGuardada.getPrecioMxn());

            return ResponseEntity.ok(new ApiResponse(true, "Bebida creada exitosamente", bebidaGuardada));

        } catch (Exception e) {
            System.out.println("❌ ERROR CREANDO BEBIDA: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Actualizar bebida
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> updateBebida(@PathVariable Long id, @Valid @RequestBody UpdateBebidaRequest request) {
        try {
            System.out.println("📝 === ACTUALIZANDO BEBIDA ID: " + id + " ===");

            Optional<Bebida> bebidaOpt = bebidaRepository.findById(id);

            if (bebidaOpt.isEmpty()) {
                System.out.println("❌ Bebida no encontrada con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Bebida no encontrada"));
            }

            Bebida bebida = bebidaOpt.get();
            System.out.println("📋 Bebida encontrada: " + bebida.getNombreBebida());

            // Verificar si el nuevo nombre ya existe (si es diferente al actual)
            if (!bebida.getNombreBebida().equals(request.getNombreBebida()) &&
                    bebidaRepository.existsByNombreBebida(request.getNombreBebida())) {
                System.out.println("❌ Nombre ya existe: " + request.getNombreBebida());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe una bebida con ese nombre"));
            }

            // Actualizar datos
            bebida.setNombreBebida(request.getNombreBebida());
            bebida.setDescripcion(request.getDescripcion());
            bebida.setPrecioMxn(request.getPrecioMxn());
            bebida.setTamaño(request.getTamaño());
            bebida.setCalorias(request.getCalorias());
            bebida.setContenidoAlcoholico(request.getContenidoAlcoholico());
            bebida.setProductoArtesanal(request.getProductoArtesanal());
            bebida.setDisponibleEnMenu(request.getDisponibleEnMenu());
            bebida.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando cambios en base de datos...");
            Bebida bebidaActualizada = bebidaRepository.save(bebida);

            System.out.println("✅ BEBIDA ACTUALIZADA EXITOSAMENTE:");
            System.out.println("  - ID: " + bebidaActualizada.getId());
            System.out.println("  - Nombre: " + bebidaActualizada.getNombreBebida());

            return ResponseEntity.ok(new ApiResponse(true, "Bebida actualizada exitosamente", bebidaActualizada));

        } catch (Exception e) {
            System.out.println("❌ ERROR ACTUALIZANDO BEBIDA: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Eliminar bebida
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteBebida(@PathVariable Long id) {
        try {
            System.out.println("🗑️ === ELIMINANDO BEBIDA ID: " + id + " ===");

            Optional<Bebida> bebidaOpt = bebidaRepository.findById(id);

            if (bebidaOpt.isEmpty()) {
                System.out.println("❌ Bebida no encontrada con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Bebida no encontrada"));
            }

            Bebida bebida = bebidaOpt.get();
            System.out.println("📋 Bebida a eliminar: " + bebida.getNombreBebida());

            System.out.println("💾 Eliminando de base de datos...");
            bebidaRepository.delete(bebida);

            System.out.println("✅ BEBIDA ELIMINADA EXITOSAMENTE: " + bebida.getNombreBebida());
            return ResponseEntity.ok(new ApiResponse(true, "Bebida eliminada exitosamente"));

        } catch (Exception e) {
            System.out.println("❌ ERROR ELIMINANDO BEBIDA: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Cambiar disponibilidad de la bebida
    @PutMapping("/{id}/disponibilidad")
    @Transactional
    public ResponseEntity<ApiResponse> cambiarDisponibilidad(@PathVariable Long id, @RequestBody CambiarDisponibilidadRequest request) {
        try {
            System.out.println("🔄 === CAMBIANDO DISPONIBILIDAD BEBIDA ID: " + id + " ===");
            System.out.println("Nueva disponibilidad: " + request.getDisponibleEnMenu());

            Optional<Bebida> bebidaOpt = bebidaRepository.findById(id);

            if (bebidaOpt.isEmpty()) {
                System.out.println("❌ Bebida no encontrada con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Bebida no encontrada"));
            }

            Bebida bebida = bebidaOpt.get();
            System.out.println("📋 Bebida encontrada: " + bebida.getNombreBebida() + " (Disponible: " + bebida.getDisponibleEnMenu() + ")");

            bebida.setDisponibleEnMenu(request.getDisponibleEnMenu());
            bebida.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando cambio de disponibilidad...");
            Bebida bebidaActualizada = bebidaRepository.save(bebida);

            System.out.println("✅ DISPONIBILIDAD CAMBIADA EXITOSAMENTE:");
            System.out.println("  - Bebida: " + bebidaActualizada.getNombreBebida());
            System.out.println("  - Disponible: " + bebidaActualizada.getDisponibleEnMenu());

            return ResponseEntity.ok(new ApiResponse(true, "Disponibilidad actualizada exitosamente", bebidaActualizada));

        } catch (Exception e) {
            System.out.println("❌ ERROR CAMBIANDO DISPONIBILIDAD: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // DTOs para las requests
    public static class CreateBebidaRequest {
        private String nombreBebida;
        private String descripcion;
        private BigDecimal precioMxn;
        private String tamaño;
        private Integer calorias;
        private Boolean contenidoAlcoholico = false;
        private Boolean productoArtesanal = false;
        private Boolean disponibleEnMenu = true;

        // Getters y Setters
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
    }

    public static class UpdateBebidaRequest extends CreateBebidaRequest {
        // Hereda todos los campos de CreateBebidaRequest
    }

    public static class CambiarDisponibilidadRequest {
        private Boolean disponibleEnMenu;

        public Boolean getDisponibleEnMenu() { return disponibleEnMenu; }
        public void setDisponibleEnMenu(Boolean disponibleEnMenu) { this.disponibleEnMenu = disponibleEnMenu; }
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