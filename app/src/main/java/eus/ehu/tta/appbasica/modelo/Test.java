package eus.ehu.tta.appbasica.modelo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tta on 27/12/17.
 */

public class Test {

    private String enunciado;
    private List<Elecciones> elecciones;


    public Test(){
          elecciones = new ArrayList<>();
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public List<Elecciones> getElecciones() {
        return elecciones;
    }

    public void setElecciones(List<Elecciones> elecciones) {
        this.elecciones = elecciones;
    }
}
