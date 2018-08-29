package android.emparejaapp.Clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC27 on 16/08/2018.
 */

public class Conexion extends SQLiteOpenHelper {

    public Conexion(Context context) {
        super(context, "game", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table opcionJuego(tiempo text)");
        //HELLO

        db.execSQL("create table puntuacion(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text, puntaje INTEGER, tiempo text )");

        db.execSQL("insert into puntuacion(nombre,puntaje,tiempo) values('elian',1000,'30')");
        db.execSQL("insert into puntuacion(nombre,puntaje) values('elian',1000)");
        db.execSQL("insert into opcionJuego values('true')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
