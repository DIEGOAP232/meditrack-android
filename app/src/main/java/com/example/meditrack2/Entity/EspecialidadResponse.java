package com.example.meditrack2.Entity;

public class EspecialidadResponse {
    private Integer idEspecialidad;
    private String nombre;
    private String descripcion;
    private String estado;

    public Integer getIdEspecialidad() { return idEspecialidad; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }

    @Override
    public String toString() { return nombre; }
}