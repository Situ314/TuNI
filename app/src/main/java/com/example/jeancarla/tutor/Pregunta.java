package com.example.jeancarla.tutor;

import java.util.HashMap;
import java.util.Map;



public class Pregunta {

    private String pregunta;
    private String respuesta;
    private String prueba;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The pregunta
     */
    public String getPregunta() {
        return pregunta;
    }

    /**
     *
     * @param pregunta
     * The pregunta
     */
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    /**
     *
     * @return
     * The repuesta
     */
    public String getRepuesta() {
        return respuesta;
    }

    /**
     *
     * @param repuesta
     * The repuesta
     */
    public void setRepuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     *
     * @return
     * The prueba
     */
    public String getPrueba() {
        return prueba;
    }

    /**
     *
     * @param prueba
     * The prueba
     */
    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}