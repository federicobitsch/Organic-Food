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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Controller
@RequestMapping("/admin/blogs")
public class AdminBlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ComentarioService comentarioService;


    @GetMapping
    public String adminListar(Model model, Principal principal) {

        Usuario user = usuarioService.buscarPorEmail(principal.getName());
        model.addAttribute("usuario", user);

        // Pasamos la lista de blogs
        model.addAttribute("blogs", blogService.listarTodos());
        return "admin/blog-listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("blog", new Blog());
        return "admin/blog-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Blog blog,
                          @RequestParam(value = "archivoImagen", required = false) MultipartFile archivo,
                          Principal principal) {
        try {
            Usuario autor = usuarioService.buscarPorEmail(principal.getName());
            blog.setAutor(autor);

            if (archivo != null && !archivo.isEmpty()) {
                //Se sube la foto y la convierto en Base64
                String imagenBase64 = Base64.getEncoder().encodeToString(archivo.getBytes());
                blog.setImagenUrl("data:" + archivo.getContentType() + ";base64," + imagenBase64);
            } else if (blog.getId() != null) {
                //Es una EDICION la recuperamos la imagen que ya tenia .
                Blog blogExistente = blogService.obtenerPorId(blog.getId());
                blog.setImagenUrl(blogExistente.getImagenUrl());
            }
            blogService.guardar(blog);
            return "redirect:/admin/blogs";
        } catch(IOException e){
            return "redirect:/admin/blogs?error=upload";
        }
    }


    // Mostrar formulario para editar
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlog(id);
        if (blog == null) return "redirect:/admin/blogs?error=notfound";

        model.addAttribute("blog", blog);
        return "admin/blog-form"; // Asegúrate que el archivo esté en templates/admin/blog-form.html
    }

    // Procesar la edición (POST)
    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Blog blog) {
        blog.setId(id); // Aseguramos que se mantenga el mismo ID
        blogService.guardar(blog);
        return "redirect:/admin/blogs?success=updated";
    }

    // Borrado Lógico de un Blog
    @PostMapping("/eliminar/{id}")
    public String eliminarBlog(@PathVariable Long id) {
        blogService.eliminarLogico(id);
        return "redirect:/admin/blogs";
    }

    @PostMapping("/eliminar-comentario/{id}")
    public String eliminarComentario(@PathVariable Long id, @RequestParam Long blogId) {
        comentarioService.eliminarComentario(id);
        // Redirige al detalle del blog en la vista pública o al panel admin
        return "redirect:/blog/" + blogId;
    }
}