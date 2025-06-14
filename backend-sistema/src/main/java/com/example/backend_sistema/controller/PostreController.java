package com.example.backend_sistema.controller;

import com.example.backend_sistema.model.Postre;
import com.example.backend_sistema.repository.PostreRepository;
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
@RequestMapping("/api/postres")
@CrossOrigin(origins = "*")
public class PostreController {

    @Autowired
    private PostreRepository postreRepository;

    // ENDPOINT DE PRUEBA SIMPLE
    @GetMapping("/test")
    public ResponseEntity<ApiResponse> test() {
        try {
            System.out.println("🧪 === ENDPOINT TEST POSTRES FUNCIONANDO ===");
            long count = postreRepository.count();
            return ResponseEntity.ok(new ApiResponse(true, "✅ API Postres funcionando! Total en BD: " + count));
        } catch (Exception e) {
            System.out.println("❌ Error en test: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "❌ Error: " + e.getMessage()));
        }
    }

    // Obtener todos los postres - SIEMPRE DEVUELVE JSON
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPostres() {
        try {
            System.out.println("🔍 === OBTENIENDO TODOS LOS POSTRES ===");
            System.out.println("📡 Petición recibida en /api/postres");

            List<Postre> postres = postreRepository.findAll();
            System.out.println("📊 Total de postres en BD: " + postres.size());

            for (Postre postre : postres) {
                System.out.println("  - ID: " + postre.getId() + " | Nombre: " + postre.getNombrePostre());
            }

            System.out.println("✅ Enviando " + postres.size() + " postres al frontend");
            return ResponseEntity.ok(new ApiResponse(true, "Postres obtenidos exitosamente", postres));
            
        } catch (Exception e) {
            System.out.println("❌ ERROR OBTENIENDO POSTRES: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error: " + e.getMessage(), new ArrayList<>()));
        }
    }

    // Obtener postre por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPostreById(@PathVariable Long id) {
        try {
            Optional<Postre> postreOpt = postreRepository.findById(id);

            if (postreOpt.isEmpty()) {
                System.out.println("❌ Postre no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Postre no encontrado"));
            }

            return ResponseEntity.ok(new ApiResponse(true, "Postre encontrado", postreOpt.get()));
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo postre: " + e.getMessage());
            return ResponseEntity.ok(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }

