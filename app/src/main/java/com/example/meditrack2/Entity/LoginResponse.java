
package com.example.meditrack2.Entity;

public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private UsuarioResponse usuario;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }
}
