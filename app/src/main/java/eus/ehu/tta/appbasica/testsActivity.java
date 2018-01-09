package eus.ehu.tta.appbasica;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import eus.ehu.tta.appbasica.modelo.Test;
import eus.ehu.tta.appbasica.negocio.GeneradorTest;


public class testsActivity extends AppCompatActivity implements View.OnClickListener {


    int respuestaCorrecta;
    String tipoMime;
    String ayuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        GeneradorTest generadorTest = new GeneradorTest();
        Test test = generadorTest.getTest();

        TextView enunciado = (TextView)findViewById(R.id.enunciado_test);
        enunciado.setText(test.getPregunta());

        respuestaCorrecta = test.getRespuestaCorrecta();
        ayuda = test.getAyuda();
        tipoMime = test.getTipoMIME();

        RadioGroup group = (RadioGroup) findViewById(R.id.elecciones_test);
        int i=0;

        for( String resp : test.getRespuestas()){
            RadioButton radio = new RadioButton(this);
            radio.setId(i);
            radio.setText(resp);
            radio.setOnClickListener(this);
            group.addView(radio);
            i++;
        }
    }



    public void enviarRespuesta(View view){
        RadioGroup radioGroup = findViewById(R.id.elecciones_test);
        int seleccionado = radioGroup.getCheckedRadioButtonId();

        findViewById(R.id.boton_enviarTest).setVisibility(View.GONE);

        radioGroup.getChildAt(respuestaCorrecta).setBackgroundColor(Color.GREEN);

        if(seleccionado != respuestaCorrecta){
            radioGroup.getChildAt(seleccionado).setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(),"!Has fallado!",Toast.LENGTH_SHORT).show();
            findViewById(R.id.boton_ayuda).setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(getApplicationContext(), "¡Correcto!", Toast.LENGTH_SHORT).show();
        }
    }

    public void verAyuda(View view){

        if(tipoMime=="null"){
            WebView web = new WebView(this);
            web.loadData(ayuda,"text/html",null);
            web.setBackgroundColor(Color.TRANSPARENT);
            web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
            LinearLayout layout = findViewById(R.id.layout_tests);
            layout.addView(web);
        }
        else if (tipoMime=="text/html"){
            if (ayuda.substring(0,10).contains("://")){
                Uri uri = Uri.parse(ayuda);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
            else{
                WebView web = new WebView(this);
                web.loadData(ayuda,"text/html",null);
                web.setBackgroundColor(Color.TRANSPARENT);
                web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
                LinearLayout layout = findViewById(R.id.layout_tests);
                layout.addView(web);
            }
        }
        else if(tipoMime=="Video"){

        }


    }

    public void onClick(View view){
        findViewById(R.id.boton_enviarTest).setVisibility(View.VISIBLE);
    }
}
