package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.models.Chacra;
import com.Proyectochacras.FoodOrganic.service.ChacraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/publicaciones")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AdminPublicacionController {

    @Autowired
    private ChacraService chacraService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("publicaciones", chacraService.obtenerTodasLasChacras());
        return "admin/admin-publicaciones";
    }

    @PostMapping("/editar/{id}")
    public String guardarEdicion(@PathVariable Long id, Chacra chacra) {
        // CORREGIDO: Llamaba a modificarPublicacion, ahora llama a modificarChacra
        chacraService.modificarChacra(id, chacra);
        return "redirect:/admin/publicaciones";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("publicacion", chacraService.obtenerPorId(id));
        return "admin/editar-publicacion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        chacraService.eliminarChacra(id);
        return "redirect:/admin/publicaciones";
    }
}