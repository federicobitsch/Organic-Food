package com.Proyectochacras.FoodOrganic.repositories;

import com.Proyectochacras.FoodOrganic.models.Chacra;
import com.Proyectochacras.FoodOrganic.models.EstadoChacra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChacraRepository extends JpaRepository<Chacra, Long> {
    // Filtra las chacras que no están inactivas
    List<Chacra> findByEstadoChacraNot(EstadoChacra estadoChacra);
}