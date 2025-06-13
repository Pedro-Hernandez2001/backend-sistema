package com.example.backend_sistema.controller;


import com.example.backend_sistema.model.DetalleOrden;
import com.example.backend_sistema.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalle_orden")
@CrossOrigin(origins = "*") // Ajusta tu puerto de React si es diferente
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @PostMapping
    public DetalleOrden crearDetalle(@RequestBody DetalleOrden detalle) {
        return detalleOrdenRepository.save(detalle);
    }
}