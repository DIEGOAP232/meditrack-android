package com.example.meditrack2.Network;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "MediTrackSession";
    private static final String KEY_TOKEN = "accessToken";
    private static final String KEY_USER_ID = "idUsuario";
    private static final String KEY_ROLE = "rol";

    private final SharedPreferences preferences;

    public SessionManager(Context context) {
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void guardarSesion(String token, int idUsuario, String rol) {
        preferences.edit()
                .putString(KEY_TOKEN, token)
                .putInt(KEY_USER_ID, idUsuario)
                .putString(KEY_ROLE, rol)
                .apply();
    }

    public String obtenerToken() {
        return preferences.getString(KEY_TOKEN, null);
    }

    public int obtenerIdUsuario() {
        return preferences.getInt(KEY_USER_ID, -1);
    }

    public String obtenerRol() {
        return preferences.getString(KEY_ROLE, null);
    }

    public boolean existeSesion() {
        String token = obtenerToken();
        return token != null && !token.isBlank();
    }

    public void cerrarSesion() {
        preferences.edit().clear().apply();
    }
}
