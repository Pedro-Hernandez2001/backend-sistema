package com.example.backend_sistema.controller;

import com.example.backend_sistema.dto.LoginRequest;
import com.example.backend_sistema.dto.LoginResponse;
import com.example.backend_sistema.dto.UsuarioDTO;
import com.example.backend_sistema.model.Usuario;
import com.example.backend_sistema.repository.UsuarioRepository;
import com.example.backend_sistema.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("=== INTENTO DE LOGIN ===");
            System.out.println("Usuario: " + loginRequest.getUsuario());
            System.out.println("Password recibida: " + loginRequest.getPassword());

            // Buscar usuario en la base de datos
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuario(loginRequest.getUsuario());

            if (usuarioOpt.isEmpty()) {
                System.out.println("❌ Usuario no encontrado: " + loginRequest.getUsuario());
                return ResponseEntity.ok(new LoginResponse(false, "Usuario no encontrado"));
            }

            Usuario usuario = usuarioOpt.get();
            System.out.println("✅ Usuario encontrado: " + usuario.getNombre());
            System.out.println("Rol: " + usuario.getRol());
            System.out.println("Estado: " + usuario.getEstado());
            System.out.println("Password en BD: " + usuario.getPassword());

            // Verificar si el usuario está activo
            if (usuario.getEstado() != Usuario.Estado.activo) {
                System.out.println("❌ Usuario inactivo: " + loginRequest.getUsuario());
                return ResponseEntity.ok(new LoginResponse(false, "Usuario inactivo"));
            }

            // Verificar contraseña - COMPARACIÓN DIRECTA (TEXTO PLANO)
            System.out.println("🔑 Verificando contraseña en texto plano...");

            if (loginRequest.getPassword().equals(usuario.getPassword())) {
                // Generar token JWT
                String token = jwtUtils.generateJwtToken(usuario.getUsuario(), usuario.getRol().name());

                // Crear DTO del usuario (sin contraseña)
                UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);

                System.out.println("✅ LOGIN EXITOSO");
                System.out.println("Usuario: " + usuario.getUsuario());
                System.out.println("Rol: " + usuario.getRol());
                System.out.println("Token generado: " + token.substring(0, 20) + "...");

                return ResponseEntity.ok(new LoginResponse(
                        true,
                        "Login exitoso",
                        usuarioDTO,
                        token
                ));
            } else {
                System.out.println("❌ Contraseña incorrecta");
                System.out.println("Esperada: '" + usuario.getPassword() + "'");
                System.out.println("Recibida: '" + loginRequest.getPassword() + "'");
                return ResponseEntity.ok(new LoginResponse(false, "Contraseña incorrecta"));
            }

        } catch (Exception e) {
            System.out.println("💥 ERROR EN LOGIN: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse(false, "Error interno del servidor"));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        try {
            // Contar usuarios en la base de datos
            long count = usuarioRepository.count();
            List<Usuario> usuarios = usuarioRepository.findAll();

            StringBuilder info = new StringBuilder();
            info.append("✅ API funcionando correctamente\n");
            info.append("📊 Usuarios en BD: ").append(count).append("\n");
            info.append("👥 Lista de usuarios:\n");

            for (Usuario u : usuarios) {
                info.append("  - Usuario: ").append(u.getUsuario());
                info.append(" | Nombre: ").append(u.getNombre());
                info.append(" | Rol: ").append(u.getRol());
                info.append(" | Password: ").append(u.getPassword()).append("\n");
            }

            return ResponseEntity.ok(info.toString());
        } catch (Exception e) {
            return ResponseEntity.ok("❌ Error conectando a BD: " + e.getMessage());
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            System.out.println("📋 Obteniendo todos los usuarios:");
            for (Usuario u : usuarios) {
                System.out.println("  - " + u.getUsuario() + " (" + u.getRol() + ")");
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            System.out.println("Error obteniendo usuarios: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}