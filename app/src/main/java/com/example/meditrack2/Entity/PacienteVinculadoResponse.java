package com.example.meditrack2.Entity;

public class PacienteVinculadoResponse {

    private Integer idPaciente;
    private String codigoPaciente;
    private String nombres;
    private String apellidos;
    private String estadoVinculo;

    public Integer getIdPaciente() { return idPaciente; }
    public String getCodigoPaciente() { return codigoPaciente; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getEstadoVinculo() { return estadoVinculo; }

    @Override
    public String toString() { return nombres + " " + apellidos; }
}