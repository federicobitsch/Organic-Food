package com.Proyectochacras.FoodOrganic.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String texto;

    private LocalDateTime fechaComentario = LocalDateTime.now();

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Blog blog;

    private boolean activo = true; //Por defecto todo es visible

    // Genera sus getters y setters
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
