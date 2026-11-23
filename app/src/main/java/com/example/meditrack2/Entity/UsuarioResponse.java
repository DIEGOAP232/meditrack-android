
package com.example.meditrack2.Entity;

public class UsuarioResponse {

    private Integer idUsuario;
    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;
    private String rol;
    private String estado;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getDni() {
        return dni;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRol() {
        return rol;
    }

    public String getEstado() {
        return estado;
    }
}
