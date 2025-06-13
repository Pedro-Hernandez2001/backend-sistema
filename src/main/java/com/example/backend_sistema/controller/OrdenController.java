package com.example.backend_sistema.controller;

import com.example.backend_sistema.model.Orden;
import com.example.backend_sistema.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenRepository ordenRepository;

    // POST - Crear nueva orden
    @PostMapping
    public ResponseEntity<?> crearOrden(@RequestBody Orden orden) {
        try {
            System.out.println("Datos recibidos: " + orden);
            orden.setEstado(Orden.Estado.pendiente);
            orden.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            Orden ordenGuardada = ordenRepository.save(orden);
            return ResponseEntity.ok(ordenGuardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear orden");
        }
    }

    // GET - Listar todas las órdenes
    @GetMapping
    public ResponseEntity<List<Orden>> obtenerTodasLasOrdenes() {
        try {
            List<Orden> ordenes = ordenRepository.findAll();
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{ordenId}/estado")
    public ResponseEntity<?> actualizarEstadoOrden(@PathVariable Integer ordenId, @RequestBody Orden ordenActualizada) {
        try {
            Orden orden = ordenRepository.findById(ordenId).orElse(null);
            if (orden == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orden no encontrada");
            }

            orden.setEstado(ordenActualizada.getEstado());
            ordenRepository.save(orden);
            return ResponseEntity.ok("Estado de la orden actualizado a " + orden.getEstado());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la orden");
        }
    }
}
