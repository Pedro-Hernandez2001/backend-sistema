package com.example.backend_sistema.controller;

import com.example.backend_sistema.model.Bebida;
import com.example.backend_sistema.repository.BebidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bebidas")
@CrossOrigin(origins = "*") // permite que React se conecte
public class BebidaController {

    @Autowired
    private BebidaRepository bebidaRepository;
    @GetMapping
    public List<Bebida> obtenerBebidas() {
        return bebidaRepository.findAll();
    }


}
