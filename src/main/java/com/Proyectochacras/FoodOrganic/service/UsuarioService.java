package com.Proyectochacras.FoodOrganic.service;

import com.Proyectochacras.FoodOrganic.dto.UsuarioDTO;
import com.Proyectochacras.FoodOrganic.models.Rol;
import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncode;

    public Usuario saveUsuario(Usuario usuario) throws Exception {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new Exception("El correo electrónico " + usuario.getEmail() + " ya está registrado.");
        }

        usuario.setPassword(passwordEncode.encode(usuario.getPassword()));

        // EVITAMOS NULLS: Si al registrarse no hay datos, inicializamos con strings vacíos
        if (usuario.getNombreUsuario() == null) usuario.setNombreUsuario("Nuevo Usuario");
        if (usuario.getBio() == null) usuario.setBio("");
        if (usuario.getFotoPerfil() == null) usuario.setFotoPerfil(""); // Opcional: ruta de avatar por defecto

        if (usuario.getRole() == null) {
            usuario.setRole(Rol.USUARIO);
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public UsuarioDTO obtenerDatosUsuarioPorEmail(String email) {
        Usuario user = usuarioRepository.findByEmail(email).orElse(null);
        if (user == null) return null;

        // Mapeo seguro: Si fotoPerfil es null/vacío, enviamos el avatar por defecto al DTO
        String foto = (user.getFotoPerfil() != null && !user.getFotoPerfil().isEmpty())
                ? user.getFotoPerfil() : "/imagenes/avatar-session.jpg";

        return new UsuarioDTO(
                user.getNombreUsuario(),
                user.getEmail(),
                user.getBio(),
                foto,
                user.getFechaRegistro() != null ? user.getFechaRegistro().toString() : "",
                user.getRole().toString()
        );
    }

    public void actualizarPerfil(String email, UsuarioDTO dto) {
        usuarioRepository.findByEmail(email).ifPresent(user -> {
            user.setNombreUsuario(dto.getUsername());
            user.setBio(dto.getBio());

            if (dto.getProfilePic() != null && !dto.getProfilePic().isEmpty()) {
                user.setFotoPerfil(dto.getProfilePic());
            }
            usuarioRepository.save(user);
        });
    }

    public void actualizarPerfilDatos(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void crearAdmin() {
        if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombreUsuario("Administrador"); // Evitamos el NULL
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncode.encode("admin123"));
            admin.setRole(Rol.ADMINISTRADOR);
            admin.setBio("Cuenta oficial de Organic Food"); // Evitamos el NULL
            usuarioRepository.save(admin);
        }
    }
}