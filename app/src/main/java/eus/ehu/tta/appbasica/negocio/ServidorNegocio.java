package eus.ehu.tta.appbasica.negocio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import eus.ehu.tta.appbasica.modelo.Ejercicio;
import eus.ehu.tta.appbasica.modelo.Elecciones;
import eus.ehu.tta.appbasica.modelo.ResourceType;
import eus.ehu.tta.appbasica.modelo.Test;
import eus.ehu.tta.appbasica.modelo.User;

/**
 * Created by tta on 9/01/18.
 */


public class ServidorNegocio implements InterfazNegocio {


    final String baseUrl = "http://u017633.ehu.eus:28080/ServidorTta/rest/tta";
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
                elecciones.setAviso(jsonObject1.optString("advise","0"));
                elecciones.setCorrecto(jsonObject1.getBoolean("correct"));
                elecciones.setRespuesta(jsonObject1.getString("answer"));

                if(elecciones.isCorrecto()==false){

                    JSONObject jsonObject2= jsonObject1.getJSONObject("resourceType");

                    ResourceType resourceType = new ResourceType();
                    resourceType.setId(jsonObject2.getInt("id"));
                    resourceType.setDescripcion(jsonObject2.getString("description"));
                    resourceType.setMime(jsonObject2.getString("mime"));

                    elecciones.setResourceType(resourceType);

                }



                test.getElecciones().add(elecciones);


            }

            return test;
        }
        catch (IOException e){
            return null;
        }

    }

    public int subirRespuestas(int userId, int choiceId,String dni, String password) throws JSONException, IOException {

        JSONObject jsonObject = new JSONObject();
        int responseCode=0;

        jsonObject.put("userId",userId);
        jsonObject.put("choiceId",choiceId);

        clienteRest.setHttpBasicAuth(dni,password);

        responseCode = clienteRest.postJson(jsonObject,"postChoice");

       return responseCode;

    }

    public Ejercicio getEjercicio(String dni, String passwd) throws IOException{

        Ejercicio ejercicio = new Ejercicio();

        clienteRest.setHttpBasicAuth(dni,passwd);
        try{
            JSONObject jsonObject = clienteRest.getJson("getExercise?id=1");

            ejercicio.setEnunciado(jsonObject.getString("wording"));
            ejercicio.setId(jsonObject.getInt("id"));

            return ejercicio;

        }
        catch(JSONException e){
            return null;
        }

   }
    public int enviarFichero(String fileName, InputStream is) throws IOException {
        return clienteRest.postFile("postExercise?user=1&id=1", is,fileName);
    }

}
