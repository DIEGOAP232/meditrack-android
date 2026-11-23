
package com.example.meditrack2.Citas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meditrack2.R;

import java.util.Calendar;
import java.util.Locale;

public class NuevaCita extends AppCompatActivity {

    private Spinner spEspecialidad;
    private EditText etMedico, etMotivo;
    private Button btnFecha, btnHora, btnGuardar;
    private ImageButton btnCancelar;

    private String fechaSeleccionada = "";
    private String horaSeleccionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cita);

        spEspecialidad = findViewById(R.id.spEspecialidad);
        etMedico = findViewById(R.id.etMedico);
        etMotivo = findViewById(R.id.etMotivo);
        btnFecha = findViewById(R.id.btnFecha);
        btnHora = findViewById(R.id.btnHora);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        String[] especialidades = {
                "Medicina General",
                "Endocrinología",
                "Nutrición",
                "Cardiología",
                "Dermatología",
                "Pediatría"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                especialidades
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spEspecialidad.setAdapter(adapter);

        btnFecha.setOnClickListener(v -> seleccionarFecha());
        btnHora.setOnClickListener(v -> seleccionarHora());
        btnCancelar.setOnClickListener(v -> finish());
        btnGuardar.setOnClickListener(v -> validarFormulario());
    }

    private void seleccionarFecha() {
        Calendar hoy = Calendar.getInstance();

        DatePickerDialog dialogo = new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    fechaSeleccionada = String.format(
                            Locale.getDefault(),
                            "%02d/%02d/%04d",
                            day, month + 1, year
                    );
                    btnFecha.setText(fechaSeleccionada);
                },
                hoy.get(Calendar.YEAR),
                hoy.get(Calendar.MONTH),
                hoy.get(Calendar.DAY_OF_MONTH)
        );

        dialogo.getDatePicker().setMinDate(
                System.currentTimeMillis() - 1000
        );

        dialogo.show();
    }

    private void seleccionarHora() {
        Calendar ahora = Calendar.getInstance();

        TimePickerDialog dialogo = new TimePickerDialog(
                this,
                (view, hour, minute) -> {
                    horaSeleccionada = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            hour, minute
                    );
                    btnHora.setText(horaSeleccionada);
                },
                ahora.get(Calendar.HOUR_OF_DAY),
                ahora.get(Calendar.MINUTE),
                false
        );

        dialogo.show();
    }

    private void validarFormulario() {
        String medico = etMedico.getText().toString().trim();
        String motivo = etMotivo.getText().toString().trim();
        String especialidad = spEspecialidad.getSelectedItem().toString();

        if (medico.isEmpty()) {
            etMedico.setError("Ingresa el nombre del médico");
            etMedico.requestFocus();
            return;
        }

        if (fechaSeleccionada.isEmpty()) {
            Toast.makeText(this, "Selecciona una fecha",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (horaSeleccionada.isEmpty()) {
            Toast.makeText(this, "Selecciona una hora",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (motivo.isEmpty()) {
            etMotivo.setError("Ingresa el motivo de la consulta");
            etMotivo.requestFocus();
            return;
        }

        Intent resultado = new Intent();
        resultado.putExtra("especialidad", especialidad);
        resultado.putExtra("medico", medico);
        resultado.putExtra("fecha", fechaSeleccionada);
        resultado.putExtra("hora", horaSeleccionada);
        resultado.putExtra("motivo", motivo);

        setResult(RESULT_OK, resultado);

        Toast.makeText(
                this,
                "Solicitud de cita confirmada",
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }
}
