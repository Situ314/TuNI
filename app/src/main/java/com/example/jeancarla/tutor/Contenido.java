package com.example.jeancarla.tutor;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

public class Contenido extends AppCompatActivity {

    private Button sib,nob;
    private String id_tema, id_usuario, nusuario;
    private String contenido,ayuda,nombre="Contenido",resumen, video, id_prueba;
    private static final String TAG = "SITU";
    private Gson gson=new Gson();
    private ImageView content;
    private TextView tv_link;
    private FloatingActionButton fab;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido);

        id_tema = getIntent().getStringExtra("id");
        id_usuario = getIntent().getStringExtra("user");
        nusuario = getIntent().getStringExtra("nusuario");

        content = (ImageView) findViewById(R.id.content);
        tv_link = (TextView) findViewById(R.id.tv_link);
        pedirDatos(id_tema);
      //  Toast.makeText(getApplicationContext(),id_usuario,Toast.LENGTH_LONG).show();
        setToolbar();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(Contenido.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_contenido);
                dialog.show();

                sib = (Button) dialog.findViewById(R.id.si_boton);
                nob = (Button) dialog.findViewById(R.id.no_boton);

                sib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getApplicationContext(),PruebaActivity.class);
                        i.putExtra("user",id_usuario);
                        i.putExtra("id_prueba",id_tema);
                        i.putExtra("nusuario",nusuario);
                        startActivity(i);
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
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(nombre);

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
        String newURL = VariablesURL.GET_TEMA + usua;

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

        ContentValues values1 = new ContentValues();
        System.out.println("SOMETHING ==============" + response.toString());

        ClassContenido contenido_guardar = gson.fromJson(response.toString(), ClassContenido.class);

        id_prueba = contenido_guardar.getId();
        video = contenido_guardar.getLink();
        contenido = contenido_guardar.getContenido();
        nombre = contenido_guardar.getNombre();
        resumen = contenido_guardar.getResumen();
        ayuda = contenido_guardar.getAyuda();

        Glide.with(getApplicationContext())
                .load(contenido)
                .into(content);

        tv_link.setText("https://www.youtube.com/watch?v="+video);
    }
}
