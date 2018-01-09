package eus.ehu.tta.appbasica.negocio;

import eus.ehu.tta.appbasica.modelo.Test;

/**
 * Created by tta on 9/01/18.
 */


public class GeneradorTest implements InterfazTest {

    public Test getTest(){

        Test test = new Test();

        test.setPregunta("¿Cual de las siguientes opciones NO se indica en el fichero de manifiesto de la app?");
        test.getRespuestas().add("Versión de la aplicación");
        test.getRespuestas().add("Listado de componentes de la aplicación");
        test.getRespuestas().add("Opciones del menú de ajustes");
        test.getRespuestas().add("Nivel mínimo de la API Android requerida");
        test.getRespuestas().add("Nombre del paquete java de la aplicación");
        test.setRespuestaCorrecta(2);

        return test;
    }

}
