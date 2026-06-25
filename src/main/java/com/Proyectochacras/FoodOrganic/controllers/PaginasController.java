package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class PaginasController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String mostrarIndex() {
        return "index"; //
    }

    @GetMapping("/contactoSoporte")
    public String mostrarContacto() {
        return "contactoSoporte";
    }

    @GetMapping("/productosOrganicos")
    public String mostrarProductosOrganicos(){
        return "productosOrganicos";
    }

    @GetMapping("/index")
    public String indexPage(Model model, Principal principal) {

        if (principal != null) {

            String email = principal.getName();

            Usuario usuario = usuarioService.buscarPorEmail(email);

            if (usuario != null) {
                model.addAttribute("usuario", usuario);
            }
        }

        return "index";
    }
}
