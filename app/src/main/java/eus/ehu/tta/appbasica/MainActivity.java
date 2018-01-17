package eus.ehu.tta.appbasica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import eus.ehu.tta.appbasica.modelo.User;
import eus.ehu.tta.appbasica.negocio.ProgressTask;
import eus.ehu.tta.appbasica.negocio.ServidorNegocio;


public class MainActivity extends AppCompatActivity {


    ServidorNegocio servidorNegocio = ServidorNegocio.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login (View view){


        final String login = ((EditText)findViewById(R.id.login)).getText().toString();
        final String passwd = ((EditText)findViewById(R.id.passwd)).getText().toString();


        new ProgressTask<User>(this){

            @Override
            protected User work() throws IOException,JSONException {
                return servidorNegocio.autenticathion(login,passwd);
            }

            @Override
            protected void onFinish(User result){

                Intent intent1 = new Intent(context, MenuActivity.class);
                Toast.makeText(getApplicationContext(), "Bienvenido: "+result.getUsuario(), Toast.LENGTH_SHORT).show();
                String nombre = result.getUsuario();
                intent1.putExtra(MenuActivity.EXTRA_NOMBRE,nombre);
                startActivity(intent1);


            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast.makeText(getApplicationContext(), R.string.errorlogeo, Toast.LENGTH_SHORT).show();
            }

        }.execute();

    }
}
