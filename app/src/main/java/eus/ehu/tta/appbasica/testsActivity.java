package eus.ehu.tta.appbasica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import eus.ehu.tta.appbasica.modelo.Test;
import eus.ehu.tta.appbasica.presentacion.data;


public class testsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        Test test = data.getTest();
        TextView enunciado = (TextView)findViewById(R.id.enunciado_test);
        enunciado.setText(test.getEnunciado());
    }

    public void enviarRespuesta(View view){

    }
}
