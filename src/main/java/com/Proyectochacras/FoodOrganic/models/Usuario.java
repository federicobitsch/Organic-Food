package com.Proyectochacras.FoodOrganic.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;


@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol role = Rol.USUARIO;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "productor_id", referencedColumnName = "id")
    private Productor productor;

    private String nombreUsuario = "";
    private String bio = "";
    private LocalDate fechaRegistro;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String fotoPerfil;

    @PrePersist
    public void asignarFechaRegistro() {
        this.fechaRegistro = LocalDate.now();
    }

    public Usuario() {}

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRole() { return role; }
    public void setRole(Rol role) { this.role = role; }
    public Productor getProductor() { return productor; }
    public void setProductor(Productor productor) { this.productor = productor; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }
}