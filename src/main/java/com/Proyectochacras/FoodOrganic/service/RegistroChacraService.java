package com.Proyectochacras.FoodOrganic.service;

import com.Proyectochacras.FoodOrganic.models.*;
import com.Proyectochacras.FoodOrganic.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroChacraService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductorRepository productorRepository;

    @Autowired
    private ChacraRepository chacraRepository;

    @Transactional
    public Chacra registrarChacraYProductor(Long usuarioId, String nombreChacra, String ubicacion, String telefono, String descripcionChacra) {
        // 1. Validar la existencia del usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));

        // 2. Si el usuario no tiene perfil de productor, lo creamos
        Productor productor = usuario.getProductor();
        if (productor == null) {
            productor = new Productor();
            productor.setNombreCompleto(usuario.getNombreUsuario());
            productor.setTelefono(telefono);
            productor.setDireccion(ubicacion);
            productor.setDescripcion("Perfil comercial de: " + usuario.getNombreUsuario());
            productor.setUsuario(usuario);

            // Guardamos el productor en la base de datos
            productor = productorRepository.save(productor);

            // Vinculamos y promovemos el Rol a PRODUCTOR
            usuario.setProductor(productor);
            usuario.setRole(Rol.PRODUCTOR);
            usuarioRepository.save(usuario);
        }

        // 3. Crear y asociar la Chacra al Productor
        Chacra nuevaChacra = new Chacra();
        nuevaChacra.setNombre(nombreChacra);
        nuevaChacra.setUbicacion(ubicacion);
        nuevaChacra.setDescripcion(descripcionChacra);
        nuevaChacra.setEstadoChacra(EstadoChacra.PENDIENTE);
        nuevaChacra.setProductor(productor);

        return chacraRepository.save(nuevaChacra);
    }
}