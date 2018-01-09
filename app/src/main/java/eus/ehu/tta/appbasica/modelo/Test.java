package eus.ehu.tta.appbasica.modelo;

import java.lang.reflect.Array;
import java.util.List;

import eus.ehu.tta.appbasica.R;

/**
 * Created by tta on 27/12/17.
 */

public class Test {

    public String pregunta;
    public List<String> respuestas;
    public int respuestaCorrecta;
    public String ayuda;

    public Test(){
          this.pregunta = null;
          this.ayuda = null;
          this.respuestas = null;
    }


    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(int respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }
}
