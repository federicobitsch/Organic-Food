package com.Proyectochacras.FoodOrganic.repositories;


import com.Proyectochacras.FoodOrganic.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//Filtrar la categoria del producto , por ejemplo PRECIO , Cantidad que tiene en Stock .
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
