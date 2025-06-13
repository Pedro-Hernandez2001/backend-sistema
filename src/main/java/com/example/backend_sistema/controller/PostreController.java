package com.example.backend_sistema.controller;


import com.example.backend_sistema.model.Postre;
import com.example.backend_sistema.repository.PostreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postres")
@CrossOrigin(origins = "*") // para permitir peticiones desde cualquier origen (como tu React)
public class PostreController {

    @Autowired
    private PostreRepository postreRepository;

    // Obtener todos los postres
    @GetMapping
    public List<Postre> getAllPostres() {
        return postreRepository.findAll();
    }

    // Opcional: buscar postres por categoría (ejemplo)
    @GetMapping("/categoria/{categoria}")
    public List<Postre> getPostresPorCategoria(@PathVariable String categoria) {
        return postreRepository.findAll().stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .toList();
    }
}
