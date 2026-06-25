package com.Proyectochacras.FoodOrganic.dto;

public class ProductorDTO {
    private String nombre;
    private String ubicacion;
    private String tipoProducto;

    // Constructor
    public ProductorDTO(String nombre, String ubicacion, String tipoProducto) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tipoProducto = tipoProducto;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
}
