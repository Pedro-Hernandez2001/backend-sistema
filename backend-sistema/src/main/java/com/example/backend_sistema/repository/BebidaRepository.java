package com.example.backend_sistema.repository;

import com.example.backend_sistema.model.Bebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {

    // Buscar bebida por nombre exacto
    Bebida findByNombreBebida(String nombreBebida);

    // Verificar si existe una bebida con ese nombre
    boolean existsByNombreBebida(String nombreBebida);

    // Buscar bebidas disponibles en menú
    List<Bebida> findByDisponibleEnMenu(Boolean disponibleEnMenu);

    // Buscar bebidas artesanales
    List<Bebida> findByProductoArtesanal(Boolean productoArtesanal);

    // Buscar bebidas sin alcohol (contenidoAlcoholico == false)
    List<Bebida> findByContenidoAlcoholico(Boolean contenidoAlcoholico);

    // Buscar bebidas por tamaño
    List<Bebida> findByTamaño(String tamaño);

    // Buscar bebidas por nombre que contenga texto (sin importar mayúsculas/minúsculas)
    List<Bebida> findByNombreBebidaContainingIgnoreCase(String nombreBebida);

    // Buscar bebidas que estén disponibles en menú y sean artesanales
    List<Bebida> findByDisponibleEnMenuAndProductoArtesanal(Boolean disponibleEnMenu, Boolean productoArtesanal);
}
