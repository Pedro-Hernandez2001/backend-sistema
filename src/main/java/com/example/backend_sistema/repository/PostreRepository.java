package com.example.backend_sistema.repository;


import com.example.backend_sistema.model.Postre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostreRepository extends JpaRepository<Postre, Integer> {
    // Puedes agregar métodos personalizados si necesitas filtrar por categoría o algo más
}