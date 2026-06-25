package com.Proyectochacras.FoodOrganic.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "chacras")
public class Chacra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @Column(length = 1500)
    private String descripcion;

    private String ubicacion;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagenPrincipal;


    @Enumerated(EnumType.STRING)
    @Column(name = "estado_chacra")
    private EstadoChacra estadoChacra = EstadoChacra.DISPONIBLE;

    @ManyToOne
    @JoinColumn(name = "productor_id")
    @JsonBackReference
    private Productor productor;

    public Chacra() {}

    // getters y setters sincronizados
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getImagenPrincipal() { return imagenPrincipal; }
    public void setImagenPrincipal(String imagenPrincipal) { this.imagenPrincipal = imagenPrincipal; }

    // CORREGIDO: El getter y setter ahora coinciden exactamente con el nombre 'estadoChacra'
    public EstadoChacra getEstadoChacra() { return estadoChacra; }
    public void setEstadoChacra(EstadoChacra estadoChacra) { this.estadoChacra = estadoChacra; }

    public Productor getProductor() { return productor; }
    public void setProductor(Productor productor) { this.productor = productor; }
}