package eus.ehu.tta.appbasica.negocio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import eus.ehu.tta.appbasica.modelo.Elecciones;
import eus.ehu.tta.appbasica.modelo.ResourceType;
import eus.ehu.tta.appbasica.modelo.Test;
import eus.ehu.tta.appbasica.modelo.User;

/**
 * Created by tta on 9/01/18.
 */


public class ServidorNegocio implements InterfazNegocio {


    final String baseUrl = "http:u017633.ehu.eus:28080/ServidorTta/rest/tta";
    public static ServidorNegocio servidorNegocio;
    ClienteRest clienteRest;


    public ServidorNegocio(){
        clienteRest = new ClienteRest(baseUrl);
    }

    public static ServidorNegocio getInstance(){
        if(servidorNegocio == null)
            servidorNegocio = new ServidorNegocio();
        return servidorNegocio;
    }

    /*public Test getTest(){

        Test test = new Test();

        test.setPregunta("¿Cual de las siguientes opciones NO se indica en el fichero de manifiesto de la app?");
        test.setAyuda("http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4");
        test.getRespuestas().add("Versión de la aplicación");
        test.getRespuestas().add("Listado de componentes de la aplicación");
        test.getRespuestas().add("Opciones del menú de ajustes");
        test.getRespuestas().add("Nivel mínimo de la API Android requerida");
        test.getRespuestas().add("Nombre del paquete java de la aplicación");
        test.setTipoMIME("video");
        test.setRespuestaCorrecta(2);

        return test;
    }*/

    public User autenticathion(String dni, String passwd) throws IOException,JSONException {

            User user = new User();

            clienteRest.setHttpBasicAuth(dni,passwd);
            JSONObject jsonObject = clienteRest.getJson(String.format("getStatus?dni=%s",dni));

            user.setId(jsonObject.getInt("id"));
            user.setNumeroLeccion(jsonObject.getInt("lessonNumber"));
            user.setUsuario(jsonObject.getString("user"));
            user.setSiguienteEjercicio(jsonObject.getInt("nextExercise"));
            user.setSiguienteTest(jsonObject.getInt("nextTest"));
            user.setTituloLeccion(jsonObject.getString("lessonTitle"));

        return user;

    }

    public Test getTest(String dni, String passwd) throws  JSONException,IOException{

        try{

            Test test = new Test();
            clienteRest.setHttpBasicAuth(dni,passwd);
            JSONObject jsonObject = clienteRest.getJson("getTest?id=1");

            test.setEnunciado(jsonObject.getString("wording"));
            JSONArray jsonArray = jsonObject.getJSONArray("choices");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Elecciones elecciones = new Elecciones();
                elecciones.setId(jsonObject1.getInt("id"));
                elecciones.setAviso(jsonObject1.getString("advise"));
                elecciones.setCorrecto(jsonObject1.getBoolean("correct"));
                elecciones.setRespuesta(jsonObject1.getString("answer"));

                JSONObject jsonObject2= jsonObject1.getJSONObject("resourceType");

                ResourceType resourceType = new ResourceType();
                resourceType.setId(jsonObject2.getInt("id"));
                resourceType.setDescripcion(jsonObject2.getString("description"));
                resourceType.setMime(jsonObject2.getString("mime"));

                elecciones.setResourceType(resourceType);

                test.getElecciones().add(elecciones);


            }

            return test;
        }
        catch (IOException e){
            return null;
        }







    }

}
