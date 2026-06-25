package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.service.ChacraService;
import com.Proyectochacras.FoodOrganic.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChacraViewController {

    @Autowired
    private ChacraService chacraService;

    @Autowired
    private UsuarioService usuarioService;

    // Mantenemos la URL /publicaciones para no romper tus links del HTML
    @GetMapping("/publicaciones")
    public String publicacionesUsuario(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        // Enviamos el usuario al HTML para que el NAV muestre la foto y el nombre
        if (userDetails != null) {
            model.addAttribute("usuario", usuarioService.buscarPorEmail(userDetails.getUsername()));
        }

        // CORREGIDO: Llamamos al método nuevo del ChacraService
        model.addAttribute("publicaciones", chacraService.obtenerChacrasActivas());
        return "publicaciones";
    }
}