
package com.example.backend_sistema.repository;

import com.example.backend_sistema.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // Buscar items por tipo
    List<MenuItem> findByTipo(MenuItem.TipoMenu tipo);

    // Buscar items disponibles
    List<MenuItem> findByDisponible(Boolean disponible);

    // Buscar items por tipo y disponibles
    List<MenuItem> findByTipoAndDisponible(MenuItem.TipoMenu tipo, Boolean disponible);

    // Buscar items por nombre (contiene)
    List<MenuItem> findByNombreContainingIgnoreCase(String nombre);

    // Buscar items por categoría
    List<MenuItem> findByCategoriaId(Integer categoriaId);

    // Buscar items por tipo y categoría
    List<MenuItem> findByTipoAndCategoriaId(MenuItem.TipoMenu tipo, Integer categoriaId);

    // Verificar si existe item con ese nombre
    boolean existsByNombre(String nombre);

    // Buscar items por especialidad (solo comida)
    List<MenuItem> findByEspecialidad(Boolean especialidad);

    // Buscar items artesanales (solo bebidas)
    List<MenuItem> findByArtesanal(Boolean artesanal);

    // Buscar items caseros (solo postres)
    List<MenuItem> findByCasero(Boolean casero);
}