package itcr.deportizate;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PHOENIXLENO on 12/04/2016.
 */
public class Ejercicio implements Serializable {
    private int id;
    private String nombre;
    private String descripcion;
    private String img;
    private String persona; //AMayor, Adulto, Joven
    private String tipo;// Calentamiento/ Estiramiento/ Ejercicio
    private List<Repeticion> repeticiones;

    public Ejercicio(){
    }
    public Ejercicio(int id, String nombre, String descripcion, String img, String persona, String tipo){
        this.id = id;

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img = img;
        this.persona = persona;
        this.tipo = tipo;
    }

    public void setId(int id){ this.id = id; }
    public int getId() { return id; }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public String getPersona() {
        return persona;
    }
    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getImgDrawableId(View convertView, RecordArrayAdapter adapter) {
        String uri = "drawable/" + this.img;
        return convertView.getResources().getIdentifier(uri, "drawable", adapter.getContext().getPackageName());
    }

    public int getImgDrawableId(AppCompatActivity activity) {
        String uri = "drawable/" + this.img;
        return activity.getResources().getIdentifier(uri, "drawable", activity.getPackageName());
    }

    public void addRepeticion(Repeticion r) {
        if (repeticiones == null) {
            repeticiones = new ArrayList<>();
        }
        repeticiones.add(r);
    }

    public List<Repeticion> getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(List<Repeticion> repeticiones) {
        this.repeticiones = repeticiones;
    }
}
