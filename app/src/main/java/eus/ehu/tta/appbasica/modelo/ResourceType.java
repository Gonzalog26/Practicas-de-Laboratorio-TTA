package eus.ehu.tta.appbasica.modelo;

/**
 * Created by tta on 17/01/18.
 */

public class ResourceType {

    private int id;
    private String descripcion;
    private String mime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public ResourceType(){

    }
}
