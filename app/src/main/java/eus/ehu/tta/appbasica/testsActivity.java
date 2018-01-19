package eus.ehu.tta.appbasica;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.tta.appbasica.modelo.Elecciones;
import eus.ehu.tta.appbasica.modelo.Test;
import eus.ehu.tta.appbasica.modelo.User;
import eus.ehu.tta.appbasica.negocio.ClienteRest;
import eus.ehu.tta.appbasica.negocio.ProgressTask;
import eus.ehu.tta.appbasica.negocio.ServidorNegocio;
import eus.ehu.tta.appbasica.presentacion.AudioPlayer;


public class testsActivity extends AppCompatActivity implements View.OnClickListener {


    public static String EXTRA_DNI;
    public static String EXTRA_PASSWORD;
    ServidorNegocio servidorNegocio = ServidorNegocio.getInstance();

    List<String> mime = new ArrayList<>();
    List<String> ayudas = new ArrayList<>();

    int respuestaCorrecta;
    int seleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        EXTRA_DNI = extras.getString("EXTRA_DNI");
        EXTRA_PASSWORD = extras.getString("EXTRA_PASSWORD");


        new ProgressTask<Test>(this){

            @Override
            protected Test work() throws IOException,JSONException {
                return servidorNegocio.getTest(EXTRA_DNI,EXTRA_PASSWORD);
            }

            @Override
            protected void onFinish(Test result){

                TextView enunciado = (TextView)findViewById(R.id.enunciado_test);
                enunciado.setText(result.getEnunciado());

                for(int j=0;j<result.getElecciones().size();j++){

                    if(result.getElecciones().get(j).isCorrecto()==true){
                        respuestaCorrecta = j;
                        ayudas.add("sin ayuda");
                        mime.add("sin mime");
                    }
                    else{
                        mime.add(result.getElecciones().get(j).getResourceType().getMime());
                        ayudas.add(result.getElecciones().get(j).getAviso());
                    }

                }

                RadioGroup group = (RadioGroup) findViewById(R.id.elecciones_test);
                int i=0;


                for(i=0;i<result.getElecciones().size();i++){

                    RadioButton radio = new RadioButton(context);
                    radio.setId(result.getElecciones().get(i).getId());
                    radio.setText(result.getElecciones().get(i).getRespuesta());
                    radio.setOnClickListener((View.OnClickListener) context);
                    group.addView(radio);

                }

            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast.makeText(getApplicationContext(), R.string.errorlogeo, Toast.LENGTH_SHORT).show();
            }

        }.execute();

    }

    public void enviarRespuesta(View view) throws JSONException,IOException{
        RadioGroup radioGroup = findViewById(R.id.elecciones_test);
        seleccionado = radioGroup.getCheckedRadioButtonId();
        seleccionado--;

        findViewById(R.id.boton_enviarTest).setVisibility(View.GONE);

        radioGroup.getChildAt(respuestaCorrecta).setBackgroundColor(Color.GREEN);

        if(seleccionado != respuestaCorrecta){
            radioGroup.getChildAt(seleccionado).setBackgroundColor(Color.RED);
            //Toast.makeText(getApplicationContext(),"!Has fallado!",Toast.LENGTH_SHORT).show();
            findViewById(R.id.boton_ayuda).setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(getApplicationContext(), "¡Correcto!", Toast.LENGTH_SHORT).show();
        }


        new ProgressTask<Integer>(this){

            @Override
            protected Integer work() throws IOException,JSONException {
                return servidorNegocio.subirRespuestas(1,seleccionado+1,EXTRA_DNI,EXTRA_PASSWORD);
            }

            @Override
            protected void onFinish(Integer result){

                if(result==200){
                    Toast.makeText(getApplicationContext(), " requestCode: "+result, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), " requestCode: "+result, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast.makeText(getApplicationContext(), R.string.errorlogeo, Toast.LENGTH_SHORT).show();
            }

        }.execute();




    }

    public void verAyuda(View view){


        switch(mime.get(seleccionado)){

            case "null":

                WebView web = new WebView(this);
                web.loadData(ayudas.get(seleccionado),"text/html",null);
                web.setBackgroundColor(Color.TRANSPARENT);
                web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
                LinearLayout layout = findViewById(R.id.layout_tests);
                layout.addView(web);

                break;

            case "text/html":

                if (ayudas.get(seleccionado).substring(0,10).contains("://")){
                    Uri uri = Uri.parse(ayudas.get(seleccionado));
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
                else{
                    WebView web2 = new WebView(this);
                    web2.loadData(ayudas.get(seleccionado),"text/html",null);
                    web2.setBackgroundColor(Color.TRANSPARENT);
                    web2.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
                    LinearLayout layout1 = findViewById(R.id.layout_tests);
                    layout1.addView(web2);
                }

                break;

            case "video/mp4":

                VideoView videoView = new VideoView(this);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                videoView.setLayoutParams(params);

                videoView.setVideoURI(Uri.parse(ayudas.get(seleccionado)));

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
                videoView.start();


                LinearLayout layout2 = findViewById(R.id.layout_tests);
                layout2.addView(videoView);


                break;

            case "audio/mpeg":

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
                    audioPlayer.setAudioUri(ayudas.get(seleccionado));
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
