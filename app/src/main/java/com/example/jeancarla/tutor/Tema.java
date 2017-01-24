package com.example.jeancarla.tutor;

/**
 * Created by Jean Carla on 30/11/2016.
 */
public class Tema {
    private String user;
    private String id_user;
    private String estilo;
    private String id;
    private String nombre;
    private String descripcion;
    private int nro;

    public Tema(String user, String id_user, String estilo, String id,String nombre, String descripcion, int nro) {
        this.estilo = estilo;
        this.id_user = id_user;
        this.user = user;
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nro = nro;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String nombre) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }
}
