package com.Proyectochacras.FoodOrganic.repositories;

import com.Proyectochacras.FoodOrganic.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    // Solo trae comentarios que no han sido borrados lógicamente
    List<Comentario> findByBlogIdAndActivoTrue(Long blogId);
}