package com.example.meditrack2.Registro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meditrack2.Entity.RegistroPacienteRequest;
import com.example.meditrack2.Login.LoginActivity;
import com.example.meditrack2.Network.ApiClient;
import com.example.meditrack2.Network.RegistroApi;
import com.example.meditrack2.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroPacienteActivity extends AppCompatActivity {

    private TextInputEditText txtnombrepaciente;
    private TextInputEditText txtapellidopaciente;
    private TextInputEditText txtdnipaciente;
    private TextInputEditText txtcorreopaciente;
    private TextInputEditText txttelefonopaciente;
    private TextInputEditText txtsexopaciente;
    private TextInputEditText txtfechapaciente;
    private TextInputEditText txtcontrasenapaciente;
    private TextInputEditText txtconfirmarcontrasenapaciente;

    private Button btnresgistrarpaciente;

    // Valores que espera el backend
    private String sexoSeleccionado = "";   // MASCULINO | FEMENINO
    private String fechaSeleccionada = "";  // yyyy-MM-dd

    private RegistroApi registroApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_paciente);

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

        inicializarCampos();

        registroApi = ApiClient.getClient().create(RegistroApi.class);

        txtsexopaciente.setOnClickListener(v -> elegirSexo());
        txtfechapaciente.setOnClickListener(v -> elegirFecha());
        btnresgistrarpaciente.setOnClickListener(v -> registrarPaciente());
    }

    private void inicializarCampos() {
        txtnombrepaciente = findViewById(R.id.txtnombrepaciente);
        txtapellidopaciente = findViewById(R.id.txtapellidopaciente);
        txtdnipaciente = findViewById(R.id.txtdnipaciente);
        txtcorreopaciente = findViewById(R.id.txtcorreopaciente);
        txttelefonopaciente = findViewById(R.id.txttelefonopaciente);
        txtsexopaciente = findViewById(R.id.txtsexopaciente);
        txtfechapaciente = findViewById(R.id.txtfechapaciente);
        txtcontrasenapaciente = findViewById(R.id.txtcontrasenapaciente);
        txtconfirmarcontrasenapaciente =
                findViewById(R.id.txtconfirmarcontrasenapaciente);
        btnresgistrarpaciente = findViewById(R.id.btnRegsistrofamilia);
    }

    private void elegirSexo() {
        String[] opciones = {"Masculino", "Femenino"};

        new AlertDialog.Builder(this)
                .setTitle("Sexo")
                .setItems(opciones, (dialog, which) -> {
                    sexoSeleccionado =
                            which == 0 ? "MASCULINO" : "FEMENINO";
                    txtsexopaciente.setText(opciones[which]);
                    txtsexopaciente.setError(null);
                })
                .show();
    }

    private void elegirFecha() {
        Calendar hoy = Calendar.getInstance();

        DatePickerDialog dialogo = new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    fechaSeleccionada = String.format(
                            Locale.US, "%04d-%02d-%02d",
                            year, month + 1, day);
                    txtfechapaciente.setText(String.format(
                            Locale.US, "%02d/%02d/%04d",
                            day, month + 1, year));
                    txtfechapaciente.setError(null);
                },
                hoy.get(Calendar.YEAR) - 30,
                hoy.get(Calendar.MONTH),
                hoy.get(Calendar.DAY_OF_MONTH)
        );

        // No permitir fechas futuras
        dialogo.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialogo.show();
    }

    private void registrarPaciente() {

        String nombre = obtenerTexto(txtnombrepaciente);
        String apellidos = obtenerTexto(txtapellidopaciente);
        String dni = obtenerTexto(txtdnipaciente);
        String correo = obtenerTexto(txtcorreopaciente);
        String telefono = obtenerTexto(txttelefonopaciente);
        String contrasena = obtenerTexto(txtcontrasenapaciente);
        String confirmar = obtenerTexto(txtconfirmarcontrasenapaciente);

        if (nombre.isEmpty()) {
            error(txtnombrepaciente, "El nombre es obligatorio");
            return;
        }
        if (apellidos.isEmpty()) {
            error(txtapellidopaciente, "Los apellidos son obligatorios");
            return;
        }
        if (!dni.matches("\\d{8}")) {
            error(txtdnipaciente, "El DNI debe tener 8 dígitos");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            error(txtcorreopaciente, "Ingresa un correo válido");
            return;
        }
        if (!telefono.matches("\\d{9}")) {
            error(txttelefonopaciente, "El teléfono debe tener 9 dígitos");
            return;
        }
        if (sexoSeleccionado.isEmpty()) {
            error(txtsexopaciente, "Selecciona el sexo");
            return;
        }
        if (fechaSeleccionada.isEmpty()) {
            error(txtfechapaciente,
                    "Selecciona la fecha de nacimiento");
            return;
        }
        if (contrasena.length() < 8
                || !contrasena.matches(".*[A-Z].*")
                || !contrasena.matches(".*[a-z].*")
                || !contrasena.matches(".*\\d.*")) {
            error(txtcontrasenapaciente,
                    "Usa 8 caracteres, mayúscula, minúscula y número");
            return;
        }
        if (!contrasena.equals(confirmar)) {
            error(txtconfirmarcontrasenapaciente,
                    "Las contraseñas no coinciden");
            return;
        }

        enviarRegistro(new RegistroPacienteRequest(
                nombre, apellidos, dni, correo, telefono,
                sexoSeleccionado, fechaSeleccionada, contrasena));
    }

    private void enviarRegistro(RegistroPacienteRequest request) {

        btnresgistrarpaciente.setEnabled(false);

        registroApi.registrarPaciente(request).enqueue(
                new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {

                        btnresgistrarpaciente.setEnabled(true);

                        if (response.isSuccessful()) {
                            mostrarCuentaCreada();
                        } else {
                            Toast.makeText(
                                    RegistroPacienteActivity.this,
                                    mensajeDeError(response),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        btnresgistrarpaciente.setEnabled(true);
                        Toast.makeText(
                                RegistroPacienteActivity.this,
                                "No se pudo conectar con MediTrack.",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    // Lee {"mensaje":"..."} del backend (400, 409...)
    private String mensajeDeError(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                String cuerpo = response.errorBody().string();
                String mensaje =
                        new JSONObject(cuerpo).optString("mensaje", "");
                if (!mensaje.isEmpty()) {
                    return mensaje;
                }
            }
        } catch (Exception ignored) {
        }
        return "No se pudo crear la cuenta. Código: " + response.code();
    }

    private void mostrarCuentaCreada() {
        new AlertDialog.Builder(this)
                .setTitle("Cuenta creada")
                .setMessage("Tu cuenta de paciente se creó correctamente. "
                        + "Ya puedes iniciar sesión.")
                .setPositiveButton("Continuar", (dialog, which) -> {
                    Intent intent = new Intent(
                            RegistroPacienteActivity.this,
                            LoginActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    private void error(TextInputEditText campo, String mensaje) {
        campo.setError(mensaje);
        campo.requestFocus();
    }

    private String obtenerTexto(TextInputEditText campo) {
        return campo.getText() == null
                ? "" : campo.getText().toString().trim();
    }
}