package com.example.jeancarla.tutor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jean Carla on 06/12/2016.
 */
public class Funciones {

    public String Retornar_usuario(Context context, String usuario, String contrasenhia) {

        DBHelper admin = new DBHelper(context);
        SQLiteDatabase bd = admin.getWritableDatabase();


        String[] user = new String[]{usuario, contrasenhia};
        String mostrar = "";
        Cursor fila = bd.rawQuery("SELECT id FROM bisa_usuario WHERE nombre_usuario=? AND password=?", user);
        if (fila.moveToFirst()) {
            do {
                mostrar = "" + fila.getInt(0);

            } while (fila.moveToNext());


            bd.close();
            return mostrar;
        } else {
            bd.close();

            return "no";
        }


    }
}


