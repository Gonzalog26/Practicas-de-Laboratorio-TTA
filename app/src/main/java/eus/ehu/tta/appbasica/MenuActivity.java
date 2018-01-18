package eus.ehu.tta.appbasica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static eus.ehu.tta.appbasica.R.*;


public class MenuActivity extends AppCompatActivity {

    public static String EXTRA_DNI;
    public static String EXTRA_PASSWORD;
    public static String EXTRA_NOMBRE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        EXTRA_NOMBRE = extras.getString("EXTRA_NOMBRE");
        EXTRA_DNI = extras.getString("EXTRA_DNI");
        EXTRA_PASSWORD = extras.getString("EXTRA_PASSWORD");


        TextView textLogin = (TextView) findViewById(id.menu_login);
        textLogin.setText( "Bienvenido: " +EXTRA_NOMBRE);
        textLogin = (TextView)findViewById(id.leccion);
        textLogin.setText(string.leccion1);
    }

    public void test (View view){
        Intent intent = new Intent(this,testsActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_DNI",EXTRA_DNI);
        extras.putString("EXTRA_PASSWORD",EXTRA_PASSWORD);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void ejercicio (View view){
        Intent intent = new Intent(this, EjercicioActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_DNI",EXTRA_DNI);
        extras.putString("EXTRA_PASSWORD",EXTRA_PASSWORD);
        intent.putExtras(extras);
        startActivity(intent);
    }


}
