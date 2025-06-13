package com.example.backend_sistema.repository;

import com.example.backend_sistema.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {

    // Buscar órdenes por mesero
    List<Orden> findByMeseroId(Integer meseroId);

    // Buscar órdenes por mesa
    List<Orden> findByMesaId(Integer mesaId);

    // Buscar órdenes por estado
    List<Orden> findByEstado(Orden.Estado estado);
}