package com.example.jeancarla.tutor;

/**
 * Created by Jean Carla on 06/12/2016.
 */
public class Usuario {

    String id;
    String user;
    String password;
    String nombres;
    String apellido_pat;
    String apellido_mat;
    String estilo;
    String nivel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAp_paterno() {
        return apellido_pat;
    }

    public void setAp_paterno(String ap_paterno) {
        this.apellido_pat = ap_paterno;
    }

    public String getAp_materno() {
        return apellido_mat;
    }

    public void setAp_materno(String ap_materno) {
        this.apellido_mat = ap_materno;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }


}
