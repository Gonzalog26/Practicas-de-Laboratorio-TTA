package eus.ehu.tta.appbasica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    public static String EXTRA_LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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
