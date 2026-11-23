
package com.example.meditrack2.Entity;

public class LoginRequest {

    private String identificador;
    private String contrasena;

    public LoginRequest(String identificador, String contrasena) {
        this.identificador = identificador;
        this.contrasena = contrasena;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getContrasena() {
        return contrasena;
    }
}
