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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class ContenidoVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private Button sib,nob;
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private TextView tv_link;
    private String video, ayuda,nombre,resumen, id_tema, id_usuario,nusuario;
    private static final String TAG = "SITU";
    private FloatingActionButton fab;
    private Gson gson=new Gson();
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_video);

        id_tema = getIntent().getStringExtra("id");
        id_usuario = getIntent().getStringExtra("user");
        nusuario = getIntent().getStringExtra("nusuario");

        tv_link = (TextView) findViewById(R.id.tv_link);

        pedirDatos(id_tema);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ContenidoVideo.this);
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            player.cueVideo(video); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = "adada";
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
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

        id_tema = contenido_guardar.getId();
        video = contenido_guardar.getLink();
        nombre = contenido_guardar.getNombre();
        resumen = contenido_guardar.getResumen();
        ayuda = contenido_guardar.getAyuda();

        tv_link.setText("https://www.youtube.com/watch?v="+video);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

    }

}
