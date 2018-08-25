package android.emparejaapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.emparejaapp.Clases.Conexion;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class ConfiguracionActivity extends AppCompatActivity {
    Button button, atras;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        button = (Button) findViewById(R.id.button);
        atras = (Button) findViewById(R.id.atras);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        cargarCheckbox();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfiguracionActivity.this,InicioActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadenaSQL="";
                if (checkBox.isChecked()){
                    cadenaSQL="update opcionJuego set tiempo='true'";
                }else{
                    cadenaSQL="update opcionJuego set tiempo='false'";
                }

                Conexion conexion=new Conexion(ConfiguracionActivity.this);
                SQLiteDatabase db=conexion.getWritableDatabase();
                db.execSQL(cadenaSQL);

                Intent intent = new Intent(ConfiguracionActivity.this,InicioActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cargarCheckbox() {
        String cadenaSQL="select tiempo from opcionJuego";
        Conexion conexion=new Conexion(this);
        SQLiteDatabase db=conexion.getReadableDatabase();

        Cursor cursor=db.rawQuery(cadenaSQL,null);
        cursor.moveToNext();
        if (cursor.getString(0).equalsIgnoreCase("false")){
            checkBox.setChecked(false);
        }else{
            checkBox.setChecked(true);
        }
    }

}
