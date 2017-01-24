package com.example.jeancarla.tutor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cuestionario extends AppCompatActivity {

    private String enviar = "";
    DBHelper crearBD;
    private SQLiteDatabase db;
    private String info_reflexivo="Antepone la reflexión a la acción y observa con detenimiento las distintas experiencias. Les gusta considerar las experiencias y observarlas desde diferentes perspectivas. Recogen datos, analizándolos con detenimiento antes de llegar a alguna conclusión. Son prudentes les gusta considerar todas las alternativas posibles antes de realizar un movimiento. Disfrutan observando la actuación de los demás, escuchan a los demás " +
            "y no intervienen hasta que no se han adueñado de la situación. " +
            "Crean a su alrededor un aire ligeramente distante y Condescendiente.";
    private String info_activo="Busca experiencias nuevas, son de mente abierta, nada escépticos y " +
            "acometen con entusiasmo las tareas nuevas.\n" +
            "Son muy activos, piensan que hay que intentarlo todo por lo menos una vez. En cuanto desciende " +
            "la excitación de una novedad comienzan a buscar la próxima. Se crecen ante los desafíos que suponen nuevas experiencias," +
            " y se aburren con los largos plazos. Son personas muy de grupo que se involucran en los asuntos de los demás y centran a su alrededor todas las actividades";
    private String info_teorico="Enfocan los problemas de forma vertical escalonada, por etapas lógicas. " +
            "Tienden a ser perfeccionistas Integran los hechos en teorías coherentes. Les gusta analizar y sintetizar. " +
            "Son profundos en su sistema de pensamiento, a la hora de establecer principios, teorías y modelos. Para ellos si es lógico es bueno. " +
            "Buscan la racionalidad y la objetividad huyendo de lo subjetivo y de lo ambiguo.";
    private String info_pragmatico="Su punto fuerte es la experimentación y la aplicación de ideas. " +
            "Descubren el aspecto positivo de las nuevas ideas y aprovechan la primera oportunidad para experimentarlas. " +
            "Les gusta actuar rápidamente y con seguridad con aquellas ideas y proyectos que les atraen. " +
            "Tienden a ser impacientes cuando hay personas que teorizan.";
    private int activo, reflexivo, teorico, pragamatico;
    private Dialog dialog, dialog2;
    private String id_usuario, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);
        id_usuario = getIntent().getStringExtra("id");
        user = getIntent().getStringExtra("user");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        dialog = new Dialog(Cuestionario.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_nuevo);
        dialog.show();

        activo = 0;
        reflexivo = 0;
        pragamatico = 0;
        teorico = 0;

        Button ir = (Button) dialog.findViewById(R.id.si_boton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviarDatos();
            }
        });
        ir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Cuestionario");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.act1:
                if (checked)
                    activo++;
                else
                    activo--;
                break;
            case R.id.act2:
                if (checked)
                    activo++;
                else
                    activo--;
                break;
            case R.id.act3:
                if (checked)
                    activo++;
                else
                    activo--;
                break;
            case R.id.act4:
                if (checked)
                    activo++;
                else
                    activo--;
                break;
            case R.id.act5:
                if (checked)
                    activo++;
                else
                    activo--;
                break;
            case R.id.act6:
                if (checked)
                    activo++;
                else
                    activo--;
                break;
            case R.id.act7:
                if (checked)
                    activo++;
                else
                    activo--;
                break;
            case R.id.act8:
                if (checked)
                    activo++;
                else
                    activo--;
                break;
            case R.id.ref1:
                if (checked)
                    reflexivo++;
                else
                    reflexivo--;
                break;
            case R.id.ref2:
                if (checked)
                    reflexivo++;
                else
                    reflexivo--;
                break;
            case R.id.ref3:
                if (checked)
                    reflexivo++;
                else
                    reflexivo--;
                break;
            case R.id.ref4:
                if (checked)
                    reflexivo++;
                else
                    reflexivo--;
                break;
            case R.id.ref5:
                if (checked)
                    reflexivo++;
                else
                    reflexivo--;
                break;
            case R.id.ref6:
                if (checked)
                    reflexivo++;
                else
                    reflexivo--;
                break;
            case R.id.ref7:
                if (checked)
                    reflexivo++;
                else
                    reflexivo--;
                break;
            case R.id.ref8:
                if (checked)
                    reflexivo++;
                else
                    reflexivo--;
                break;
            case R.id.teo1:
                if (checked)
                    teorico++;
                else
                    teorico--;
                break;
            case R.id.teo2:
                if (checked)
                    teorico++;
                else
                    teorico--;
                break;
            case R.id.teo3:
                if (checked)
                    teorico++;
                else
                    teorico--;
                break;
            case R.id.teo4:
                if (checked)
                    teorico++;
                else
                    teorico--;
                break;
            case R.id.teo5:
                if (checked)
                    teorico++;
                else
                    teorico--;
                break;
            case R.id.teo6:
                if (checked)
                    teorico++;
                else
                    teorico--;
                break;
            case R.id.teo7:
                if (checked)
                    teorico++;
                else
                    teorico--;
                break;
            case R.id.teo8:
                if (checked)
                    teorico++;
                else
                    teorico--;
                break;
            case R.id.pra1:
                if (checked)
                    pragamatico++;
                else
                    pragamatico--;
                break;
            case R.id.pra2:
                if (checked)
                    pragamatico++;
                else
                    pragamatico--;
                break;
            case R.id.pra3:
                if (checked)
                    pragamatico++;
                else
                    pragamatico--;
                break;
            case R.id.pra4:
                if (checked)
                    pragamatico++;
                else
                    pragamatico--;
                break;
            case R.id.pra5:
                if (checked)
                    pragamatico++;
                else
                    pragamatico--;
                break;
            case R.id.pra6:
                if (checked)
                    pragamatico++;
                else
                    pragamatico--;
                break;
            case R.id.pra7:
                if (checked)
                    pragamatico++;
                else
                    pragamatico--;
                break;
            case R.id.pra8:
                if (checked)
                    pragamatico++;
                else
                    pragamatico--;
                break;
        }
    }

    private ProgressDialog progressDialog;
    public void enviarDatos() {
        progressDialog = new ProgressDialog(Cuestionario.this);
        progressDialog.setMessage("Descargando Tareas....");
        progressDialog.show();
        int largest = Collections.max(Arrays.asList(activo, reflexivo, teorico,
                pragamatico));

        Toast.makeText(getApplicationContext(), "ACTIVO: " + activo + " REFLEXIVO: " + reflexivo + " TEORICO: " + teorico + " PRAGMATICO: " + pragamatico, Toast.LENGTH_LONG).show();

        crearBD = new DBHelper(Cuestionario.this);
        db = crearBD.getWritableDatabase();
        if (activo == largest) {
            Toast.makeText(getApplicationContext(), "ACTIVO", Toast.LENGTH_LONG).show();
            enviar = "1";
        } else if (reflexivo == largest) {
            Toast.makeText(getApplicationContext(), "REFLEXIVO: ", Toast.LENGTH_LONG).show();
            enviar = "3";
        } else if (teorico == largest) {
            Toast.makeText(getApplicationContext(), "TEORICO: ", Toast.LENGTH_LONG).show();
            enviar = "4";
        } else if (pragamatico == largest) {
            Toast.makeText(getApplicationContext(), "PRAGMATICO: ", Toast.LENGTH_LONG).show();
            enviar = "2";
        }

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id",id_usuario);
        map.put("tipo",enviar);


        JSONObject jobject = new JSONObject(map);
        Log.e("SITU: ",jobject.toString());
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        VariablesURL.INSERT_CUESTIONARIO,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                progressDialog.dismiss();
                                procesarRespuesta(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("SITU", "Error Volley: " + error.getMessage());
                                Toast.makeText(getApplicationContext(), "No se pudo comunicar con el servidor en este momento, inténtelo más tarde...", Toast.LENGTH_LONG).show();
                                db.close();
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

    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":

                    dialog2 = new Dialog(Cuestionario.this);
                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.dialog_test);
                    dialog2.show();

                    TextView tipo = (TextView) dialog2.findViewById(R.id.tipo);
                    TextView info = (TextView) dialog2.findViewById(R.id.info_tipo);
                    Button continuar = (Button) dialog2.findViewById(R.id.si_boton);

                    if(enviar.equals("1")){
                        tipo.setText("ACTIVO");
                        info.setText(info_activo);
                    }else if(enviar.equals("2")){
                        tipo.setText("PRAGMÁTICO");
                        info.setText(info_pragmatico);
                    }else if(enviar.equals("3")){
                        tipo.setText("REFLEXIVO");
                        info.setText(info_reflexivo);
                    }else if(enviar.equals("4")){
                        tipo.setText("TEÓRICO");
                        info.setText(info_teorico);
                    }

                    continuar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("SITU ENVIAR: ", "BIEN");
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.putExtra("id",id_usuario);
                            i.putExtra("user",user);
                            finish();
                            startActivity(i);
                        }
                    });

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
