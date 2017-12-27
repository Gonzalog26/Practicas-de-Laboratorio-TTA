package eus.ehu.tta.appbasica.modelo;

import java.lang.reflect.Array;

import eus.ehu.tta.appbasica.R;

/**
 * Created by tta on 27/12/17.
 */

public class Test {

    public static String enunciado;
    public static String[] botones;

    public Test(){
            //DUDA
            enunciado = "¿Cual de las siguientes opciones no se indica en el fichero de manifiesto de la app?";
            botones = new String[5];
            botones[1]="Version de la aplicación";
            botones[2]="Listado de componentes de la aplicación";
            botones[3]="Opciones del menú de ajustes";
            botones[4]="Nivel mínimo de la API android requerida";
            botones[5]="Nombre del paquete java de la aplicación";
    }

    public String getEnunciado(){
        return enunciado;
    }

    public String[] getBotones(){
        return botones;
    }



}
