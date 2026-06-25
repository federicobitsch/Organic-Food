package com.Proyectochacras.FoodOrganic.dto;

public class ChacraDTO {
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String telefono;

    // Constructores, Getters y Setters
    public ChacraDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
