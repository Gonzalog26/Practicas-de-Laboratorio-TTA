package eus.ehu.tta.appbasica.negocio;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import eus.ehu.tta.appbasica.modelo.Ejercicio;
import eus.ehu.tta.appbasica.modelo.Test;

/**
 * Created by tta on 9/01/18.
 */

public interface InterfazNegocio {
    public Test getTest(String dni, String passwd) throws IOException, JSONException;
    public Ejercicio getEjercicio(String dni, String passwd)throws  IOException;
}
