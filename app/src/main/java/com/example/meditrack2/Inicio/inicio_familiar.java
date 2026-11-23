
package com.example.meditrack2.Inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meditrack2.Citas.Perfil_familiar;
import com.example.meditrack2.R;

public class inicio_familiar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_familiar);

        View vistaPrincipal = findViewById(R.id.main);

        if (vistaPrincipal != null) {
            ViewCompat.setOnApplyWindowInsetsListener(
                    vistaPrincipal,
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

        // ELEMENTOS DEL PERFIL EN EL MENÚ INFERIOR
        ImageView imagenPerfil = findViewById(R.id.imgperfilfamiliar);
        TextView textoPerfil = findViewById(R.id.txtperfilfamiliar);

        // ACCIÓN PARA ABRIR EL PERFIL DEL FAMILIAR
        View.OnClickListener abrirPerfilFamiliar = v -> {
            Intent intent = new Intent(
                    inicio_familiar.this,
                    Perfil_familiar.class
            );

            startActivity(intent);
        };

        if (imagenPerfil != null) {
            imagenPerfil.setOnClickListener(abrirPerfilFamiliar);
        }

        if (textoPerfil != null) {
            textoPerfil.setOnClickListener(abrirPerfilFamiliar);
        }
    }
}
