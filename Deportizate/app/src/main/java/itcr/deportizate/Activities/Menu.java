package itcr.deportizate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import itcr.deportizate.AnalyticsTracker;
import itcr.deportizate.DBHandler;
import itcr.deportizate.R;

/**
 * Created by Boga on 09.06.2016.
 */
public class Menu extends AppCompatActivity {

    AnalyticsTracker analyticsTracker;
    DBHandler dbHandler;
    String persona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());
        dbHandler = new DBHandler(this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                persona = "Adulto";
            } else {
                persona = extras.getString("persona");
            }
        } else {
            persona = savedInstanceState.getString("persona");
        }

        setContentView(R.layout.activity_menu_adulto);
    }

    @Override
    protected void onResume() {
        super.onResume();
        analyticsTracker.trackScreen("MenuJovenes");

        ImageButton Menu_Calentamiento = (ImageButton) findViewById(R.id.CalentamientoButton);
        ImageButton Menu_Estiramiento = (ImageButton) findViewById(R.id.EstiramientoButton);
        ImageButton Menu_Ejercicio = (ImageButton) findViewById(R.id.EjerciciosButton);
        ImageButton Menu_Rutina = (ImageButton) findViewById(R.id.RutinasButton);

        Menu_Rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RutinaActivity.class);
                intent.putExtra("Persona", persona);
                startActivity(intent);
            }
        });

        Menu_Calentamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Listado.class);
                intent.putExtra("Tipo", "Calentamiento");
                intent.putExtra("Persona", persona);
                startActivity(intent);
            }
        });

        Menu_Estiramiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Listado.class);
                intent.putExtra("Tipo", "Estiramiento");
                intent.putExtra("Persona", persona);
                startActivity(intent);
            }
        });

        Menu_Ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Listado.class);
                intent.putExtra("Tipo", "Ejercicio");
                intent.putExtra("Persona", persona);
                startActivity(intent);
            }
        });
    }
}
