package itcr.deportizate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import itcr.deportizate.DBHandler;
import itcr.deportizate.Ejercicio;
import itcr.deportizate.R;
import itcr.deportizate.RecordArrayAdapter;
import itcr.deportizate.RutinaArrayAdapter;

public class RutinaActivity extends AppCompatActivity {
    String persona;
    ListView listview;

    private List<Integer> rutina_brazos;
    private List<Integer> rutina_pecho;
    private List<Integer> rutina_espalda;
    private List<Integer> rutina_piernas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_rutina);
        String[] values;
        Bundle extras = getIntent().getExtras();
        persona = extras.getString("Persona");

        if(persona.equals("AMayor")){
            values = new String[] {"Rutina de Brazos","Rutina de Piernas"};
            rutina_brazos = new ArrayList<Integer>(Arrays.asList(13,43,37,10,59,60,61,63));
            rutina_pecho = new ArrayList<Integer>();
            rutina_espalda = new ArrayList<Integer>();
            rutina_piernas = new ArrayList<Integer>(Arrays.asList(40,31,19,3,65,66,67));
        }
        else if(persona.equals("Joven")){
            values = new String[] {"Rutina de Brazos","Rutina de Pecho","Rutina de Espalda","Rutina de Piernas"};
            rutina_brazos = new ArrayList<Integer>(Arrays.asList(8,11,20,4,57,99,91,97));
            rutina_pecho = new ArrayList<Integer>(Arrays.asList(25,35,41,4,55,92,93));
            rutina_espalda = new ArrayList<Integer>(Arrays.asList(47,50,16,4,44,98,95));
            rutina_piernas = new ArrayList<Integer>(Arrays.asList(6,16,53,1,55,94,103,87,101));
        }
        else{
            values = new String[] {"Rutina de Brazos","Rutina de Pecho","Rutina de Espalda","Rutina de Piernas"};
            rutina_brazos = new ArrayList<Integer>(Arrays.asList(11,8,20,4,55,91,97,99));
            rutina_pecho = new ArrayList<Integer>(Arrays.asList(35,41,25,55,4,92,93));
            rutina_espalda = new ArrayList<Integer>(Arrays.asList(16,50,47,57,4,95,98));
            rutina_piernas = new ArrayList<Integer>(Arrays.asList(53,6,16,44,1,88,94,87,100));
        }

        listview = (ListView) findViewById(R.id.contacts_list_view);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Listado.class);
                if (listview.getItemAtPosition(position).equals("Rutina de Brazos")) {
                    intent.putExtra("ListaEjercicios", (Serializable)rutina_brazos);
                    startActivityForResult(intent, 0);
                }
                else if (listview.getItemAtPosition(position).equals("Rutina de Pecho")) {
                    intent.putExtra("ListaEjercicios", (Serializable)rutina_pecho);
                    startActivityForResult(intent, 0);
                }
                else if (listview.getItemAtPosition(position).equals("Rutina de Espalda")) {
                    intent.putExtra("ListaEjercicios", (Serializable)rutina_espalda);
                    startActivityForResult(intent, 0);
                }
                else if (listview.getItemAtPosition(position).equals("Rutina de Piernas")) {
                    intent.putExtra("ListaEjercicios", (Serializable)rutina_piernas);
                    startActivityForResult(intent, 0);
                }
            }
        });
        listview.setAdapter(new RutinaArrayAdapter(this, R.layout.list_item, values));
    }
}