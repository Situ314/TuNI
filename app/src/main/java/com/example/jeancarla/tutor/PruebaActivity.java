package com.example.jeancarla.tutor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PruebaActivity extends AppCompatActivity {

    private Button sib,nob;
    private int nota;
    private String id_tema, id_usuario, nusuario;
    private Gson gson = new Gson();
    private Pregunta[] preguntas;
    public static final String TAG = "SITU";
    LinearLayout parent;
    FloatingActionButton fab;
    private Dialog dialog, dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        parent = (LinearLayout) findViewById(R.id.parent);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        id_tema = getIntent().getStringExtra("id_prueba");
        id_usuario = getIntent().getStringExtra("user");
        nusuario = getIntent().getStringExtra("nusuario");

       // Toast.makeText(getApplicationContext(),id_tema+"  "+id_usuario,Toast.LENGTH_LONG).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PruebaActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_evaluar);
                dialog.show();

                sib = (Button) dialog.findViewById(R.id.si_boton);
                nob = (Button) dialog.findViewById(R.id.no_boton);

                sib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nota = evaluar();
                        dialog.dismiss();

                       // Toast.makeText(getApplicationContext(),"NOTA: "+nota,Toast.LENGTH_LONG).show();

                        enviarNota();
                        //dialog2 = new Dialog(PruebaActivity.this);
                       // dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                       // dialog2.setContentView(R.layout.dialog_evaluar);
                        //dialog2.show();

                    }
                });

                nob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        if(!id_tema.equals("1")){
            pedirDatos(id_tema);
        }
        else{
            subirnivel();
        }

        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Evaluación");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private void pedirDatos(String usua) {

        // Añadir parámetro a la URL del web service
        String newURL = VariablesURL.GET_PREGUNTAS + usua;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley en pedir Datos: " + error.getMessage());
                            }
                        }
                )
        );
    }

    /**
     * Procesa cada uno de los estados posibles de la
     * respuesta enviada desde el servidor
     *
     * @param response Objeto Json
     */

    private void procesarRespuesta(JSONObject response) {

        System.out.println("SOMETHING ==============" + response.toString());
        try {
        JSONArray mensaje = response.getJSONArray("pruebas");
        preguntas = gson.fromJson(mensaje.toString(),Pregunta[].class);


        for(int i=0;i<preguntas.length;i++){

            String preg = preguntas[i].getPregunta();
            TextView tv_pregunta = new TextView(getApplicationContext());
            tv_pregunta.setText(preg);
            tv_pregunta.setTextSize(18);
            tv_pregunta.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

            parent.addView(tv_pregunta);

            EditText et_respuesta = (EditText) getLayoutInflater().inflate(R.layout.et_respuesta,null);
            parent.addView(et_respuesta);

        }
        }
     catch (JSONException e) {
        Log.d(TAG, e.getMessage());
    }

    }

    public int evaluar(){

        int nro=preguntas.length;
        int valor=100/nro;


        int childs = parent.getChildCount();
        List<String> respondidas = new ArrayList<>();

        for(int i=0;i<=childs;i++){
            View v = parent.getChildAt(i);

            if (v instanceof EditText){
                EditText et_respuesta = (EditText) v;
                respondidas.add(et_respuesta.getText().toString());
            }
        }

        int correctas=0;
        for (int j=0;j<preguntas.length;j++){
            Log.e("SITU: ",respondidas.get(j)+ " - "+preguntas[j].getRepuesta());
            if(respondidas.get(j).equalsIgnoreCase(preguntas[j].getRepuesta())){

                correctas++;
            }
        }

        return correctas*valor;
    }

    private ProgressDialog progressDialog;
    public void enviarNota() {
        progressDialog = new ProgressDialog(PruebaActivity.this);
        progressDialog.setMessage("Evaluando respuestas....");
        progressDialog.show();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id_user",id_usuario);
        map.put("id_prueba",id_tema);
        map.put("nota",nota+"");


        JSONObject jobject = new JSONObject(map);
        Log.e("SITU: ",jobject.toString());
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        VariablesURL.INSERT_NOTA,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                progressDialog.dismiss();
                                procesarRespuestaNota(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("SITU", "Error Volley: " + error.getMessage());
                                Toast.makeText(getApplicationContext(), "No se pudo comunicar con el servidor en este momento, inténtelo más tarde...", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );


    }

    private void procesarRespuestaNota(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    if(nota>50)
                        subirnivel();
                    else
                        mensajefinal();

                    break;

                case "2":
                    // Mostrar mensaje
                    Log.e("SITU ENVIAR: ", "MAL");

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void mensajefinal(){
        Button continuar;
        if(id_tema.equals("1")){
            dialog2 = new Dialog(PruebaActivity.this);
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.dialog_uno);
            dialog2.show();

            continuar = (Button) dialog2.findViewById(R.id.si_boton);

            continuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("id",id_usuario);
                    i.putExtra("user",nusuario);
                    startActivity(i);
                }
            });

        }
        else {
            Log.e("SITU ENVIAR: ", "BIEN");
            dialog2 = new Dialog(PruebaActivity.this);
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.dialog_nota);
            dialog2.show();
            TextView dnota = (TextView) dialog2.findViewById(R.id.nota_text);
            TextView dtitulo = (TextView) dialog2.findViewById(R.id.titulo);
            TextView dinfo = (TextView) dialog2.findViewById(R.id.info);
            dnota.setText("" + nota);
            continuar = (Button) dialog2.findViewById(R.id.si_boton);

            if (nota <= 50) {
                dtitulo.setText("LO SIENTO MUCHO");
                dinfo.setText("Usted no obtuvo la nota necesaria para pasar al siguiente nivel. Pero no se desanime ya que puede volver a intentarlo. Vuelva al Menú de Niveles para poder volver a revisar este tema.");
            }

            continuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("id",id_usuario);
                    i.putExtra("user",nusuario);
                    startActivity(i);
                }
            });


        }

    }

    private ProgressDialog progressDialog2;

    public void subirnivel(){
        progressDialog2 = new ProgressDialog(PruebaActivity.this);
        progressDialog2.setMessage("Actualizando nivel....");
        progressDialog2.show();
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id_user",id_usuario);

        JSONObject jobject = new JSONObject(map);
        Log.e("SITU: ",jobject.toString());
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        VariablesURL.INSERT_NIVEL,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                progressDialog2.dismiss();
                                procesarRespuestaNivel(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("SITU", "Error Volley: " + error.getMessage());
                                Toast.makeText(getApplicationContext(), "No se pudo comunicar con el servidor en este momento, inténtelo más tarde...", Toast.LENGTH_LONG).show();
                                progressDialog2.dismiss();
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );
    }

    private void procesarRespuestaNivel(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    mensajefinal();
                    break;

                case "2":
                    // Mostrar mensaje
                    Log.e("SITU ENVIAR: ", "MAL");

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
