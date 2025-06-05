package com.example.backend_sistema.repository;

import com.example.backend_sistema.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    // Buscar mesa por número
    Mesa findByNumero(String numero);

    // Verificar si existe una mesa con ese número
    boolean existsByNumero(String numero);

    // Buscar mesas por estado
    List<Mesa> findByEstado(Mesa.EstadoMesa estado);

    // Buscar mesas por ubicación
    List<Mesa> findByUbicacion(String ubicacion);

    // Buscar mesas por capacidad
    List<Mesa> findByCapacidad(Integer capacidad);

    // Buscar mesas con capacidad mayor o igual
    List<Mesa> findByCapacidadGreaterThanEqual(Integer capacidad);

    // Buscar mesas por estado y ubicación
    List<Mesa> findByEstadoAndUbicacion(Mesa.EstadoMesa estado, String ubicacion);
}