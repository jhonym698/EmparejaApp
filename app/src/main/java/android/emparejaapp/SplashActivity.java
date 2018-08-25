package android.emparejaapp;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    EditText namePlayerUno, namePlayerDos;
    Button salir, continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                createLoginDialogo().show();
            }
        },2000);
    }

    public AlertDialog createLoginDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.item_ingreso, null);
        builder.setView(v);
        namePlayerUno = (EditText)v.findViewById(R.id.txtPlayerUno);
        namePlayerDos = (EditText)v.findViewById(R.id.txtPlayerDos);
        salir = (Button) v.findViewById(R.id.salir);
        continuar = (Button) v.findViewById(R.id.continuar);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = namePlayerUno.getText().toString();
                String name2 = namePlayerDos.getText().toString();
                if (name1.equals("")|| name2.equals("")){
                    Toast.makeText(v.getContext(), "Ingrese datos completos", Toast.LENGTH_SHORT).show();
                } else {
                    if (name1.equals(name2)){
                        Toast.makeText(v.getContext(), "Los nombres no puedes ser iguales", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, InicioActivity.class);
                        intent.putExtra("uno",name1);
                        intent.putExtra("dos",name2);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }
                }
            }
        });
        return builder.create();
    }


}
