package android.emparejaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnIniciar,btnConfiguracion,btnPuntajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incio);

        btnIniciar=(Button) findViewById(R.id.btnIniciar);
        btnConfiguracion=(Button) findViewById(R.id.btnConfiguracion);
        btnPuntajes=(Button) findViewById(R.id.btnPuntajes);

        btnIniciar.setOnClickListener(this);
        btnConfiguracion.setOnClickListener(this);
        btnPuntajes.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIniciar:
                Intent intent=new Intent(InicioActivity.this,DificultadActivity.class);
                intent.putExtra("uno",getIntent().getExtras().getString("uno"));
                intent.putExtra("dos",getIntent().getExtras().getString("dos"));
                startActivity(intent);
                this.finish();
                break;
            case R.id.btnConfiguracion:
                Intent intent2=new Intent(InicioActivity.this,ConfiguracionActivity.class);
                startActivity(intent2);
                this.finish();
                break;
            case R.id.btnPuntajes:
                Intent intent3=new Intent(InicioActivity.this,ListaPuntajes.class);
                startActivity(intent3);
                this.finish();
                break;
        }
    }
}
