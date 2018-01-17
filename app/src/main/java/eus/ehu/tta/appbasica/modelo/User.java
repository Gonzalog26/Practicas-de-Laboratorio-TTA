package eus.ehu.tta.appbasica.modelo;

/**
 * Created by tta on 22/12/17.
 */

public class User {

    private int id;
    private String usuario;
    private int numeroLeccion;
    private String tituloLeccion;
    private int siguienteTest;
    private int siguienteEjercicio;

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getNumeroLeccion() {
        return numeroLeccion;
    }

    public void setNumeroLeccion(int numeroLeccion) {
        this.numeroLeccion = numeroLeccion;
    }

    public String getTituloLeccion() {
        return tituloLeccion;
    }

    public void setTituloLeccion(String tituloLeccion) {
        this.tituloLeccion = tituloLeccion;
    }

    public int getSiguienteTest() {
        return siguienteTest;
    }

    public void setSiguienteTest(int siguienteTest) {
        this.siguienteTest = siguienteTest;
    }

    public int getSiguienteEjercicio() {
        return siguienteEjercicio;
    }

    public void setSiguienteEjercicio(int siguienteEjercicio) {
        this.siguienteEjercicio = siguienteEjercicio;
    }
}
