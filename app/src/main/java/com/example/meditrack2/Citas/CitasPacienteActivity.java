
package com.example.meditrack2.Citas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meditrack2.R;

public class CitasPacienteActivity extends AppCompatActivity {

    private TextView tabProximas;
    private TextView tabHistorial;
    private LinearLayout listaCitas;
    private Button btnAgendarNuevaCita;

    private ActivityResultLauncher<Intent> lanzadorNuevaCita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_citas_paciente);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
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

        tabProximas = findViewById(R.id.textView56);
        tabHistorial = findViewById(R.id.textView57);
        listaCitas = findViewById(R.id.listaCitas);
        btnAgendarNuevaCita =
                findViewById(R.id.btnAgendarNuevaCita);

        lanzadorNuevaCita = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                resultado -> {
                    if (resultado.getResultCode() == RESULT_OK
                            && resultado.getData() != null) {

                        Intent datos = resultado.getData();

                        String especialidad =
                                datos.getStringExtra("especialidad");
                        String medico =
                                datos.getStringExtra("medico");
                        String fecha =
                                datos.getStringExtra("fecha");
                        String hora =
                                datos.getStringExtra("hora");

                        agregarTarjetaCita(
                                especialidad,
                                medico,
                                fecha,
                                hora
                        );

                        mostrarProximas();

                        Toast.makeText(
                                this,
                                "Cita agregada correctamente",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );

        tabProximas.setOnClickListener(
                v -> mostrarProximas()
        );

        tabHistorial.setOnClickListener(
                v -> mostrarHistorial()
        );

        btnAgendarNuevaCita.setOnClickListener(v -> {
            Intent intent = new Intent(
                    CitasPacienteActivity.this,
                    NuevaCita.class
            );
            lanzadorNuevaCita.launch(intent);
        });

        View btnRegresar = findViewById(R.id.imageButton3);
        btnRegresar.setOnClickListener(v -> finish());

        mostrarProximas();
    }

    private void agregarTarjetaCita(
            String especialidad,
            String medico,
            String fecha,
            String hora) {

        // Inflar la tarjeta definida en XML.
        View tarjeta = getLayoutInflater().inflate(
                R.layout.item_cita,
                listaCitas,
                false
        );

        TextView tvDia = tarjeta.findViewById(R.id.tvDia);
        TextView tvMes = tarjeta.findViewById(R.id.tvMes);
        TextView tvEspecialidad =
                tarjeta.findViewById(R.id.tvEspecialidad);
        TextView tvMedico =
                tarjeta.findViewById(R.id.tvMedico);
        TextView tvHora =
                tarjeta.findViewById(R.id.tvHora);
        TextView tvEstado =
                tarjeta.findViewById(R.id.tvEstado);

        String[] partesFecha = fecha.split("/");
        tvDia.setText(partesFecha[0]);

        String[] meses = {
                "ENE", "FEB", "MAR", "ABR", "MAY", "JUN",
                "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"
        };

        if (partesFecha.length >= 2) {
            try {
                int numeroMes = Integer.parseInt(partesFecha[1]);
                if (numeroMes >= 1 && numeroMes <= 12) {
                    tvMes.setText(meses[numeroMes - 1]);
                }
            } catch (NumberFormatException e) {
                tvMes.setText("--");
            }
        }

        tvEspecialidad.setText(especialidad);
        tvMedico.setText(medico);
        tvHora.setText(hora);
        tvEstado.setText("Pendiente");

        listaCitas.addView(tarjeta);
    }

    private void mostrarProximas() {
        listaCitas.setVisibility(View.VISIBLE);

        tabProximas.setBackgroundColor(0xFF08B6B3);
        tabProximas.setTextColor(Color.WHITE);

        tabHistorial.setBackgroundColor(0xFFEAF4FA);
        tabHistorial.setTextColor(0xFF55769C);

        findViewById(R.id.cardCita1).setVisibility(View.VISIBLE);
        findViewById(R.id.cardCita2).setVisibility(View.VISIBLE);
        findViewById(R.id.cardCita3).setVisibility(View.VISIBLE);
    }

    private void mostrarHistorial() {
        tabHistorial.setBackgroundColor(0xFF08B6B3);
        tabHistorial.setTextColor(Color.WHITE);

        tabProximas.setBackgroundColor(0xFFEAF4FA);
        tabProximas.setTextColor(0xFF55769C);

        listaCitas.setVisibility(View.GONE);

        Toast.makeText(
                this,
                "Todavía no hay citas históricas registradas.",
                Toast.LENGTH_SHORT
        ).show();
    }
}
