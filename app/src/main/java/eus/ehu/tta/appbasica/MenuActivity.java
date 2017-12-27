package eus.ehu.tta.appbasica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MenuActivity extends AppCompatActivity {

    public static String EXTRA_LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        TextView textLogin = (TextView) findViewById(R.id.menu_login);
        String text = R.string.bienvenido+intent.getStringExtra(EXTRA_LOGIN);
        textLogin.setText(text);
        textLogin = (TextView)findViewById(R.id.leccion);
        textLogin.setText(R.string.leccion1);
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
