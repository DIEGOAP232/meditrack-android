package com.example.meditrack2.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meditrack2.Entity.LoginRequest;
import com.example.meditrack2.Entity.LoginResponse;
import com.example.meditrack2.Entity.UsuarioResponse;

import com.example.meditrack2.Inicio.inicio_PacienteActivity;
import com.example.meditrack2.Inicio.inicio_familiar;
import com.example.meditrack2.Network.ApiClient;
import com.example.meditrack2.Network.AuthApi;
import com.example.meditrack2.Network.SessionManager;
import com.example.meditrack2.R;
import com.example.meditrack2.Registro.Tipo_de_usuario;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    // CAMPOS DEL LOGIN
    private TextInputEditText txtDniCorreo;
    private TextInputEditText txtContrasenaLogin;

    // BOTONES PRINCIPALES
    private Button btnIniciarsesion;
    private TextView registrarse;

    // ACCESO RÁPIDO: GOOGLE Y APPLE
    private ImageView btnGoogle;
    private ImageView btnApple;

    // [NUEVO] SERVICIOS DE AUTENTICACIÓN Y SESIÓN
    private AuthApi authApi;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // CONFIGURACIÓN DE PANTALLA
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // INICIALIZAR CAMPOS
        inicializarCampos();

        // [NUEVO] INICIALIZAR CONEXIÓN CON EL BACKEND
        authApi = ApiClient.getClient().create(AuthApi.class);

        // [NUEVO] INICIALIZAR ADMINISTRADOR DE SESIÓN
        sessionManager = new SessionManager(getApplicationContext());

        // EVENTO: INICIAR SESIÓN
        btnIniciarsesion.setOnClickListener(
                v -> iniciarSesion()
        );

        // EVENTO: REGISTRARSE
        registrarse.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    Tipo_de_usuario.class
            );

            startActivity(intent);
        });

        // ACCESO RÁPIDO: GOOGLE
        btnGoogle.setOnClickListener(
                v -> iniciarSesionGoogle()
        );

        // ACCESO RÁPIDO: APPLE
        btnApple.setOnClickListener(
                v -> iniciarSesionApple()
        );
    }

    // INICIALIZAR CAMPOS
    private void inicializarCampos() {

        txtDniCorreo = findViewById(R.id.txtDniCorreo);
        txtContrasenaLogin = findViewById(R.id.txtContrasenaLogin);

        btnIniciarsesion = findViewById(R.id.btnIniciarsesion);
        registrarse = findViewById(R.id.registrarce);

        btnGoogle = findViewById(R.id.imageView7);
        btnApple = findViewById(R.id.imageView8);
    }

    // ==========================================
    // INICIAR SESIÓN MEDIANTE QUARKUS
    // ==========================================

    private void iniciarSesion() {

        String identificador = obtenerTexto(txtDniCorreo);

        // [NUEVO] No modificar la contraseña eliminando espacios.
        String contrasena = txtContrasenaLogin.getText() == null
                ? ""
                : txtContrasenaLogin.getText().toString();

        // VALIDAR IDENTIFICADOR
        if (identificador.isEmpty()) {

            txtDniCorreo.setError("Ingresa tu DNI o correo");
            txtDniCorreo.requestFocus();
            return;
        }

        // VALIDAR CONTRASEÑA
        if (contrasena.isBlank()) {

            txtContrasenaLogin.setError("Ingresa tu contraseña");
            txtContrasenaLogin.requestFocus();
            return;
        }

        // [NUEVO] EVITAR ENVÍOS REPETIDOS
        btnIniciarsesion.setEnabled(false);
        btnIniciarsesion.setText("Iniciando sesión...");

        // [NUEVO] CONSTRUIR SOLICITUD
        LoginRequest request = new LoginRequest(
                identificador,
                contrasena
        );

        // [NUEVO] ENVIAR SOLICITUD A QUARKUS
        authApi.login(request).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(
                    Call<LoginResponse> call,
                    Response<LoginResponse> response) {

                btnIniciarsesion.setEnabled(true);
                btnIniciarsesion.setText("Iniciar sesión");

                // AUTENTICACIÓN CORRECTA
                if (response.isSuccessful() && response.body() != null) {

                    LoginResponse loginResponse = response.body();

                    UsuarioResponse usuario = loginResponse.getUsuario();

                    // VALIDAR RESPUESTA DEL SERVIDOR
                    if (loginResponse.getAccessToken() == null
                            || loginResponse.getAccessToken().isBlank()
                            || usuario == null
                            || usuario.getIdUsuario() == null
                            || usuario.getRol() == null) {

                        mostrarError(
                                "El servidor devolvió una respuesta incompleta."
                        );
                        return;
                    }

                    // GUARDAR SESIÓN
                    sessionManager.guardarSesion(
                            loginResponse.getAccessToken(),
                            usuario.getIdUsuario(),
                            usuario.getRol()
                    );

                    // MOSTRAR BIENVENIDA Y REDIRIGIR
                    mostrarInicioExitoso(usuario);

                } else {

                    // PROCESAR ERRORES DEL BACKEND
                    procesarError(response);
                }
            }

            @Override
            public void onFailure(
                    Call<LoginResponse> call,
                    Throwable t) {

                btnIniciarsesion.setEnabled(true);
                btnIniciarsesion.setText("Iniciar sesión");

                Toast.makeText(
                        LoginActivity.this,
                        "No se pudo conectar con MediTrack.",
                        Toast.LENGTH_LONG
                ).show();

                android.util.Log.e(
                        "LOGIN_MEDTRACK",
                        "Error de conexión con el backend",
                        t
                );
            }
        });
    }

    // ==========================================
    // PROCESAR ERRORES DEL BACKEND
    // ==========================================

    private void procesarError(Response<LoginResponse> response) {

        String mensaje;

        switch (response.code()) {

            case 400:
                mensaje = "Los datos enviados no son válidos.";
                break;

            case 401:
                mensaje = "DNI, correo o contraseña incorrectos.";
                break;

            case 403:
                mensaje = "No tienes permiso para acceder desde este portal.";
                break;

            case 500:
                mensaje = "Error interno del servidor.";
                break;

            default:
                mensaje = "No se pudo iniciar sesión. Código: "
                        + response.code();
                break;
        }

        // Intentar obtener el mensaje original del backend
        ResponseBody errorBody = response.errorBody();

        if (errorBody != null) {
            try {
                android.util.Log.w(
                        "LOGIN_MEDTRACK",
                        "Respuesta del servidor: " + errorBody.string()
                );
            } catch (java.io.IOException e) {
                android.util.Log.e(
                        "LOGIN_MEDTRACK",
                        "No se pudo leer el error del servidor",
                        e
                );
            }
        }

        mostrarError(mensaje);
    }

    // ==========================================
    // REDIRECCIÓN SEGÚN EL ROL
    // ==========================================

    private void mostrarInicioExitoso(UsuarioResponse usuario) {

        String nombre = usuario.getNombres() == null
                ? "Usuario"
                : usuario.getNombres();

        new AlertDialog.Builder(this)
                .setTitle("Bienvenido")
                .setMessage(
                        "Hola " + nombre
                                + ", has iniciado sesión correctamente."
                )
                .setPositiveButton("Continuar", (dialog, which) -> {

                    Intent intent = null;

                    String rol = usuario.getRol();

                    // INICIO DEL PACIENTE
                    if ("PACIENTE".equalsIgnoreCase(rol)) {

                        intent = new Intent(
                                LoginActivity.this,
                                inicio_PacienteActivity.class
                        );

                        // INICIO DEL TUTOR
                    } else if ("TUTOR".equalsIgnoreCase(rol)) {

                        intent = new Intent(
                                LoginActivity.this,
                                inicio_familiar.class
                        );
                    }

                    if (intent != null) {

                        startActivity(intent);
                        finish();

                    } else {

                        // ROL NO CONFIGURADO
                        sessionManager.cerrarSesion();

                        mostrarError(
                                "Todavía no está configurado el inicio "
                                        + "para este tipo de usuario."
                        );
                    }
                })
                .setCancelable(false)
                .show();
    }

    // ==========================================
    // ACCESO RÁPIDO: GOOGLE
    // ==========================================

    private void iniciarSesionGoogle() {

        Toast.makeText(
                this,
                "Google: falta configurar la autenticación.",
                Toast.LENGTH_LONG
        ).show();
    }

    // ==========================================
    // ACCESO RÁPIDO: APPLE
    // ==========================================

    private void iniciarSesionApple() {

        Toast.makeText(
                this,
                "Apple: falta configurar la autenticación.",
                Toast.LENGTH_LONG
        ).show();
    }

    // ==========================================
    // MOSTRAR MENSAJES DE ERROR
    // ==========================================

    private void mostrarError(String mensaje) {

        new AlertDialog.Builder(this)
                .setTitle("No se pudo iniciar sesión")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }

    // ==========================================
    // OBTENER TEXTO DE LOS CAMPOS
    // ==========================================

    private String obtenerTexto(TextInputEditText campo) {

        if (campo.getText() == null) {
            return "";
        }

        return campo.getText().toString().trim();
    }
}
