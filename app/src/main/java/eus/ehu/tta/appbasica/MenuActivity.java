package eus.ehu.tta.appbasica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static eus.ehu.tta.appbasica.R.*;


public class MenuActivity extends AppCompatActivity {

    public static String EXTRA_LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_menu);

        Intent intent = getIntent();
        TextView textLogin = (TextView) findViewById(id.menu_login);
        textLogin.setText(string.bienvenido);
        textLogin = (TextView)findViewById(id.leccion);
        textLogin.setText(string.leccion1);
    }

    public void test (View view){
        Intent intent = new Intent(this,testsActivity.class);
        startActivity(intent);
    }

    public void ejercicio (View view){
        Intent intent = new Intent(this, EjercicioActivity.class);
        startActivity(intent);
    }


}
