package android.emparejaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.emparejaapp.Clases.Conexion;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimerTask;

public class FacilActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btn1,btn2,btn3,btn4;
    TextView lblJugadorUno,lblJugadorDos,lblPuntosUno,lblPuntosDos,lblTemporizador,labelTiempo;

    int imagenes[]={R.drawable.uno,R.drawable.dos};
    int arrayImage[] = new int[4];
    int seleccionado1=0;
    int seleccionado2=0;

    int idOriginales[];
    int puntosUno=0;
    int puntosDos=0;

    String judadorUno="";
    String judadorDos="";

    int turnoJugador=0;
    Conexion conexion;

    int contadorAciertos=0;

    int tiempoCorrido=0;
    boolean temporizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facil);

        btn1=(ImageButton) findViewById(R.id.btn1);
        btn2=(ImageButton) findViewById(R.id.btn2);
        btn3=(ImageButton) findViewById(R.id.btn3);
        btn4=(ImageButton) findViewById(R.id.btn4);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);



        lblJugadorUno=(TextView) findViewById(R.id.lblJudadorUno);
        lblJugadorDos=(TextView) findViewById(R.id.lblJudadorDos);
        lblPuntosUno=(TextView) findViewById(R.id.lblPuntosUno);
        lblPuntosDos=(TextView) findViewById(R.id.lblPuntosDos);
        lblTemporizador=(TextView) findViewById(R.id.lblTemporizador);
        labelTiempo=(TextView) findViewById(R.id.labelTiempo);

        judadorDos=getIntent().getExtras().getString("uno");
        judadorUno=getIntent().getExtras().getString("dos");

        lblJugadorUno.setText(judadorUno);
        lblJugadorDos.setText(judadorDos);


        idOriginales= new int[]{R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4};
        cargarArregloImagenes();

        conexion=new Conexion(this);

        cargarTurnoAleatorio();

        temporizador=validarTemporizador();
        if (temporizador==true){
            activarTemporizador();
        }else{
            lblTemporizador.setVisibility(View.INVISIBLE);
            labelTiempo.setVisibility(View.INVISIBLE);
        }

    }

    CountDownTimer timer;
    private void activarTemporizador() {
        timer=new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                lblTemporizador.setText(Long.toString(l/1000));
                tiempoCorrido++;
            }

            @Override
            public void onFinish() {
                terminarJuego();
            }
        }.start();
    }

    private void terminarJuego() {
        guardarPuntos();
        try{
            String auxiliar="";
            if (temporizador==true){
                timer.cancel();
                auxiliar="<b> Tiempo </b>"+Integer.toString(tiempoCorrido);
            }else{
                auxiliar="";
            }
            String ganador1="";
            String ganador2="";
            if (puntosUno>puntosDos){
                ganador1="Ganador";
            }else{
                ganador2="Ganador";
            }


            AlertDialog.Builder ventana=new AlertDialog.Builder(FacilActivity.this);

            ventana.setTitle("JUEGO TERMINADO");
            ventana.setMessage(Html.fromHtml("<li><b>"+judadorUno+"</b> "+Integer.toString(puntosUno)+"      <b>"+ganador1+"</b></li><h3><h3>" +
                    "<li><b>"+judadorDos+"</b> "+Integer.toString(puntosDos)+"        <b>"+ganador2+"</b></li>" +
                    "<h3></h3> "+auxiliar+""));
            ventana.setPositiveButton("Compartir en redes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    abrirRedes();
                }
            }).setNegativeButton("Jugar denuevo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    startActivity(getIntent());
                }
            }).setNeutralButton("Volver al inicio", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent(FacilActivity.this,InicioActivity.class);
                    intent.putExtra("uno",getIntent().getExtras().getString("uno"));
                    intent.putExtra("dos",getIntent().getExtras().getString("dos"));
                    startActivity(intent);
                }
            });
            ventana.show();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    public String puntaje="";
    private void abrirRedes() {

        if (puntosUno>puntosDos){
            puntaje=judadorUno+" ha ganado en EmparejaApp app su puntaje es "+Integer.toString(puntosUno);
        }else{
            puntaje=judadorDos+" ha ganado en EmparejaApp su puntaje es "+Integer.toString(puntosDos);
        }
        AlertDialog.Builder ventanaRedes= new AlertDialog.Builder(FacilActivity.this);
        ventanaRedes.setTitle("Seleccione la red social");
        final CharSequence opciones []={"Facebook","Twetter","Otro"};
        ventanaRedes.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Facebook")){
                    Intent sendText = new Intent();
                    sendText.setAction(Intent.ACTION_SEND);
                    sendText.putExtra(Intent.EXTRA_TEXT, puntaje);
                    sendText.setType("text/plain");
                    sendText.setPackage("com.facebook.lite");
                    startActivity(sendText);
                }
                if (opciones[i].equals("Twetter")){
                    Intent sendText = new Intent();
                    sendText.setAction(Intent.ACTION_SEND);
                    sendText.putExtra(Intent.EXTRA_TEXT, puntaje);
                    sendText.setType("text/plain");
                    sendText.setPackage("com.twitter.android");
                    startActivity(sendText);
                }
                if (opciones[i].equals("Otro")){
                    Intent sendText = new Intent();
                    sendText.setAction(Intent.ACTION_SEND);
                    sendText.putExtra(Intent.EXTRA_TEXT, puntaje);
                    sendText.setType("text/plain");
                    startActivity(sendText);
                }

            }
        }).show();
    }

    private void guardarPuntos() {
        String sql;
        if (temporizador==true){
            sql="insert into puntuacion(nombre,puntaje,tiempo) values('"+judadorUno+"',"+puntosUno+",'"+tiempoCorrido+"')," +
                    "('"+judadorDos+"',"+puntosDos+",'"+tiempoCorrido+"')";
        }else{
            sql="insert into puntuacion(nombre,puntaje,tiempo) values('"+judadorUno+"',"+puntosUno+",'null')," +
                    "('"+judadorDos+"',"+puntosDos+",'null')";
        }





        try{

            SQLiteDatabase db=conexion.getWritableDatabase();
            db.execSQL(sql);
            Toast.makeText(this, "resultados guardados", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
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

    private void cargarTurnoAleatorio() {
        turnoJugador= (int) (Math.random()*2);
        if (turnoJugador==0){
            lblJugadorUno.setTextColor(Color.GREEN);
            lblPuntosUno.setTextColor(Color.GREEN);
            lblJugadorDos.setTextColor(Color.GRAY);
            lblPuntosDos.setTextColor(Color.GRAY);
        }else{
            lblJugadorDos.setTextColor(Color.GREEN);
            lblPuntosDos.setTextColor(Color.GREEN);
            lblJugadorUno.setTextColor(Color.GRAY);
            lblPuntosUno.setTextColor(Color.GRAY);
        }

    }

    private void cargarArregloImagenes() {
        int contador=0;

        for (int i=0; i<imagenes.length;){
            int random= (int) (Math.random()*4);
            if (arrayImage[random]==0){
                contador++;
                arrayImage[random]=imagenes[i];
                if (contador==2){
                    contador=0;
                    i++;
                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                btn1.setImageResource(arrayImage[0]);
                btn1.setId(arrayImage[0]);
                if (seleccionado1==0){
                    seleccionado1=arrayImage[0];
                }else{
                    seleccionado2=arrayImage[0];
                    comprobarID();
                }
                break;

            case R.id.btn2:
                btn2.setImageResource(arrayImage[1]);
                btn2.setId(arrayImage[1]);
                if (seleccionado1==0){
                    seleccionado1=arrayImage[1];
                }else{
                    seleccionado2=arrayImage[1];
                    comprobarID();
                }
                break;

            case R.id.btn3:
                btn3.setImageResource(arrayImage[2]);
                btn3.setId(arrayImage[2]);
                if (seleccionado1==0){
                    seleccionado1=arrayImage[2];
                }else{
                    seleccionado2=arrayImage[2];
                    comprobarID();
                }
                break;

            case R.id.btn4:
                btn4.setImageResource(arrayImage[3]);
                btn4.setId(arrayImage[3]);
                if (seleccionado1==0){
                    seleccionado1=arrayImage[3];
                }else{
                    seleccionado2=arrayImage[3];
                    comprobarID();
                }
                break;



        }
    }

    private void comprobarID() {
        disabledButton();
        if (seleccionado1==seleccionado2){
            if (btn1.getId()==seleccionado1 && btn1.getId()==seleccionado2){btn1.setVisibility(View.INVISIBLE);}
            if (btn2.getId()==seleccionado1 && btn2.getId()==seleccionado2){btn2.setVisibility(View.INVISIBLE);}
            if (btn3.getId()==seleccionado1 && btn3.getId()==seleccionado2){btn3.setVisibility(View.INVISIBLE);}
            if (btn4.getId()==seleccionado1 && btn4.getId()==seleccionado2){btn4.setVisibility(View.INVISIBLE);}


            if (turnoJugador==0){
                puntosUno+=100;
                turnoJugador=1;
                cambiarColores();
            }else{
                puntosDos+=100;
                turnoJugador=0;
                cambiarColores();
            }
            enabledButton();
            contadorAciertos++;
        }else{
            if (turnoJugador==0){
                puntosUno--;
                turnoJugador=1;
                cambiarColores();
            }else{
                puntosDos--;
                turnoJugador=0;
                cambiarColores();
            }
            retornarID();
            new Handler().postDelayed(new TimerTask() {
                @Override
                public void run() {
                    ocultarImagen();
                    enabledButton();

                }
            },1000);
        }
        seleccionado1=0;
        seleccionado2=0;

        if (contadorAciertos==2){
            terminarJuego();
        }


    }

    private void cambiarColores() {
        lblPuntosUno.setText(Integer.toString(puntosUno));
        lblPuntosDos.setText(Integer.toString(puntosDos));
        if (turnoJugador==0){
            lblJugadorUno.setTextColor(Color.GREEN);
            lblPuntosUno.setTextColor(Color.GREEN);
            lblJugadorDos.setTextColor(Color.GRAY);
            lblPuntosDos.setTextColor(Color.GRAY);
            Toast.makeText(this, "Turno jugador uno", Toast.LENGTH_SHORT).show();
        }else{
            lblJugadorDos.setTextColor(Color.GREEN);
            lblPuntosDos.setTextColor(Color.GREEN);
            lblJugadorUno.setTextColor(Color.GRAY);
            lblPuntosUno.setTextColor(Color.GRAY);
            Toast.makeText(this, "Turno jugador dos", Toast.LENGTH_SHORT).show();
        }
    }

    private void enabledButton() {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);

    }

    private void disabledButton() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);

    }

    private void ocultarImagen() {
        btn1.setImageResource(R.drawable.carta);
        btn2.setImageResource(R.drawable.carta);
        btn3.setImageResource(R.drawable.carta);
        btn4.setImageResource(R.drawable.carta);

    }

    private void retornarID() {
        btn1.setId(idOriginales[0]);
        btn2.setId(idOriginales[1]);
        btn3.setId(idOriginales[2]);
        btn4.setId(idOriginales[3]);

    }
}
