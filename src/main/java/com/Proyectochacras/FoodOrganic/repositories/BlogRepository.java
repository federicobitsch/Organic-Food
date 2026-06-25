package com.Proyectochacras.FoodOrganic.repositories;

import com.Proyectochacras.FoodOrganic.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
}
