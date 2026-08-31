package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.models.Chacra;
import com.Proyectochacras.FoodOrganic.service.ChacraService;
import com.Proyectochacras.FoodOrganic.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChacraViewController {

    @Autowired
    private ChacraService chacraService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/chacra/{id}")
    public String verDetalles(@PathVariable Long id, Model model){
        Chacra chacra = chacraService.obtenerPorId(id);
        if(chacra == null){
            return "redirect:/publicaciones?error=notfound";
        }

        model.addAttribute("chacra",chacra);
        return "chacra-detalle";
    }
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