package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.models.Chacra;
// ATENCIÓN: Si tu carpeta se llama "service" sin la 's' al final, sacale la 's' a la palabra "services" acá abajo:
import com.Proyectochacras.FoodOrganic.service.ChacraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones") // Mantenemos esta URL para que tu JavaScript siga funcionando perfecto
public class ChacraController {

    @Autowired
    private ChacraService chacraService;

    @GetMapping
    public List<Chacra> listarParaJs() {
        // CORREGIDO
        return chacraService.obtenerChacrasActivas();
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crear(@RequestBody Chacra chacra,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // CORREGIDO: Promoción automática de rol al crear la chacra
            chacraService.crearChacraUsuario(userDetails.getUsername(), chacra);
            return ResponseEntity.ok("Chacra creada y perfil promovido a Productor correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> modificar(@PathVariable Long id, @RequestBody Chacra chacra) {
        // CORREGIDO
        Chacra result = chacraService.modificarChacra(id, chacra);
        if (result != null) {
            return ResponseEntity.ok("Chacra modificada correctamente");
        }
        return ResponseEntity.status(404).body("Chacra no encontrada");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        // CORREGIDO
        if (chacraService.eliminarChacra(id)) {
            return ResponseEntity.ok("Chacra eliminada de forma lógica correctamente");
        }
        return ResponseEntity.status(404).body("Chacra no encontrada");
    }
}