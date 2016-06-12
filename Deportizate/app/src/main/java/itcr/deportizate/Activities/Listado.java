package itcr.deportizate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import itcr.deportizate.DBHandler;
import itcr.deportizate.Ejercicio;
import itcr.deportizate.R;
import itcr.deportizate.RecordArrayAdapter;

/**
 * Created by Boga on 09.06.2016.
 */
public class Listado extends AppCompatActivity {

    ListView listview;
    private List<Ejercicio> ejercicios;
    private List<Integer> ejerciciosRutina;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_jovenes);

        dbHandler = new DBHandler(this);

        try {
            dbHandler.createDatabase();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            dbHandler.openDatabase();
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        listview = (ListView) findViewById(R.id.contacts_list_view);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    Intent intent = new Intent(getApplicationContext(), ActividadFisica.class);
                    intent.putExtra("ejercicio", ejercicios.get(position));
                    startActivityForResult(intent, 0);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras.getString("Tipo") != null) {
            popularListView();
        } else {
            ejerciciosRutina = (List<Integer>) extras.getSerializable("ListaEjercicios");
            popularListView(ejerciciosRutina);
        }
    }

    private void popularListView() {
        dbHandler = new DBHandler(this);
        Bundle extras = getIntent().getExtras();
        ejercicios = dbHandler.getAll(extras.getString("Tipo"), extras.getString("Persona"));
        listview.setAdapter(new RecordArrayAdapter(this, R.layout.list_item, ejercicios));
    }

    private void popularListView(List<Integer> ejerciciosRutina) {
        ejercicios = new ArrayList<>();
        if(ejerciciosRutina != null) {
            dbHandler = new DBHandler(this);
            for (Integer numeroEjercicio : ejerciciosRutina) {
                ejercicios.add(dbHandler.getEjercicio(numeroEjercicio.intValue()));
            }
        }
        listview.setAdapter(new RecordArrayAdapter(this, R.layout.list_item, ejercicios));
    }
}
