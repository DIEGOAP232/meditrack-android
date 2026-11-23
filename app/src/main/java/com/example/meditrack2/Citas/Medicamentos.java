
package com.example.meditrack2.Citas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.cardview.widget.CardView;

import com.example.meditrack2.R;
import com.google.android.material.button.MaterialButton;

public class Medicamentos extends AppCompatActivity {

    private TextView tvActivos;
    private TextView tvFinalizados;

    private View listaMedicamentos;

    private MaterialButton btnAgregar;
    private ImageButton btnVolver;

    private CardView cardMetformina;
    private CardView cardInsulina;
    private CardView cardOmeprazol;
    private CardView cardAtorvastatina;

    private boolean mostrarActivos = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medicamentos);

        // Ajustar la pantalla a las barras del sistema
        View principal = findViewById(R.id.main);

        if (principal != null) {
            ViewCompat.setOnApplyWindowInsetsListener(
                    principal,
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
        }

        // PESTAÑAS
        tvActivos = findViewById(R.id.tvMedicamentosActivos);
        tvFinalizados = findViewById(R.id.tvMedicamentosFinalizados);

        // TARJETAS DE MEDICAMENTOS
        cardMetformina = findViewById(R.id.cardMetformina);
        cardInsulina = findViewById(R.id.cardInsulina);
        cardOmeprazol = findViewById(R.id.cardOmeprazol);
        cardAtorvastatina = findViewById(R.id.cardAtorvastatina);

        // CONTENEDOR DE LA LISTA
        listaMedicamentos = findViewById(R.id.scrollMedicamentos);

        // BOTONES
        btnAgregar = findViewById(R.id.btnAgregarMedicamento);
        btnVolver = findViewById(R.id.btnVolverMedicamentos);

        // ABRIR FORMULARIO AGREGAR MEDICAMENTO
        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Medicamentos.this,
                    AgregarMedicamentos.class
            );

            startActivity(intent);
        });

        // VOLVER A LA PANTALLA ANTERIOR
        btnVolver.setOnClickListener(v -> finish());

        // CAMBIAR ENTRE PESTAÑAS
        tvActivos.setOnClickListener(v -> mostrarPestanaActivos());

        tvFinalizados.setOnClickListener(v -> mostrarPestanaFinalizados());

        // DETALLES DE MEDICAMENTOS
        configurarDetalle(
                R.id.btnDetalleMetformina,
                "Metformina",
                "Dosis de ejemplo: 500 mg"
        );

        configurarDetalle(
                R.id.btnDetalleInsulina,
                "Insulina",
                "Dosis de ejemplo: 10 UI"
        );

        configurarDetalle(
                R.id.btnDetalleOmeprazol,
                "Omeprazol",
                "Dosis de ejemplo: 20 mg"
        );

        configurarDetalle(
                R.id.btnDetalleAtorvastatina,
                "Atorvastatina",
                "Dosis de ejemplo: 10 mg"
        );

        // MOSTRAR ACTIVOS AL ENTRAR
        mostrarPestanaActivos();
    }

    // PESTAÑA ACTIVOS
    private void mostrarPestanaActivos() {
        mostrarActivos = true;

        actualizarEstiloPestanas();

        listaMedicamentos.setVisibility(View.VISIBLE);

        cardMetformina.setVisibility(View.VISIBLE);
        cardInsulina.setVisibility(View.VISIBLE);
        cardOmeprazol.setVisibility(View.VISIBLE);
        cardAtorvastatina.setVisibility(View.VISIBLE);

        btnAgregar.setVisibility(View.VISIBLE);
    }

    // PESTAÑA FINALIZADOS
    private void mostrarPestanaFinalizados() {
        mostrarActivos = false;

        actualizarEstiloPestanas();

        listaMedicamentos.setVisibility(View.GONE);
        btnAgregar.setVisibility(View.GONE);

        Toast.makeText(
                this,
                "Todavía no hay medicamentos finalizados.",
                Toast.LENGTH_SHORT
        ).show();
    }

    // ACTUALIZAR ESTILO DE LAS PESTAÑAS
    private void actualizarEstiloPestanas() {

        if (mostrarActivos) {

            tvActivos.setBackgroundResource(
                    R.drawable.bg_nav_selected
            );

            tvActivos.setTextColor(
                    android.graphics.Color.WHITE
            );

            tvFinalizados.setBackgroundResource(
                    android.R.color.transparent
            );

            tvFinalizados.setTextColor(
                    android.graphics.Color.rgb(89, 118, 157)
            );

        } else {

            tvFinalizados.setBackgroundResource(
                    R.drawable.bg_nav_selected
            );

            tvFinalizados.setTextColor(
                    android.graphics.Color.WHITE
            );

            tvActivos.setBackgroundResource(
                    android.R.color.transparent
            );

            tvActivos.setTextColor(
                    android.graphics.Color.rgb(89, 118, 157)
            );
        }
    }

    // MOSTRAR DETALLES DE CADA MEDICAMENTO
    private void configurarDetalle(
            int idVista,
            String nombre,
            String dosis
    ) {
        View detalle = findViewById(idVista);

        if (detalle != null) {
            detalle.setOnClickListener(v ->
                    new AlertDialog.Builder(Medicamentos.this)
                            .setTitle(nombre)
                            .setMessage(
                                    dosis
                                            + "\n\nEstos datos son de demostración. "
                                            + "Consulta siempre tu receta médica."
                            )
                            .setPositiveButton("Cerrar", null)
                            .show()
            );
        }
    }
}
