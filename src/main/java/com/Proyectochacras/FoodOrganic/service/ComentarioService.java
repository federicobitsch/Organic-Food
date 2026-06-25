package com.Proyectochacras.FoodOrganic.service;

import com.Proyectochacras.FoodOrganic.models.Blog;
import com.Proyectochacras.FoodOrganic.models.Comentario;
import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.repositories.BlogRepository;
import com.Proyectochacras.FoodOrganic.repositories.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private BlogRepository blogRepository;


    public Comentario getComentario(Long id) {
        return comentarioRepository.findById(id).orElse(null);
    }

    public void crearComentario(Long blogId, Usuario usuario, String texto) {
        Blog blog = blogRepository.findById(blogId).orElse(null);
        if (blog != null && usuario != null) {
            Comentario c = new Comentario();
            c.setTexto(texto);
            c.setUsuario(usuario);
            c.setBlog(blog);
            // c.setActivo(true); // Opcional: para borrado lógico
            comentarioRepository.save(c);
        }
    }

    public void eliminarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }

}