    // Obtener postres disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse> getPostresDisponibles() {
        try {
            System.out.println("🔍 === OBTENIENDO POSTRES DISPONIBLES ===");
            List<Postre> postres = postreRepository.findByDisponibleEnMenu(true);
            System.out.println("📊 Postres disponibles: " + postres.size());
            return ResponseEntity.ok(new ApiResponse(true, "Postres disponibles obtenidos", postres));
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo postres disponibles: " + e.getMessage());
            return ResponseEntity.ok(new ApiResponse(false, "Error: " + e.getMessage(), new ArrayList<>()));
        }
    }

    // Crear nuevo postre
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse> createPostre(@Valid @RequestBody CreatePostreRequest request) {
        try {
            System.out.println("🆕 === CREANDO NUEVO POSTRE ===");
            System.out.println("Datos recibidos:");
            System.out.println("  - Nombre: " + request.getNombrePostre());
            System.out.println("  - Precio: $" + request.getPrecioMxn());
            System.out.println("  - Porción: " + request.getPorcion());
            System.out.println("  - Calorías: " + request.getCalorias());
            System.out.println("  - Nivel dulzura: " + request.getNivelDulzura());
            System.out.println("  - Hecho en casa: " + request.getHechoEnCasa());

            // Verificar que no exista un postre con ese nombre
            if (postreRepository.existsByNombrePostre(request.getNombrePostre())) {
                System.out.println("❌ Postre ya existe con nombre: " + request.getNombrePostre());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe un postre con ese nombre"));
            }

            // Crear nuevo postre
            Postre nuevoPostre = new Postre();
            nuevoPostre.setNombrePostre(request.getNombrePostre());
            nuevoPostre.setDescripcion(request.getDescripcion());
            nuevoPostre.setPrecioMxn(request.getPrecioMxn());
            nuevoPostre.setPorcion(request.getPorcion());
            nuevoPostre.setCalorias(request.getCalorias());
            nuevoPostre.setNivelDulzura(request.getNivelDulzura());
            nuevoPostre.setHechoEnCasa(request.getHechoEnCasa());
            nuevoPostre.setDisponibleEnMenu(request.getDisponibleEnMenu());
            nuevoPostre.setFechaCreacion(LocalDateTime.now());
            nuevoPostre.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando en base de datos...");
            Postre postreGuardado = postreRepository.save(nuevoPostre);

            System.out.println("✅ POSTRE GUARDADO EXITOSAMENTE:");
            System.out.println("  - ID asignado: " + postreGuardado.getId());
            System.out.println("  - Nombre: " + postreGuardado.getNombrePostre());
            System.out.println("  - Precio: $" + postreGuardado.getPrecioMxn());

            return ResponseEntity.ok(new ApiResponse(true, "Postre creado exitosamente", postreGuardado));

        } catch (Exception e) {
            System.out.println("❌ ERROR CREANDO POSTRE: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Actualizar postre
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> updatePostre(@PathVariable Long id, @Valid @RequestBody UpdatePostreRequest request) {
        try {
            System.out.println("📝 === ACTUALIZANDO POSTRE ID: " + id + " ===");

            Optional<Postre> postreOpt = postreRepository.findById(id);

            if (postreOpt.isEmpty()) {
                System.out.println("❌ Postre no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Postre no encontrado"));
            }

            Postre postre = postreOpt.get();
            System.out.println("📋 Postre encontrado: " + postre.getNombrePostre());

            // Verificar si el nuevo nombre ya existe (si es diferente al actual)
            if (!postre.getNombrePostre().equals(request.getNombrePostre()) &&
                    postreRepository.existsByNombrePostre(request.getNombrePostre())) {
                System.out.println("❌ Nombre ya existe: " + request.getNombrePostre());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe un postre con ese nombre"));
            }

            // Actualizar datos
            postre.setNombrePostre(request.getNombrePostre());
            postre.setDescripcion(request.getDescripcion());
            postre.setPrecioMxn(request.getPrecioMxn());
            postre.setPorcion(request.getPorcion());
            postre.setCalorias(request.getCalorias());
            postre.setNivelDulzura(request.getNivelDulzura());
            postre.setHechoEnCasa(request.getHechoEnCasa());
            postre.setDisponibleEnMenu(request.getDisponibleEnMenu());
            postre.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando cambios en base de datos...");
            Postre postreActualizado = postreRepository.save(postre);

            System.out.println("✅ POSTRE ACTUALIZADO EXITOSAMENTE:");
            System.out.println("  - ID: " + postreActualizado.getId());
            System.out.println("  - Nombre: " + postreActualizado.getNombrePostre());

            return ResponseEntity.ok(new ApiResponse(true, "Postre actualizado exitosamente", postreActualizado));

        } catch (Exception e) {
            System.out.println("❌ ERROR ACTUALIZANDO POSTRE: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Eliminar postre
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> deletePostre(@PathVariable Long id) {
        try {
            System.out.println("🗑️ === ELIMINANDO POSTRE ID: " + id + " ===");

            Optional<Postre> postreOpt = postreRepository.findById(id);

            if (postreOpt.isEmpty()) {
                System.out.println("❌ Postre no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Postre no encontrado"));
            }

            Postre postre = postreOpt.get();
            System.out.println("📋 Postre a eliminar: " + postre.getNombrePostre());

            System.out.println("💾 Eliminando de base de datos...");
            postreRepository.delete(postre);

            System.out.println("✅ POSTRE ELIMINADO EXITOSAMENTE: " + postre.getNombrePostre());
            return ResponseEntity.ok(new ApiResponse(true, "Postre eliminado exitosamente"));

        } catch (Exception e) {
            System.out.println("❌ ERROR ELIMINANDO POSTRE: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Cambiar disponibilidad del postre
    @PutMapping("/{id}/disponibilidad")
    @Transactional
    public ResponseEntity<ApiResponse> cambiarDisponibilidad(@PathVariable Long id, @RequestBody CambiarDisponibilidadRequest request) {
        try {
            System.out.println("🔄 === CAMBIANDO DISPONIBILIDAD POSTRE ID: " + id + " ===");
            System.out.println("Nueva disponibilidad: " + request.getDisponibleEnMenu());

            Optional<Postre> postreOpt = postreRepository.findById(id);

            if (postreOpt.isEmpty()) {
                System.out.println("❌ Postre no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Postre no encontrado"));
            }

            Postre postre = postreOpt.get();
            System.out.println("📋 Postre encontrado: " + postre.getNombrePostre() + " (Disponible: " + postre.getDisponibleEnMenu() + ")");

            postre.setDisponibleEnMenu(request.getDisponibleEnMenu());
            postre.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando cambio de disponibilidad...");
            Postre postreActualizado = postreRepository.save(postre);

            System.out.println("✅ DISPONIBILIDAD CAMBIADA EXITOSAMENTE:");
            System.out.println("  - Postre: " + postreActualizado.getNombrePostre());
            System.out.println("  - Disponible: " + postreActualizado.getDisponibleEnMenu());

            return ResponseEntity.ok(new ApiResponse(true, "Disponibilidad actualizada exitosamente", postreActualizado));

        } catch (Exception e) {
            System.out.println("❌ ERROR CAMBIANDO DISPONIBILIDAD: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // DTOs para las requests
    public static class CreatePostreRequest {
        private String nombrePostre;
        private String descripcion;
        private BigDecimal precioMxn;
        private String porcion;
        private Integer calorias;
        private Postre.NivelDulzura nivelDulzura = Postre.NivelDulzura.Medio;
        private Boolean hechoEnCasa = false;
        private Boolean disponibleEnMenu = true;

        // Getters y Setters
        public String getNombrePostre() { return nombrePostre; }
        public void setNombrePostre(String nombrePostre) { this.nombrePostre = nombrePostre; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public BigDecimal getPrecioMxn() { return precioMxn; }
        public void setPrecioMxn(BigDecimal precioMxn) { this.precioMxn = precioMxn; }

        public String getPorcion() { return porcion; }
        public void setPorcion(String porcion) { this.porcion = porcion; }

        public Integer getCalorias() { return calorias; }
        public void setCalorias(Integer calorias) { this.calorias = calorias; }

        public Postre.NivelDulzura getNivelDulzura() { return nivelDulzura; }
        public void setNivelDulzura(Postre.NivelDulzura nivelDulzura) { this.nivelDulzura = nivelDulzura; }

        public Boolean getHechoEnCasa() { return hechoEnCasa; }
        public void setHechoEnCasa(Boolean hechoEnCasa) { this.hechoEnCasa = hechoEnCasa; }

        public Boolean getDisponibleEnMenu() { return disponibleEnMenu; }
        public void setDisponibleEnMenu(Boolean disponibleEnMenu) { this.disponibleEnMenu = disponibleEnMenu; }
    }

    public static class UpdatePostreRequest extends CreatePostreRequest {
        // Hereda todos los campos de CreatePostreRequest
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