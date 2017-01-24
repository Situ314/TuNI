package com.example.jeancarla.tutor;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TemasAdapter adapter;
    private List<Tema> albumList;
    private String id_usuario, user, nivel, estilo;
    private static final String TAG = "SITU";
    private Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id_usuario = getIntent().getStringExtra("id");
        user = getIntent().getStringExtra("user");
        initCollapsingToolbar();

        //Toast.makeText(getApplicationContext(),"ID USUARIO: "+id_usuario+" USER: "+user,Toast.LENGTH_LONG).show();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new TemasAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        pedirDatos(user);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Lista de Temas");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {

        int n=Integer.parseInt(nivel);

        int[] covers = new int[]{
                R.drawable.uno,
                R.drawable.dos,
                R.drawable.tres,
                R.drawable.cuatro,
                R.drawable.cinco,
                R.drawable.seis,
                R.drawable.siete
              };

        int[] coversb = new int[]{
                R.drawable.unob,
                R.drawable.dosb,
                R.drawable.tresb,
                R.drawable.cuatrob,
                R.drawable.cincob,
                R.drawable.seisb,
                R.drawable.sieteb
        };


        Tema a = new Tema(id_usuario, user, estilo, "1","Introducción", "Los tres tipos de nomenclatura de compuestos inorgánicos", covers[0]);
        Tema b = new Tema(id_usuario, user, estilo, "1","Introducción", "Los tres tipos de nomenclatura de compuestos inorgánicos", coversb[0]);
        if(1<=n)
        albumList.add(a);
        else
        albumList.add(b);

        a = new Tema(id_usuario, user, estilo,"2","Óxidos", "Formación de un metal + oxígeno.", covers[1]);
        b = new Tema(id_usuario, user,  estilo, "2","Óxidos", "Formación de un metal + oxígeno.", coversb[1]);
        if(2<=n)
            albumList.add(a);
        else
            albumList.add(b);

        a = new Tema(id_usuario, user,  estilo, "3","Peróxidos", "Formación de un metal + el ión peróxido (O2).", covers[2]);
        b = new Tema(id_usuario, user, estilo, "3","Peróxidos", "Formación de un metal + el ión peróxido (O2).", coversb[2]);
        if(3<=n)
            albumList.add(a);
        else
            albumList.add(b);

        a = new Tema(id_usuario, user, estilo, "4","Hidróxidos", "Formación de un metal + grupo hidroxilo (OH).", covers[3]);
        b = new Tema(id_usuario, user, estilo, "4","Hidróxidos", "Formación de un metal + grupo hidroxilo (OH).", coversb[3]);
        if(4<=n)
            albumList.add(a);
        else
            albumList.add(b);

        a = new Tema(id_usuario, user, estilo, "5","Anhídridos", "Formulación y nomenclatura de los óxidos no metálicos (óxidos ácidos o anhídridos).", covers[4]);
        b = new Tema(id_usuario, user, estilo, "5","Anhídridos", "Formulación y nomenclatura de los óxidos no metálicos (óxidos ácidos o anhídridos).", coversb[4]);
        if(5<=n)
            albumList.add(a);
        else
            albumList.add(b);

        a = new Tema(id_usuario, user, estilo, "6","Oxiácidos", "Formación ternaria de Hidrogeno + no metal + oxígeno.", covers[5]);
        b = new Tema(id_usuario, user, estilo, "6","Oxiácidos", "Formación ternaria de Hidrogeno + no metal + oxígeno.", coversb[5]);
        if(6<=n)
            albumList.add(a);
        else
            albumList.add(b);


        a = new Tema(id_usuario, user, estilo, "7","Sales", "Formación de un metal + un no metal + oxigeno.", covers[6]);
        b = new Tema(id_usuario, user, estilo, "7","Sales", "Formación de un metal + un no metal + oxigeno.", coversb[6]);
        if(7<=n)
            albumList.add(a);
        else
            albumList.add(b);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void pedirDatos(String usua) {

        // Añadir parámetro a la URL del web service
        String newURL = VariablesURL.GET_USER + usua;

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

        Usuario usuario_guardar = gson.fromJson(response.toString(), Usuario.class);

        nivel = usuario_guardar.getNivel();
        estilo = usuario_guardar.getEstilo();
        prepareAlbums();


    }
}
