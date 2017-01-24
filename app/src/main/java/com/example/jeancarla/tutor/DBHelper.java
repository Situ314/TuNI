package com.example.jeancarla.tutor;

/**
 * Created by Jean Carla on 06/12/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SiTu on 14/09/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String NOMBREBD = "bigsomer_bd.sqlite";
    //Versión de la base de datos
    public static final int VERSION = 1;
    //Nombre de la tabla (puede haber tantas como necesitemos)

    //Campo 1
    public static final String ID = "id";
    //Campo 2 (también puede haber tantos campos como queramos)s

    //Constructor
    public DBHelper(Context context) {super(context, NOMBREBD, null, VERSION);}
    //DBHelper(Context context) {
    //    super(context, "/mnt/sdcard/database_name.db", null, 0);
    //}

    //Aquí crearemos la base de datos

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table users(id varchar primary key,user varchar,password varchar,nombres varchar," +
                "ap_paterno varchar,ap_materno varchar,estilo varchar,nivel varchar)");
    }

    //Aquí se actualizará la base de datos
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
