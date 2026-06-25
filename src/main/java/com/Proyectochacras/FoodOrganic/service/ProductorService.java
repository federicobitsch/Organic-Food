package com.Proyectochacras.FoodOrganic.service;

import com.Proyectochacras.FoodOrganic.models.Productor;
import com.Proyectochacras.FoodOrganic.repositories.ProductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductorService {

    @Autowired
    private ProductorRepository productorRepository;

    public List<Productor> obtenerTodosLosProductores() {
        return productorRepository.findAll();
    }

    // Crear un perfil de productor vacío (los datos de login se manejan desde Usuario)
    public Productor crearProductor(Productor productor) {
        return productorRepository.save(productor);
    }

    @Transactional
    public void modificarProductor(Long id, Productor productor) throws Exception {
        Productor productorExistente = productorRepository.findById(id)
                .orElseThrow(() -> new Exception("El productor con ID " + id + " no existe"));

        // Modificamos exclusivamente datos comerciales y de contacto comercial en Río Grande
        productorExistente.setNombreCompleto(productor.getNombreCompleto());
        productorExistente.setTelefono(productor.getTelefono());
        productorExistente.setDescripcion(productor.getDescripcion());
        productorExistente.setDireccion(productor.getDireccion());

        productorRepository.save(productorExistente);
    }

    public void eliminarProductor(Long id) throws Exception {
        if (!productorRepository.existsById(id)) {
            throw new Exception("El productor con ID " + id + " no existe");
        }
        productorRepository.deleteById(id);
    }

    public Optional<Productor> obtenerProductorPorId(Long id) {
        return productorRepository.findById(id);
    }
}