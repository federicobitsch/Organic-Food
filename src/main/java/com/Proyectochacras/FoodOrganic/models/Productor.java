package com.Proyectochacras.FoodOrganic.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productores")
public class Productor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "productor")
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "productor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chacra> chacras = new ArrayList<>();

    private String nombreCompleto;
    private String telefono;
    private String descripcion;
    private String direccion;

    public Productor() {}

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<Chacra> getChacras() { return chacras; }
    public void setChacras(List<Chacra> chacras) { this.chacras = chacras; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}