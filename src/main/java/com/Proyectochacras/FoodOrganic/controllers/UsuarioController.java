package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // LOGIN
    @GetMapping("/login")
    public String mostrarLoginForm() {
        return "login"; // Busca login.html en templates
    }

    // REGISTRO (GET)
    @GetMapping("/register")
    public String mostrarRegistroForm(Model model) {
        model.addAttribute("usuario", new Usuario()); // Pasa un objeto vacío al formulario
        return "registro"; // Busca registro.html
    }


    // REGISTRO (POST) en UsuarioController.java
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.saveUsuario(usuario);
            // Redirige al login con un mensaje de éxito
            return "redirect:/login?success";
        } catch (Exception e) {
            // Si hay error (ej: email duplicado), mandamos el mensaje al HTML
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario); // Mantenemos los datos escritos
            return "registro";
        }
    }
}