package itcr.deportizate;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Boga on 09.06.2016.
 */
public class Repeticion {

    private Ejercicio ejercicio;
    private int num_repeticiones;
    private String fecha;

    public Repeticion() {    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setFechaHoy() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.fecha = format.format(new Date());
    }

    public int getNum_repeticiones() {
        return num_repeticiones;
    }

    public void setNum_repeticiones(int num_repeticiones) {
        this.num_repeticiones = num_repeticiones;
    }
}
