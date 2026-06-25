package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.models.Comentario;
import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.service.ComentarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.Proyectochacras.FoodOrganic.service.UsuarioService;

import java.security.Principal;
@Controller
@RequestMapping("/comentario")
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear/{blogId}")
    public String crear(@PathVariable Long blogId, @RequestParam String texto, Principal principal) {
        if (principal == null) return "redirect:/login";
        Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
        comentarioService.crearComentario(blogId, usuario, texto);
        return "redirect:/blog/" + blogId;
    }

    @PostMapping("/user/eliminar/{id}")
    public String usuarioEliminaSuPropioComentario(@PathVariable Long id, Principal principal) {
        // Obtenemos el comentario para saber a qué blog pertenece antes de borrarlo
        Comentario c = comentarioService.getComentario(id);

        if (c != null && principal != null) {
            // Verificamos que el email del dueño coincida con el usuario logueado
            if (c.getUsuario().getEmail().equals(principal.getName())) {
                comentarioService.eliminarComentario(id);
                return "redirect:/blog/" + c.getBlog().getId() + "?deleted=true";
            }
        }
        return "redirect:/blog/" + (c != null ? c.getBlog().getId() : "");
    }
}