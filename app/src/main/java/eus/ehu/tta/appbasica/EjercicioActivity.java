package eus.ehu.tta.appbasica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class EjercicioActivity extends AppCompatActivity {

    private final int WRITE_PERMISSION_CODE = 1;
    private final int AUDIO_REQUEST_CODE = 1;
    private final int VIDEO_REQUEST_CODE = 2;
    private final int PICTURE_REQUEST_CODE = 3;
    private final int READ_REQUEST_CODE = 4;

    Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        TextView textEjercicio = (TextView)findViewById(R.id.enunciado_ejercicio);
        textEjercicio.setText(R.string.ejercicio1);
    }

    public void enviarFichero(View view){

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
                    Uri pictureUri = Uri.fromFile(file);
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
                Toast.makeText(this, R.string.noapp, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(this, R.string.noapp, Toast.LENGTH_SHORT).show();

        }
    }

}
