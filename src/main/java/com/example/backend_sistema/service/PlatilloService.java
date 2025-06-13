package com.example.backend_sistema.service;

import org.springframework.stereotype.Service;
import com.example.backend_sistema.model.Platillo;
import com.example.backend_sistema.repository.PlatilloRepository;
import com.example.backend_sistema.service.PlatilloService;
import java.util.List;

@Service
public class PlatilloService {

    private final PlatilloRepository platilloRepository;

    public PlatilloService(PlatilloRepository platilloRepository) {
        this.platilloRepository = platilloRepository;
    }

    public List<Platillo> getPlatillosByCategoria(String categoria) {
        return platilloRepository.findByCategoria(categoria);
    }
}
