
package com.example.meditrack2.Citas;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meditrack2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.example.meditrack2.Citas.NuevoRecordatorio;

public class Recordatorios extends AppCompatActivity {

    private SwitchMaterial switchMedicacion;
    private SwitchMaterial switchGlucosa;
    private SwitchMaterial switchCita;
    private SwitchMaterial switchEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recordatorios);

        // Ajustar la pantalla a las barras del sistema
        android.view.View main = findViewById(R.id.main);

        if (main != null) {
            ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
                Insets barras = insets.getInsets(
                        WindowInsetsCompat.Type.systemBars()
                );

                v.setPadding(
                        barras.left,
                        barras.top,
                        barras.right,
                        barras.bottom
                );

                return insets;
            });
        }

        // Botón para regresar
        ImageButton btnVolver =
                findViewById(R.id.btnVolverRecordatorios);

        btnVolver.setOnClickListener(v -> finish());

        // Obtener los interruptores
        switchMedicacion = findViewById(R.id.switchMedicacion);
        switchGlucosa = findViewById(R.id.switchGlucosa);
        switchCita = findViewById(R.id.switchCita);
        switchEjercicio = findViewById(R.id.switchEjercicio);

        // Permitir activar y desactivar los recordatorios
        configurarInterruptor(switchMedicacion, "Medicación");
        configurarInterruptor(switchGlucosa, "Medición de glucosa");
        configurarInterruptor(switchCita, "Cita médica");
        configurarInterruptor(switchEjercicio, "Ejercicio");

        // Botón para crear un recordatorio
        // BOTÓN: NUEVO RECORDATORIO
        MaterialButton btnNuevo = findViewById(R.id.btnNuevoRecordatorio);

        btnNuevo.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Recordatorios.this,
                    NuevoRecordatorio.class
            );

            startActivity(intent);
        });
    }

    private void configurarInterruptor(
            SwitchMaterial interruptor,
            String nombre) {

        if (interruptor == null) {
            return;
        }

        // El interruptor debe responder al toque
        interruptor.setClickable(true);
        interruptor.setFocusable(true);

        interruptor.setOnCheckedChangeListener((buttonView, activado) -> {
            String mensaje = activado
                    ? nombre + ": recordatorio activado"
                    : nombre + ": recordatorio desactivado";

            Toast.makeText(
                    Recordatorios.this,
                    mensaje,
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}
