package com.example.backend_sistema.repository;

import com.example.backend_sistema.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por nombre de usuario
    Optional<Usuario> findByUsuario(String usuario);

    // Buscar usuario por nombre de usuario y estado activo
    Optional<Usuario> findByUsuarioAndEstado(String usuario, Usuario.Estado estado);

    // Buscar todos los usuarios por rol
    List<Usuario> findByRol(Usuario.Rol rol);

    // Buscar todos los usuarios activos
    List<Usuario> findByEstado(Usuario.Estado estado);

    // Verificar si existe un usuario con ese nombre
    boolean existsByUsuario(String usuario);

    // Buscar por rol y estado
    List<Usuario> findByRolAndEstado(Usuario.Rol rol, Usuario.Estado estado);
}