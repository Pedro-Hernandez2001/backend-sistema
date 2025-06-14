package com.example.backend_sistema.controller;

import com.example.backend_sistema.dto.UsuarioDTO;
import com.example.backend_sistema.model.Usuario;
import com.example.backend_sistema.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meseros")
@CrossOrigin(origins = "*")
public class MeseroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los meseros
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllMeseros() {
        try {
            System.out.println("🔍 === OBTENIENDO TODOS LOS MESEROS ===");

            List<Usuario> meseros = usuarioRepository.findByRol(Usuario.Rol.mesero);
            System.out.println("📊 Total de meseros en BD: " + meseros.size());

            for (Usuario mesero : meseros) {
                System.out.println("  - ID: " + mesero.getId() + " | Nombre: " + mesero.getNombre() + " | Usuario: " + mesero.getUsuario());
            }

            List<UsuarioDTO> meserosDTO = meseros.stream()
                    .map(UsuarioDTO::new)
                    .collect(Collectors.toList());

            System.out.println("✅ Enviando " + meserosDTO.size() + " meseros al frontend");
            return ResponseEntity.ok(meserosDTO);
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo meseros: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Crear nuevo mesero
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse> createMesero(@Valid @RequestBody CreateMeseroRequest request) {
        try {
            System.out.println("🆕 === CREANDO NUEVO MESERO ===");
            System.out.println("Datos recibidos:");
            System.out.println("  - Usuario: " + request.getUsuario());
            System.out.println("  - Nombre: " + request.getNombre());
            System.out.println("  - Teléfono: " + request.getTelefono());
            System.out.println("  - Turno: " + request.getTurno());
            System.out.println("  - Estado: " + request.getEstado());

            // Verificar que no exista el usuario
            if (usuarioRepository.existsByUsuario(request.getUsuario())) {
                System.out.println("❌ Usuario ya existe: " + request.getUsuario());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe un usuario con ese nombre"));
            }

            // Crear nuevo mesero
            Usuario nuevoMesero = new Usuario();
            nuevoMesero.setUsuario(request.getUsuario());
            nuevoMesero.setPassword(request.getPassword());
            nuevoMesero.setNombre(request.getNombre());
            nuevoMesero.setTelefono(request.getTelefono());
            nuevoMesero.setRol(Usuario.Rol.mesero);
            nuevoMesero.setEstado(request.getEstado());
            nuevoMesero.setTurno(request.getTurno());
            nuevoMesero.setFechaIngreso(request.getFechaIngreso());
            nuevoMesero.setFechaCreacion(LocalDateTime.now());
            nuevoMesero.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando en base de datos...");
            Usuario meseroGuardado = usuarioRepository.save(nuevoMesero);

            System.out.println("✅ MESERO GUARDADO EXITOSAMENTE:");
            System.out.println("  - ID asignado: " + meseroGuardado.getId());
            System.out.println("  - Nombre: " + meseroGuardado.getNombre());
            System.out.println("  - Usuario: " + meseroGuardado.getUsuario());
            System.out.println("  - Fecha creación: " + meseroGuardado.getFechaCreacion());

            // Verificar que se guardó realmente
            Optional<Usuario> verificacion = usuarioRepository.findById(meseroGuardado.getId());
            if (verificacion.isPresent()) {
                System.out.println("✅ Verificación: Mesero existe en BD con ID " + verificacion.get().getId());
            } else {
                System.out.println("❌ PROBLEMA: Mesero no encontrado después de guardar!");
            }

            return ResponseEntity.ok(new ApiResponse(true, "Mesero creado exitosamente", new UsuarioDTO(meseroGuardado)));

        } catch (Exception e) {
            System.out.println("❌ ERROR CREANDO MESERO: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Actualizar mesero
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> updateMesero(@PathVariable Long id, @Valid @RequestBody UpdateMeseroRequest request) {
        try {
            System.out.println("📝 === ACTUALIZANDO MESERO ID: " + id + " ===");
            System.out.println("Datos recibidos:");
            System.out.println("  - Usuario: " + request.getUsuario());
            System.out.println("  - Nombre: " + request.getNombre());
            System.out.println("  - Teléfono: " + request.getTelefono());
            System.out.println("  - Turno: " + request.getTurno());
            System.out.println("  - Estado: " + request.getEstado());

            Optional<Usuario> meseroOpt = usuarioRepository.findById(id);

            if (meseroOpt.isEmpty()) {
                System.out.println("❌ Mesero no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Mesero no encontrado"));
            }

            Usuario mesero = meseroOpt.get();
            System.out.println("📋 Mesero encontrado: " + mesero.getNombre());

            if (mesero.getRol() != Usuario.Rol.mesero) {
                System.out.println("❌ Usuario con ID " + id + " no es mesero, es: " + mesero.getRol());
                return ResponseEntity.ok(new ApiResponse(false, "El usuario no es un mesero"));
            }

            // Verificar si el nuevo usuario ya existe (si es diferente al actual)
            if (!mesero.getUsuario().equals(request.getUsuario()) &&
                    usuarioRepository.existsByUsuario(request.getUsuario())) {
                System.out.println("❌ Usuario ya existe: " + request.getUsuario());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe un usuario con ese nombre"));
            }

            // Actualizar datos
            mesero.setUsuario(request.getUsuario());
            mesero.setNombre(request.getNombre());
            mesero.setTelefono(request.getTelefono());
            mesero.setEstado(request.getEstado());
            mesero.setTurno(request.getTurno());
            mesero.setFechaActualizacion(LocalDateTime.now());

            // Solo actualizar contraseña si se proporciona
            if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
                System.out.println("🔐 Actualizando contraseña");
                mesero.setPassword(request.getPassword());
            }

            System.out.println("💾 Guardando cambios en base de datos...");
            Usuario meseroActualizado = usuarioRepository.save(mesero);

            System.out.println("✅ MESERO ACTUALIZADO EXITOSAMENTE:");
            System.out.println("  - ID: " + meseroActualizado.getId());
            System.out.println("  - Nombre: " + meseroActualizado.getNombre());
            System.out.println("  - Usuario: " + meseroActualizado.getUsuario());
            System.out.println("  - Fecha actualización: " + meseroActualizado.getFechaActualizacion());

            return ResponseEntity.ok(new ApiResponse(true, "Mesero actualizado exitosamente", new UsuarioDTO(meseroActualizado)));

        } catch (Exception e) {
            System.out.println("❌ ERROR ACTUALIZANDO MESERO: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Eliminar mesero
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteMesero(@PathVariable Long id) {
        try {
            System.out.println("🗑️ === ELIMINANDO MESERO ID: " + id + " ===");

            Optional<Usuario> meseroOpt = usuarioRepository.findById(id);

            if (meseroOpt.isEmpty()) {
                System.out.println("❌ Mesero no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Mesero no encontrado"));
            }

            Usuario mesero = meseroOpt.get();
            System.out.println("📋 Mesero a eliminar: " + mesero.getNombre());

            if (mesero.getRol() != Usuario.Rol.mesero) {
                System.out.println("❌ Usuario con ID " + id + " no es mesero, es: " + mesero.getRol());
                return ResponseEntity.ok(new ApiResponse(false, "El usuario no es un mesero"));
            }

            System.out.println("💾 Eliminando de base de datos...");
            usuarioRepository.delete(mesero);

            // Verificar que se eliminó
            Optional<Usuario> verificacion = usuarioRepository.findById(id);
            if (verificacion.isEmpty()) {
                System.out.println("✅ Verificación: Mesero eliminado correctamente de la BD");
            } else {
                System.out.println("❌ PROBLEMA: Mesero aún existe en BD después de eliminar!");
            }

            System.out.println("✅ MESERO ELIMINADO EXITOSAMENTE: " + mesero.getNombre());
            return ResponseEntity.ok(new ApiResponse(true, "Mesero eliminado exitosamente"));

        } catch (Exception e) {
            System.out.println("❌ ERROR ELIMINANDO MESERO: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Cambiar estado del mesero
    @PutMapping("/{id}/estado")
    @Transactional
    public ResponseEntity<ApiResponse> cambiarEstadoMesero(@PathVariable Long id, @RequestBody CambiarEstadoRequest request) {
        try {
            System.out.println("🔄 === CAMBIANDO ESTADO MESERO ID: " + id + " ===");
            System.out.println("Nuevo estado: " + request.getEstado());

            Optional<Usuario> meseroOpt = usuarioRepository.findById(id);

            if (meseroOpt.isEmpty()) {
                System.out.println("❌ Mesero no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Mesero no encontrado"));
            }

            Usuario mesero = meseroOpt.get();
            System.out.println("📋 Mesero encontrado: " + mesero.getNombre() + " (Estado actual: " + mesero.getEstado() + ")");

            if (mesero.getRol() != Usuario.Rol.mesero) {
                System.out.println("❌ Usuario con ID " + id + " no es mesero, es: " + mesero.getRol());
                return ResponseEntity.ok(new ApiResponse(false, "El usuario no es un mesero"));
            }

            mesero.setEstado(request.getEstado());
            mesero.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando cambio de estado...");
            Usuario meseroActualizado = usuarioRepository.save(mesero);

            System.out.println("✅ ESTADO CAMBIADO EXITOSAMENTE:");
            System.out.println("  - ID: " + meseroActualizado.getId());
            System.out.println("  - Nombre: " + meseroActualizado.getNombre());
            System.out.println("  - Estado: " + meseroActualizado.getEstado());

            return ResponseEntity.ok(new ApiResponse(true, "Estado actualizado exitosamente", new UsuarioDTO(meseroActualizado)));

        } catch (Exception e) {
            System.out.println("❌ ERROR CAMBIANDO ESTADO: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Endpoint para debug - ver todos los usuarios en BD
    @GetMapping("/debug/todos")
    public ResponseEntity<String> debugTodosLosUsuarios() {
        try {
            List<Usuario> todos = usuarioRepository.findAll();
            StringBuilder sb = new StringBuilder();
            sb.append("=== TODOS LOS USUARIOS EN BASE DE DATOS ===\n");
            sb.append("Total: ").append(todos.size()).append("\n\n");

            for (Usuario u : todos) {
                sb.append("ID: ").append(u.getId())
                        .append(" | Usuario: ").append(u.getUsuario())
                        .append(" | Nombre: ").append(u.getNombre())
                        .append(" | Rol: ").append(u.getRol())
                        .append(" | Estado: ").append(u.getEstado())
                        .append(" | Turno: ").append(u.getTurno())
                        .append("\n");
            }

            return ResponseEntity.ok(sb.toString());
        } catch (Exception e) {
            return ResponseEntity.ok("Error: " + e.getMessage());
        }
    }

    // DTOs (mismas clases que antes)
    public static class CreateMeseroRequest {
        private String usuario;
        private String password;
        private String nombre;
        private String telefono;
        private Usuario.Estado estado = Usuario.Estado.activo;
        private Usuario.Turno turno;
        private LocalDate fechaIngreso = LocalDate.now();

        // Getters y Setters
        public String getUsuario() { return usuario; }
        public void setUsuario(String usuario) { this.usuario = usuario; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }

        public Usuario.Estado getEstado() { return estado; }
        public void setEstado(Usuario.Estado estado) { this.estado = estado; }

        public Usuario.Turno getTurno() { return turno; }
        public void setTurno(Usuario.Turno turno) { this.turno = turno; }

        public LocalDate getFechaIngreso() { return fechaIngreso; }
        public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    }

    public static class UpdateMeseroRequest {
        private String usuario;
        private String password;
        private String nombre;
        private String telefono;
        private Usuario.Estado estado;
        private Usuario.Turno turno;

        // Getters y Setters
        public String getUsuario() { return usuario; }
        public void setUsuario(String usuario) { this.usuario = usuario; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }

        public Usuario.Estado getEstado() { return estado; }
        public void setEstado(Usuario.Estado estado) { this.estado = estado; }

        public Usuario.Turno getTurno() { return turno; }
        public void setTurno(Usuario.Turno turno) { this.turno = turno; }
    }

    public static class CambiarEstadoRequest {
        private Usuario.Estado estado;

        public Usuario.Estado getEstado() { return estado; }
        public void setEstado(Usuario.Estado estado) { this.estado = estado; }
    }

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