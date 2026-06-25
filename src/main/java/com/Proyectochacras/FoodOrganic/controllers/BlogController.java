package com.Proyectochacras.FoodOrganic.controllers;
import com.Proyectochacras.FoodOrganic.models.Blog;
import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.service.BlogService;
import com.Proyectochacras.FoodOrganic.service.ComentarioService;
import com.Proyectochacras.FoodOrganic.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("blogs", blogService.listarActivos());
        return "blog/listar";
    }


    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model, Principal principal) {
        Blog blog = blogService.getBlog(id);
        model.addAttribute("blog", blog);
        model.addAttribute("comentarios", blog.getComentarios());

        // CARGA EL USUARIO PARA EL NAVBAR Y LAS VALIDACIONES DE BORRADO
        if (principal != null) {
            Usuario usuarioActual = usuarioService.buscarPorEmail(principal.getName());
            model.addAttribute("usuario", usuarioActual);
        }

        return "blog/detalle";
    }

    @PostMapping("/{id}/comentar")
    public String comentar(@PathVariable Long id, @RequestParam String texto, Principal principal) {
        if (principal == null) return "redirect:/login";

        Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
        comentarioService.crearComentario(id, usuario, texto);
        return "redirect:/blog/" + id;
    }
}