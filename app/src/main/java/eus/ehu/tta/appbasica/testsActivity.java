package eus.ehu.tta.appbasica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import eus.ehu.tta.appbasica.modelo.Test;


public class testsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        Test test = new Test();

        TextView enunciado = (TextView)findViewById(R.id.enunciado_test);
        enunciado.setText(test.getPregunta());
        RadioGroup group = (RadioGroup) findViewById(R.id.elecciones_test);
        int i=0;

        for( String resp : test.getRespuestas()){
            RadioButton radio = new RadioButton(this);
            radio.setText(resp);
            group.addView(radio);
        }
    }

    public void enviarRespuesta(View view){

    }
}
