package com.example.backend_sistema.controller;

import com.example.backend_sistema.model.MenuItem;
import com.example.backend_sistema.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuItemController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Obtener todos los items del menú
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        try {
            System.out.println("🔍 === OBTENIENDO TODOS LOS ITEMS DEL MENÚ ===");

            List<MenuItem> items = menuItemRepository.findAll();
            System.out.println("📊 Total de items en BD: " + items.size());

            for (MenuItem item : items) {
                System.out.println("  - ID: " + item.getId() + " | Nombre: " + item.getNombre() +
                        " | Tipo: " + item.getTipo() + " | Precio: $" + item.getPrecio());
            }

            System.out.println("✅ Enviando " + items.size() + " items al frontend");
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo items del menú: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtener items por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByTipo(@PathVariable String tipo) {
        try {
            System.out.println("🔍 === OBTENIENDO ITEMS POR TIPO: " + tipo + " ===");

            MenuItem.TipoMenu tipoMenu = MenuItem.TipoMenu.valueOf(tipo.toLowerCase());
            List<MenuItem> items = menuItemRepository.findByTipo(tipoMenu);

            System.out.println("📊 Items de tipo " + tipo + ": " + items.size());
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo items por tipo: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtener item por ID
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        try {
            Optional<MenuItem> itemOpt = menuItemRepository.findById(id);

            if (itemOpt.isEmpty()) {
                System.out.println("❌ Item no encontrado con ID: " + id);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(itemOpt.get());
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo item: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Crear nuevo item del menú
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse> createMenuItem(@Valid @RequestBody CreateMenuItemRequest request) {
        try {
            System.out.println("🆕 === CREANDO NUEVO ITEM DEL MENÚ ===");
            System.out.println("Datos recibidos:");
            System.out.println("  - Nombre: " + request.getNombre());
            System.out.println("  - Tipo: " + request.getTipo());
            System.out.println("  - Precio: $" + request.getPrecio());
            System.out.println("  - Descripción: " + request.getDescripcion());

            // Verificar que no exista un item con ese nombre
            if (menuItemRepository.existsByNombre(request.getNombre())) {
                System.out.println("❌ Item ya existe con nombre: " + request.getNombre());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe un item con ese nombre"));
            }

            // Crear nuevo item
            MenuItem nuevoItem = new MenuItem();
            nuevoItem.setNombre(request.getNombre());
            nuevoItem.setDescripcion(request.getDescripcion());
            nuevoItem.setPrecio(request.getPrecio());
            nuevoItem.setTipo(request.getTipo());
            nuevoItem.setDisponible(request.getDisponible());
            nuevoItem.setImagenUrl(request.getImagenUrl());
            nuevoItem.setFechaCreacion(LocalDateTime.now());
            nuevoItem.setFechaActualizacion(LocalDateTime.now());

            // Establecer campos específicos según el tipo
            switch (request.getTipo()) {
                case comida:
                    nuevoItem.setPicante(request.getPicante());
                    nuevoItem.setIngredientes(request.getIngredientes());
                    nuevoItem.setEspecialidad(request.getEspecialidad());
                    break;
                case bebida:
                    nuevoItem.setTamaño(request.getTamaño());
                    nuevoItem.setAlcohol(request.getAlcohol());
                    nuevoItem.setArtesanal(request.getArtesanal());
                    break;
                case postre:
                    nuevoItem.setPorcion(request.getPorcion());
                    nuevoItem.setCalorias(request.getCalorias());
                    nuevoItem.setDulzura(request.getDulzura());
                    nuevoItem.setCasero(request.getCasero());
                    break;
            }

            System.out.println("💾 Guardando en base de datos...");
            MenuItem itemGuardado = menuItemRepository.save(nuevoItem);

            System.out.println("✅ ITEM GUARDADO EXITOSAMENTE:");
            System.out.println("  - ID asignado: " + itemGuardado.getId());
            System.out.println("  - Nombre: " + itemGuardado.getNombre());
            System.out.println("  - Tipo: " + itemGuardado.getTipo());

            return ResponseEntity.ok(new ApiResponse(true, "Item creado exitosamente", itemGuardado));

        } catch (Exception e) {
            System.out.println("❌ ERROR CREANDO ITEM: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Actualizar item del menú
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> updateMenuItem(@PathVariable Long id, @Valid @RequestBody UpdateMenuItemRequest request) {
        try {
            System.out.println("📝 === ACTUALIZANDO ITEM ID: " + id + " ===");

            Optional<MenuItem> itemOpt = menuItemRepository.findById(id);

            if (itemOpt.isEmpty()) {
                System.out.println("❌ Item no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Item no encontrado"));
            }

            MenuItem item = itemOpt.get();
            System.out.println("📋 Item encontrado: " + item.getNombre());

            // Verificar si el nuevo nombre ya existe (si es diferente al actual)
            if (!item.getNombre().equals(request.getNombre()) &&
                    menuItemRepository.existsByNombre(request.getNombre())) {
                System.out.println("❌ Nombre ya existe: " + request.getNombre());
                return ResponseEntity.ok(new ApiResponse(false, "Ya existe un item con ese nombre"));
            }

            // Actualizar datos básicos
            item.setNombre(request.getNombre());
            item.setDescripcion(request.getDescripcion());
            item.setPrecio(request.getPrecio());
            item.setDisponible(request.getDisponible());
            item.setImagenUrl(request.getImagenUrl());
            item.setFechaActualizacion(LocalDateTime.now());

            // Limpiar campos específicos primero
            item.setPicante(null);
            item.setIngredientes(null);
            item.setEspecialidad(null);
            item.setTamaño(null);
            item.setAlcohol(null);
            item.setArtesanal(null);
            item.setPorcion(null);
            item.setCalorias(null);
            item.setDulzura(null);
            item.setCasero(null);

            // Establecer campos específicos según el tipo
            switch (item.getTipo()) {
                case comida:
                    item.setPicante(request.getPicante());
                    item.setIngredientes(request.getIngredientes());
                    item.setEspecialidad(request.getEspecialidad());
                    break;
                case bebida:
                    item.setTamaño(request.getTamaño());
                    item.setAlcohol(request.getAlcohol());
                    item.setArtesanal(request.getArtesanal());
                    break;
                case postre:
                    item.setPorcion(request.getPorcion());
                    item.setCalorias(request.getCalorias());
                    item.setDulzura(request.getDulzura());
                    item.setCasero(request.getCasero());
                    break;
            }

            System.out.println("💾 Guardando cambios en base de datos...");
            MenuItem itemActualizado = menuItemRepository.save(item);

            System.out.println("✅ ITEM ACTUALIZADO EXITOSAMENTE: " + itemActualizado.getNombre());
            return ResponseEntity.ok(new ApiResponse(true, "Item actualizado exitosamente", itemActualizado));

        } catch (Exception e) {
            System.out.println("❌ ERROR ACTUALIZANDO ITEM: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Eliminar item del menú
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteMenuItem(@PathVariable Long id) {
        try {
            System.out.println("🗑️ === ELIMINANDO ITEM ID: " + id + " ===");

            Optional<MenuItem> itemOpt = menuItemRepository.findById(id);

            if (itemOpt.isEmpty()) {
                System.out.println("❌ Item no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Item no encontrado"));
            }

            MenuItem item = itemOpt.get();
            System.out.println("📋 Item a eliminar: " + item.getNombre());

            System.out.println("💾 Eliminando de base de datos...");
            menuItemRepository.delete(item);

            System.out.println("✅ ITEM ELIMINADO EXITOSAMENTE: " + item.getNombre());
            return ResponseEntity.ok(new ApiResponse(true, "Item eliminado exitosamente"));

        } catch (Exception e) {
            System.out.println("❌ ERROR ELIMINANDO ITEM: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // Cambiar disponibilidad del item
    @PutMapping("/{id}/disponibilidad")
    @Transactional
    public ResponseEntity<ApiResponse> cambiarDisponibilidad(@PathVariable Long id, @RequestBody CambiarDisponibilidadRequest request) {
        try {
            System.out.println("🔄 === CAMBIANDO DISPONIBILIDAD ITEM ID: " + id + " ===");

            Optional<MenuItem> itemOpt = menuItemRepository.findById(id);

            if (itemOpt.isEmpty()) {
                System.out.println("❌ Item no encontrado con ID: " + id);
                return ResponseEntity.ok(new ApiResponse(false, "Item no encontrado"));
            }

            MenuItem item = itemOpt.get();
            System.out.println("📋 Item: " + item.getNombre() + " (Disponible: " + item.getDisponible() + ")");

            item.setDisponible(request.getDisponible());
            item.setFechaActualizacion(LocalDateTime.now());

            System.out.println("💾 Guardando cambio de disponibilidad...");
            MenuItem itemActualizado = menuItemRepository.save(item);

            System.out.println("✅ DISPONIBILIDAD CAMBIADA: " + itemActualizado.getNombre() + " -> " + itemActualizado.getDisponible());
            return ResponseEntity.ok(new ApiResponse(true, "Disponibilidad actualizada exitosamente", itemActualizado));

        } catch (Exception e) {
            System.out.println("❌ ERROR CAMBIANDO DISPONIBILIDAD: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    // DTOs para las requests
    public static class CreateMenuItemRequest {
        private String nombre;
        private String descripcion;
        private BigDecimal precio;
        private MenuItem.TipoMenu tipo;
        private Boolean disponible = true;
        private String imagenUrl;

        // Campos específicos para comida
        private Integer picante;
        private String ingredientes;
        private Boolean especialidad;

        // Campos específicos para bebidas
        private String tamaño;
        private String alcohol;
        private Boolean artesanal;

        // Campos específicos para postres
        private String porcion;
        private String calorias;
        private Integer dulzura;
        private Boolean casero;

        // Getters y Setters básicos
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public BigDecimal getPrecio() { return precio; }
        public void setPrecio(BigDecimal precio) { this.precio = precio; }

        public MenuItem.TipoMenu getTipo() { return tipo; }
        public void setTipo(MenuItem.TipoMenu tipo) { this.tipo = tipo; }

        public Boolean getDisponible() { return disponible; }
        public void setDisponible(Boolean disponible) { this.disponible = disponible; }

        public String getImagenUrl() { return imagenUrl; }
        public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

        // Getters y Setters para comida
        public Integer getPicante() { return picante; }
        public void setPicante(Integer picante) { this.picante = picante; }

        public String getIngredientes() { return ingredientes; }
        public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }

        public Boolean getEspecialidad() { return especialidad; }
        public void setEspecialidad(Boolean especialidad) { this.especialidad = especialidad; }

        // Getters y Setters para bebidas
        public String getTamaño() { return tamaño; }
        public void setTamaño(String tamaño) { this.tamaño = tamaño; }

        public String getAlcohol() { return alcohol; }
        public void setAlcohol(String alcohol) { this.alcohol = alcohol; }

        public Boolean getArtesanal() { return artesanal; }
        public void setArtesanal(Boolean artesanal) { this.artesanal = artesanal; }

        // Getters y Setters para postres
        public String getPorcion() { return porcion; }
        public void setPorcion(String porcion) { this.porcion = porcion; }

        public String getCalorias() { return calorias; }
        public void setCalorias(String calorias) { this.calorias = calorias; }

        public Integer getDulzura() { return dulzura; }
        public void setDulzura(Integer dulzura) { this.dulzura = dulzura; }

        public Boolean getCasero() { return casero; }
        public void setCasero(Boolean casero) { this.casero = casero; }
    }

    public static class UpdateMenuItemRequest extends CreateMenuItemRequest {
        // Hereda todos los campos de CreateMenuItemRequest
    }

    public static class CambiarDisponibilidadRequest {
        private Boolean disponible;

        public Boolean getDisponible() { return disponible; }
        public void setDisponible(Boolean disponible) { this.disponible = disponible; }
    }

    // Clase de respuesta genérica
    public static class ApiResponse {
        private boolean success;
        private String message;
        private Object data;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public ApiResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }
    }
}