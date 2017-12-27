package eus.ehu.tta.appbasica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import eus.ehu.tta.appbasica.modelo.Test;
import eus.ehu.tta.appbasica.presentacion.Data;


public class testsActivity extends AppCompatActivity {

    public Data Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        Data Data = new Data();
        Test test = Data.getTest();
        TextView enunciado = (TextView)findViewById(R.id.enunciado_test);
        enunciado.setText(test.getEnunciado());
        /*RadioGroup group = (RadioGroup) findViewById(R.id.elecciones_test);
        int i=0;

        for( test.getBotones()){
            RadioButton radio = new RadioButton(this);
            radio.setText(boto);
        }*/
    }

    public void enviarRespuesta(View view){

    }
}
