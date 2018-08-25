package android.emparejaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DificultadActivity extends AppCompatActivity {
    Button btnFacil,btnMedio,btnDificil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

        btnFacil=(Button) findViewById(R.id.btnFacil);
        btnMedio=(Button) findViewById(R.id.btnMedio);
        btnDificil=(Button) findViewById(R.id.btnDificil);

        btnDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DificultadActivity.this,MainActivity.class);
                intent.putExtra("uno",getIntent().getExtras().getString("uno"));
                intent.putExtra("dos",getIntent().getExtras().getString("dos"));
                startActivity(intent);
            }
        });
        btnMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DificultadActivity.this,MedioActivity.class);
                intent.putExtra("uno",getIntent().getExtras().getString("uno"));
                intent.putExtra("dos",getIntent().getExtras().getString("dos"));
                startActivity(intent);
            }
        });
        btnFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DificultadActivity.this,FacilActivity.class);
                intent.putExtra("uno",getIntent().getExtras().getString("uno"));
                intent.putExtra("dos",getIntent().getExtras().getString("dos"));
                startActivity(intent);
            }
        });

    }
}
