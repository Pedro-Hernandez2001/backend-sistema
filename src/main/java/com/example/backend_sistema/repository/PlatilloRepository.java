package com.example.backend_sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.backend_sistema.model.Platillo;

public interface PlatilloRepository extends JpaRepository<Platillo, Long> {
    List<Platillo> findByCategoria(String categoria);
}
