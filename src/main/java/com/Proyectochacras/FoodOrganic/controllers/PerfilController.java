package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.dto.UsuarioDTO;
import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String mostrarPerfil(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login"; // Seguridad: si no hay sesión, al login
        }

        // Buscamos al usuario real en la base de datos por su email (username)
        Usuario usuarioActual = usuarioService.buscarPorEmail(userDetails.getUsername());

        // ESTA ES LA LÍNEA CLAVE: El nombre "usuario" debe coincidir con tu HTML
        model.addAttribute("usuario", usuarioActual);

        return "perfil";
    }

    @GetMapping("/datos")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerDatosPerfil(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.buscarPorEmail(userDetails.getUsername());
        Map<String, Object> datos = new HashMap<>();

        datos.put("username", usuario.getNombreUsuario());
        datos.put("email", usuario.getEmail());
        datos.put("bio", usuario.getBio());
        datos.put("rol", usuario.getRole().name());
        datos.put("fechaRegistro", usuario.getFechaRegistro().toString());

        // Si fotoPerfil es null en la DB, enviamos la ruta de tu avatar por defecto
        String foto = (usuario.getFotoPerfil() != null) ? usuario.getFotoPerfil() : "/imagenes/avatar-session.jpg";
        datos.put("profilePic", foto);

        return ResponseEntity.ok(datos);
    }

    // Datos para el nombre y foto en el navbar
    @GetMapping("/navbar")
    @ResponseBody
    public ResponseEntity<UsuarioDTO> obtenerDatosNavbar(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(usuarioService.obtenerDatosUsuarioPorEmail(userDetails.getUsername()));
    }


    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<?> actualizarPerfil(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestParam("username") String username,
                                              @RequestParam("bio") String bio,
                                              @RequestParam(value = "profilePic", required = false) MultipartFile profilePic) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(userDetails.getUsername());
            usuario.setNombreUsuario(username);
            usuario.setBio(bio);

            if (profilePic != null && !profilePic.isEmpty()) {
                String imagenBase64 = Base64.getEncoder().encodeToString(profilePic.getBytes());
                // Guardamos con el prefijo para que el navegador la lea como imagen
                usuario.setFotoPerfil("data:" + profilePic.getContentType() + ";base64," + imagenBase64);
            }

            usuarioService.actualizarPerfilDatos(usuario);
            return ResponseEntity.ok().body("{\"message\": \"Perfil actualizado\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}