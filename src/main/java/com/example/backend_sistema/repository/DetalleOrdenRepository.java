package com.example.backend_sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend_sistema.model.DetalleOrden;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
}