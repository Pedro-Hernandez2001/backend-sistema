package com.example.backend_sistema.controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.backend_sistema.model.Platillo;
import com.example.backend_sistema.repository.PlatilloRepository;
import com.example.backend_sistema.service.PlatilloService;
@RestController
@RequestMapping("/api/platillos")
@CrossOrigin(origins = "*") // Cambia este puerto si usas otro en React
public class PlatilloController {

    private final PlatilloService platilloService;

    public PlatilloController(PlatilloService platilloService) {
        this.platilloService = platilloService;
    }

    @GetMapping
    public Map<String, List<Platillo>> obtenerPlatillos() {
        Map<String, List<Platillo>> respuesta = new HashMap<>();
        respuesta.put("antojitos", platilloService.getPlatillosByCategoria("antojitos"));
        respuesta.put("platillosPrincipales", platilloService.getPlatillosByCategoria("platillosPrincipales"));
        respuesta.put("caldos", platilloService.getPlatillosByCategoria("caldos"));
        return respuesta;
    }
}
