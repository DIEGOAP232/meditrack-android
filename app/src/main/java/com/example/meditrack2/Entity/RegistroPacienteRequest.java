package com.example.meditrack2.Entity;

public class RegistroPacienteRequest {
    private String nombres;
    private String apellidos;
    private String dni;
    private String correo;
    private String telefono;
    private String sexo;
    private String fechaNacimiento;
    private String contrasena;

    public RegistroPacienteRequest(String nombres, String apellidos,
                                   String dni, String correo, String telefono, String sexo,
                                   String fechaNacimiento, String contrasena) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasena = contrasena;
    }
}