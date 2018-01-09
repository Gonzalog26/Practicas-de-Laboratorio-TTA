package eus.ehu.tta.appbasica;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

import eus.ehu.tta.appbasica.modelo.Test;
import eus.ehu.tta.appbasica.negocio.GeneradorTest;
import eus.ehu.tta.appbasica.presentacion.AudioPlayer;


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


        switch(tipoMime){

            case "null":

                WebView web = new WebView(this);
                web.loadData(ayuda,"text/html",null);
                web.setBackgroundColor(Color.TRANSPARENT);
                web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
                LinearLayout layout = findViewById(R.id.layout_tests);
                layout.addView(web);

                break;

            case "text/html":

                if (ayuda.substring(0,10).contains("://")){
                    Uri uri = Uri.parse(ayuda);
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
                else{
                    WebView web2 = new WebView(this);
                    web2.loadData(ayuda,"text/html",null);
                    web2.setBackgroundColor(Color.TRANSPARENT);
                    web2.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
                    LinearLayout layout1 = findViewById(R.id.layout_tests);
                    layout1.addView(web2);
                }

                break;

            case "video":

                VideoView videoView = new VideoView(this);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                videoView.setLayoutParams(params);

                videoView.setVideoURI(Uri.parse(ayuda));

                MediaController controller = new MediaController(this){

                    @Override
                    public void hide(){

                    }

                    public boolean dispathcKeyEvent(KeyEvent event){
                        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                            finish();
                        return super.dispatchKeyEvent(event);
                    }
                };

                controller.setAnchorView(videoView);
                videoView.setMediaController(controller);

                LinearLayout layout2 = findViewById(R.id.layout_tests);
                layout2.addView(videoView);
                videoView.start();

                break;

            case "audio":

                View audioView;
                audioView = new View(this);

                ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                audioView.setLayoutParams(params2);

                audioView.setLayoutParams(params2);
                AudioPlayer audioPlayer = new AudioPlayer(audioView, new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });

                try {
                    audioPlayer.setAudioUri(ayuda);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

        }



    }

    public void onClick(View view){
        findViewById(R.id.boton_enviarTest).setVisibility(View.VISIBLE);
    }
}
