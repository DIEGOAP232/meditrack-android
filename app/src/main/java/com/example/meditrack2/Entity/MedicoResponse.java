package com.example.meditrack2.Entity;

public class MedicoResponse {
    private Integer idMedico;
    private String dni;
    private String nombres;
    private String apellidos;
    private String codigoProfesional;
    private Integer idEspecialidad;
    private String especialidad;
    private Boolean especialidadPrincipal;

    public Integer getIdMedico() { return idMedico; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCodigoProfesional() { return codigoProfesional; }
    public Integer getIdEspecialidad() { return idEspecialidad; }
    public String getEspecialidad() { return especialidad; }

    @Override
    public String toString() { return "Dr. " + nombres + " " + apellidos; }
}