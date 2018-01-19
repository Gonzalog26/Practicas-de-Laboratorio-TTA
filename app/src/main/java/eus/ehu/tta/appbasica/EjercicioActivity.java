package eus.ehu.tta.appbasica;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import eus.ehu.tta.appbasica.modelo.Ejercicio;
import eus.ehu.tta.appbasica.modelo.Test;
import eus.ehu.tta.appbasica.negocio.ProgressTask;
import eus.ehu.tta.appbasica.negocio.ServidorNegocio;

public class EjercicioActivity extends AppCompatActivity {

    private final int PICTURE_REQUEST_CODE = 1;
    private final int AUDIO_REQUEST_CODE = 2;
    private final int VIDEO_REQUEST_CODE = 3;
    private final int READ_REQUEST_CODE = 4;
    private final int WRITE_PERMISSION_CODE = 5;

    public static String EXTRA_DNI;
    public static String EXTRA_PASSWORD;

    ServidorNegocio servidorNegocio = ServidorNegocio.getInstance();

    Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        EXTRA_DNI = extras.getString("EXTRA_DNI");
        EXTRA_PASSWORD = extras.getString("EXTRA_PASSWORD");

        new ProgressTask<Ejercicio>(this){

            @Override
            protected Ejercicio work() throws IOException,JSONException {
                return servidorNegocio.getEjercicio(EXTRA_DNI,EXTRA_PASSWORD);
            }

            @Override
            protected void onFinish(Ejercicio result){

                TextView textEjercicio = (TextView)findViewById(R.id.enunciado_ejercicio);
                textEjercicio.setText(result.getEnunciado());

            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast.makeText(getApplicationContext(), R.string.errorlogeo, Toast.LENGTH_SHORT).show();
            }

        }.execute();


    }

    public void enviarFichero(View view){

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    private void dumpMetadata(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                String size = null;
                if (!cursor.isNull(sizeIndex))
                    size = cursor.getString(sizeIndex);
                else
                    size = "Unknown";

                Toast.makeText(this, "Nombre: "+displayName+" Tamaño: "+size, Toast.LENGTH_SHORT).show();
            }
        } finally {
            cursor.close();
        }
    }

    public void sacarFoto(View view){

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this, R.string.nocamera, Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    sacarFoto();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION_CODE);
                }
            } else
                Toast.makeText(this, R.string.noapp, Toast.LENGTH_SHORT).show();

        }

    }

    private void sacarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                try {

                    File file = File.createTempFile("tta", ".jpg", dir);
                    pictureUri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                    startActivityForResult(intent, PICTURE_REQUEST_CODE);
                } catch (IOException ex) {
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_CODE);
            }
        } else
            Toast.makeText(this, R.string.noapp, Toast.LENGTH_SHORT).show();
    }


    public void grabarAudio(View view) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
            Toast.makeText(this, R.string.nomicrofono, Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, AUDIO_REQUEST_CODE);
            } else
                Toast.makeText(this, R.string.noappgrabador, Toast.LENGTH_SHORT).show();

        }
    }

    public void grabarVideo(View view) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this, R.string.nocamera, Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, VIDEO_REQUEST_CODE);
            } else
                Toast.makeText(this, R.string.noappgrabadorvideo, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case READ_REQUEST_CODE:
                break;
            case VIDEO_REQUEST_CODE:
                sendFile(requestCode,data.getData());
            case AUDIO_REQUEST_CODE:
                sendFile(requestCode,data.getData());
                break;
            case PICTURE_REQUEST_CODE:
                sendFile(requestCode,pictureUri);
                break;
        }
    }

    public void sendFile(final int requestCode, final Uri uri) {

        new ProgressTask<Integer>(this) {
            @Override
            protected Integer work() throws Exception {
                String[] strings = uri.getPath().split("/");
                String fileName = strings[strings.length - 1];
                InputStream is = null;
                if (requestCode == PICTURE_REQUEST_CODE) {
                    File file = new File(uri.getPath());
                    is = new FileInputStream(file);
                } else
                    is = getContentResolver().openInputStream(uri);

                return servidorNegocio.enviarFichero(fileName, is);
            }

            @Override
            protected void onFinish(Integer result) {
                Toast.makeText(getApplicationContext(), R.string.subidaficherocorrecta, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast.makeText(getApplicationContext(), R.string.subidaficheroeerror, Toast.LENGTH_SHORT).show();
            }
        }.execute();

    }


}
