package com.example.jeancarla.tutor;

/**
 * Created by Jean Carla on 07/12/2016.
 */
public class ClassContenido {

    private String id;
    private String nombre;
    private String resumen;
    private String contenido;
    private String link;
    private String ayuda;

    public ClassContenido(String id, String nombre, String resumen, String contenido, String link, String ayuda) {
        this.id = id;
        this.nombre = nombre;
        this.resumen = resumen;
        this.contenido = contenido;
        this.link = link;
        this.ayuda = ayuda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }
}
