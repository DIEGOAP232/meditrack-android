
package com.example.meditrack2.Citas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meditrack2.R;

public class Familiares extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // CONFIGURACIÓN DE LA PANTALLA
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_familiares);

        View vistaPrincipal = findViewById(R.id.main);

        if (vistaPrincipal != null) {
            ViewCompat.setOnApplyWindowInsetsListener(
                    vistaPrincipal, (v, insets) -> {

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

        // ACCIÓN: ABRIR VINCULACIÓN DE FAMILIAR
        View.OnClickListener abrirVinculacion = v -> {
            Intent intent = new Intent(
                    Familiares.this,
                    Vincular_Familiar.class
            );
            startActivity(intent);
        };

        // BOTÓN: VINCULAR FAMILIAR
        // Este ID debe coincidir con el del XML.
        View botonVincular = findViewById(R.id.btnGenerarCodigoVinculacion);

        if (botonVincular != null) {
            botonVincular.setOnClickListener(abrirVinculacion);
        } else {
            Toast.makeText(
                    this,
                    "Revisa el ID del botón Vincular familiar en el XML",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
