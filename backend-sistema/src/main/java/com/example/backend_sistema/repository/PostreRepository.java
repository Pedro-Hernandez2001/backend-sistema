package com.example.backend_sistema.repository;

import com.example.backend_sistema.model.Postre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostreRepository extends JpaRepository<Postre, Long> {

    // Buscar postre por nombre
    Postre findByNombrePostre(String nombrePostre);

    // Verificar si existe un postre con ese nombre
    boolean existsByNombrePostre(String nombrePostre);

    // Buscar postres disponibles en menú
    List<Postre> findByDisponibleEnMenu(Boolean disponibleEnMenu);

    // Buscar postres por nivel de dulzura
    List<Postre> findByNivelDulzura(Postre.NivelDulzura nivelDulzura);

    // Buscar postres hechos en casa
    List<Postre> findByHechoEnCasa(Boolean hechoEnCasa);

    // Buscar postres por porción
    List<Postre> findByPorcion(String porcion);

    // Buscar postres con calorías menores o iguales a un valor
    List<Postre> findByCaloriasLessThanEqual(Integer calorias);

    // Buscar postres por nombre (contiene)
    List<Postre> findByNombrePostreContainingIgnoreCase(String nombrePostre);

    // Buscar postres disponibles y hechos en casa
    List<Postre> findByDisponibleEnMenuAndHechoEnCasa(Boolean disponibleEnMenu, Boolean hechoEnCasa);
}
