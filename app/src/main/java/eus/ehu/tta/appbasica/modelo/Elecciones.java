package eus.ehu.tta.appbasica.modelo;

/**
 * Created by tta on 17/01/18.
 */

public class Elecciones {

    private int id;
    private String aviso;
    private String respuesta;
    private boolean correcto;
    private ResourceType resourceType;

    public Elecciones(){
        resourceType = new ResourceType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isCorrecto() {
        return correcto;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
