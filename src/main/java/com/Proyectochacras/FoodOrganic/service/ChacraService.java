package com.Proyectochacras.FoodOrganic.service;

import com.Proyectochacras.FoodOrganic.models.*;
import com.Proyectochacras.FoodOrganic.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ChacraService {

    @Autowired
    private ChacraRepository chacraRepository;

    @Autowired
    private ProductorRepository productorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void crearChacraUsuario(String email, Chacra chacra) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        Productor productor = usuario.getProductor();

        if (productor == null) {
            productor = new Productor();
            productor.setNombreCompleto(usuario.getNombreUsuario());
            productor.setTelefono("");
            productor.setDireccion(chacra.getUbicacion());
            productor.setDescripcion("Perfil comercial de: " + usuario.getNombreUsuario());
            productor = productorRepository.save(productor);

            usuario.setProductor(productor);
            usuario.setRole(Rol.PRODUCTOR);
            usuarioRepository.save(usuario);
        }

        chacra.setProductor(productor);
        if (chacra.getEstadoChacra() == null) {
            chacra.setEstadoChacra(EstadoChacra.DISPONIBLE);
        }
        chacraRepository.save(chacra);
    }

    public List<Chacra> obtenerChacrasActivas() {
        return chacraRepository.findByEstadoChacraNot(EstadoChacra.INACTIVO);
    }

    public List<Chacra> obtenerTodasLasChacras() {
        return chacraRepository.findAll();
    }

    public Chacra obtenerPorId(Long id) {
        return chacraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chacra no encontrada"));
    }

    public Chacra modificarChacra(Long id, Chacra nuevaData) {
        return chacraRepository.findById(id)
                .map(chacra -> {
                    chacra.setNombre(nuevaData.getNombre());
                    chacra.setDescripcion(nuevaData.getDescripcion());
                    chacra.setUbicacion(nuevaData.getUbicacion());
                    chacra.setEstadoChacra(nuevaData.getEstadoChacra());
                    return chacraRepository.save(chacra);
                })
                .orElse(null);
    }

    public boolean eliminarChacra(Long id) {
        return chacraRepository.findById(id)
                .map(chacra -> {
                    chacra.setEstadoChacra(EstadoChacra.INACTIVO);
                    chacraRepository.save(chacra);
                    return true;
                })
                .orElse(false);
    }
}