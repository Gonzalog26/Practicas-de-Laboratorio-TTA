package eus.ehu.tta.appbasica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EjercicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        TextView textEjercicio = (TextView)findViewById(R.id.enunciado_ejercicio);
        textEjercicio.setText(R.string.ejercicio1);
    }

    public void enviarFichero(View view){

    }

    public void sacarFoto(View view){

    }

    public void grabarAudio(View view){

    }

    public void grabarVideo(View view){

    }
}
