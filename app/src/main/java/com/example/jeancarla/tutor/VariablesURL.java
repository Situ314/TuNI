package com.example.jeancarla.tutor;

/**
 * Created by Jean Carla on 06/12/2016.
 */
public class VariablesURL {

    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = "esy.es";
    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "tuniumsa.";
    /**
     * URLs del Web Service
     */

    public static final String GET_LOGIN = "http://" + IP  + PUERTO_HOST + "/tuni/login.php?nombre_usuario=";
    public static final String GET_TEMA = "http://" + IP  + PUERTO_HOST + "/tuni/descargar_tema.php?id=";
    public static final String GET_USER = "http://" + IP + PUERTO_HOST + "/tuni/descargar_datos_usuario.php?nombre_usuario=";
    public static final String GET_PREGUNTAS = "http://" + IP  + PUERTO_HOST + "/tuni/descargar_preguntas.php?id_prueba=";
    public static final String INSERT_CUESTIONARIO = "http://" + IP + PUERTO_HOST + "/tuni/guardar_estilo.php";
    public static final String INSERT_NOTA = "http://" + IP + PUERTO_HOST + "/tuni/guardar_nota.php";
    public static final String INSERT_NIVEL = "http://" + IP + PUERTO_HOST + "/tuni/guardar_nivel.php";



    /**
     * Clave para el valor extra que representa al identificador de una meta
     */
    public static final String EXTRA_ID = "IDEXTRA";
}
