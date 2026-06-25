package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMINISTRADOR')") // Unifica con tu SecurityConfig
public class AdminDashboardController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin/dashboard")
    public String verDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario adminActual = usuarioService.buscarPorEmail(userDetails.getUsername());
        model.addAttribute("usuario", adminActual);
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "admin/dashboard";
    }
}