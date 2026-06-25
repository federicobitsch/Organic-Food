package com.Proyectochacras.FoodOrganic.service;

import com.Proyectochacras.FoodOrganic.models.Blog;
import com.Proyectochacras.FoodOrganic.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> listarTodos() {
        return blogRepository.findAll();
    }

    public List<Blog> listarActivos() {
        // Por ahora devolvemos todos para que no veas la pantalla vacía
        return blogRepository.findAll();
    }

    public Blog obtenerPorId(Long id){
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BLOG no encontrado"));
    }

    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public Blog guardar(Blog blog) {
        return blogRepository.save(blog);
    }

    public void eliminarLogico(Long id) {
        blogRepository.deleteById(id);
    }
}