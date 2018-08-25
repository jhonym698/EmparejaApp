package android.emparejaapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.emparejaapp.Clases.Conexion;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaPuntajes extends AppCompatActivity {
    ListView listaView;
    Conexion conexion;
    ArrayList<String> listaPuntajes;
    boolean temporizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_puntajes);

        listaView=(ListView) findViewById(R.id.listaPuntajes);

        conexion=new Conexion(this);
        temporizador=validarTemporizador();

        if (temporizador==true){
            Toast.makeText(this, "si", Toast.LENGTH_SHORT).show();
            cargarlistaConPuntaje();
        }else{
            cargarListaSinPuntaje();
        }
    }

    private void cargarlistaConPuntaje() {

        List<String> al = new ArrayList<String>();

        try{
            String cadenaSQL="select * from puntuacion   order by puntaje desc limit 4";
            SQLiteDatabase db=conexion.getReadableDatabase();
            Cursor cursor=db.rawQuery(cadenaSQL,null);
            cursor.moveToFirst();
            int contador=0;
            while (cursor.moveToNext()){
                al.add(cursor.getString(1)+" "+Integer.toString(cursor.getInt(2)));
            }

            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, al);

            listaView.setAdapter(listAdapter);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarListaSinPuntaje() {
        List<String> al = new ArrayList<String>();

        try{
            String cadenaSQL="select * from puntuacion where tiempo='null' order by puntaje desc limit 4 ";
            SQLiteDatabase db=conexion.getReadableDatabase();
            Cursor cursor=db.rawQuery(cadenaSQL,null);
            cursor.moveToFirst();
            int contador=0;
            while (cursor.moveToNext()){
                al.add(cursor.getString(1)+" "+Integer.toString(cursor.getInt(2)));
            }

            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, al);

            listaView.setAdapter(listAdapter);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    private boolean validarTemporizador() {
        String sql="select * from opcionJuego";
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToNext();
        if (cursor.getString(0).equalsIgnoreCase("true")){
            return true;
        }else{
            return false;
        }
    }
}
    