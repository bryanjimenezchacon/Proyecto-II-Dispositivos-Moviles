package itcr.deportizate.Activities;

import android.app.DialogFragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.List;

import itcr.deportizate.DBHandler;
import itcr.deportizate.Ejercicio;
import itcr.deportizate.R;
import itcr.deportizate.Repeticion;

/**
 * Created by Boga on 09.06.2016.
 */
public class ActividadFisica extends AppCompatActivity implements DialogRepeticiones.ListenerDialog {

    MediaPlayer mediaPlayer;
    Ejercicio ejercicio;
    ImageView img;
    TextView txt_nombre;
    TextView txt_desc;
    int tiempo = 30;
    DBHandler dbHandler;
    private List<Repeticion> listaRepeticiones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_jovenes);

        dbHandler = new DBHandler(this);

        img = (ImageView) findViewById(R.id.dt_img_ejercicio);
        txt_nombre = (TextView) findViewById(R.id.dt_nombre_ejercicio);
        txt_desc = (TextView) findViewById(R.id.dt_desc_ejercicio);

        final TextView textViewCronos= (TextView)findViewById(R.id.textViewCrono);
        Button empezar = (Button)findViewById(R.id.buttonEmpezar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                ejercicio = null;
            } else {
                ejercicio = (Ejercicio) extras.getSerializable("ejercicio");
                actualizarElementos();
            }
        } else {
            ejercicio = (Ejercicio) savedInstanceState.getSerializable("contact");
            actualizarElementos();
        }

        textViewCronos.setText("Tiempo de Ejercicio: " + tiempo);
        empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tiempo);

                new CountDownTimer(tiempo * 1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        mediaPlayer.start();
                        textViewCronos.setText("Segundos Restantes: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        textViewCronos.setText("LISTO!");
                        Toast.makeText(ActividadFisica.this, "Descanse 10 seg para continuar", Toast.LENGTH_LONG).show();
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.objetivologrado);
                        mediaPlayer.start();
                        DialogRepeticiones dialogo = new DialogRepeticiones();
                        dialogo.show(getFragmentManager(), "DialogRepeticiones");

                    }
                }.start();
            }
        });





    }

    private void rellenarTabla(){

        TableLayout tabla;
        int id_fila, num_celda = 1;

        tabla = (TableLayout)findViewById(R.id.TablaRegistro);
        listaRepeticiones =  dbHandler.getRepeticiones(ejercicio);
        try{
        //Toast.makeText(ActividadFisica.this, "Entre a RellenarTabla", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < dbHandler.getRepeticionesTotal(ejercicio); i++){

            TableRow fila = new TableRow(this);
            fila.setId(100 + i);

            TextView col1 = new TextView(this);
            col1.setId(200 + i);
            col1.setText(listaRepeticiones.get(i).getFecha().toString());
            Log.d("Repeticiones", (Integer.toString(listaRepeticiones.get(i).getNum_repeticiones())));
            //Toast.makeText(ActividadFisica.this,listaRepeticiones.get(i).getNum_repeticiones(), Toast.LENGTH_SHORT).show();
            TextView col2 = new TextView(this);
            col2.setId(300 + i);
            col2.setText("    " + Integer.toString(listaRepeticiones.get(i).getNum_repeticiones()));

            //Toast.makeText(ActividadFisica.this, listaRepeticiones.get(i).getFecha() + " -> " + Integer.toString(listaRepeticiones.get(i).getNum_repeticiones()), Toast.LENGTH_SHORT).show();
            fila.addView(col1);
            fila.addView(col2);
            tabla.addView(fila);

        }
        }catch (Exception e){
           // Toast.makeText(ActividadFisica.this, "Error" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarElementos() {
        dbHandler.getRepeticiones(ejercicio);
        img.setImageResource(ejercicio.getImgDrawableId(this));
        txt_nombre.setText(ejercicio.getNombre());
        txt_desc.setText(ejercicio.getDescripcion());
        rellenarTabla();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String repeticiones) {
        try {
            int repeticiones_int = Integer.parseInt(repeticiones);
            Repeticion repeticion = new Repeticion();
            repeticion.setEjercicio(ejercicio);
            repeticion.setNum_repeticiones(repeticiones_int);
            repeticion.setFechaHoy();
          //  Toast.makeText(ActividadFisica.this, repeticion.getFecha(), Toast.LENGTH_SHORT).show();
            dbHandler.addRepeticion(repeticion);
        } catch(NumberFormatException e) {
            Toast.makeText(this, "Debe ingresar un número entero.", Toast.LENGTH_LONG).show();
            DialogRepeticiones dialogo = new DialogRepeticiones();
            dialogo.show(getFragmentManager(), "DialogRepeticiones");
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
            Toast.makeText(this, "Negativo", Toast.LENGTH_LONG).show();
    }


}
