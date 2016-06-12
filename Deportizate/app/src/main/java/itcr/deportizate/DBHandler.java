package itcr.deportizate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200;


/**
 * Created by PHOENIXLENO on 12/04/2016.
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static String DB_PATH = "/data/data/itcr.deportizate/databases/";
    private static final String DATABASE_NAME = "deportizate.db";
    private SQLiteDatabase db;
    private final Context context;

    private static final String TABLE_EJERCICIOS = "Ejercicio";
    private static final String KEY_ID = "__id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_DESCRIPCION = "descripcion";
    private static final String KEY_IMG = "img";
    private static final String KEY_PERSONA = "persona";
    private static final String KEY_TIPO = "tipo";

    private static final String TABLE_REPETICIONES = "Repeticion";
    private static final String KEY_ID_EJERCICIO = "id_ejercicio";
    private static final String KEY_FECHA = "fecha";
    private static final String KEY_NUM_REPETICIONES = "num_repeticiones";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if (!dbExist) {
            this.getReadableDatabase();

            try {
                copyDatabase();
            } catch(IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;

        try {
            String path = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e) {
            e.printStackTrace();
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }

    private void copyDatabase() throws IOException {
        InputStream input = context.getAssets().open(DATABASE_NAME);
        String archivoSalida = DB_PATH + DATABASE_NAME;
        OutputStream salida = new FileOutputStream(archivoSalida);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            salida.write(buffer, 0, length);
        }

        salida.flush();
        salida.close();
        input.close();
    }

    public void openDatabase() throws SQLException {
        String path = DB_PATH + DATABASE_NAME;
        this.db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (db != null) {
            db.close();
        }
        super.close();
    }

    public List<Ejercicio> getAll(String Tipo, String Persona) {
        List<Ejercicio> listaEjercicios = new ArrayList<>();

        String selectQuery = " SELECT * FROM " + TABLE_EJERCICIOS + " WHERE tipo = '" + Tipo + "' and persona = '" + Persona +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Ejercicio ejercicios = new Ejercicio();

                ejercicios.setId(Integer.parseInt(cursor.getString(0)));
                ejercicios.setNombre(cursor.getString(1));
                ejercicios.setDescripcion(cursor.getString(2));
                ejercicios.setImg(cursor.getString(3));
                ejercicios.setPersona(cursor.getString(4));
                ejercicios.setTipo(cursor.getString(5));

                listaEjercicios.add(ejercicios);
            } while (cursor.moveToNext());
        }

        return listaEjercicios;
    }

    public int getEjerciciosCount() {
        String countQuery = "SELECT * FROM " + TABLE_EJERCICIOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateEjercicio(Ejercicio ejercicio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, ejercicio.getNombre());
        values.put(KEY_DESCRIPCION, ejercicio.getDescripcion());
        values.put(KEY_IMG, ejercicio.getImg());
        values.put(KEY_PERSONA, ejercicio.getPersona());
        values.put(KEY_TIPO, ejercicio.getTipo());

        return db.update(TABLE_EJERCICIOS,values, KEY_ID + " = ?" ,
                new String[] { String.valueOf(ejercicio.getId()) });
    }

    public void deleteEjercicio(Ejercicio ejercicio) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EJERCICIOS, KEY_ID + " = ?",
                new String[] {String.valueOf(ejercicio.getId()) });
        db.close();
    }

    public void addEjercicio(Ejercicio ejercicio){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, ejercicio.getNombre());
        values.put(KEY_DESCRIPCION, ejercicio.getDescripcion());
        values.put(KEY_IMG, ejercicio.getImg());
        values.put(KEY_PERSONA, ejercicio.getPersona());
        values.put(KEY_TIPO, ejercicio.getTipo());

        db.insert(TABLE_EJERCICIOS, null, values);

        db.close();
    }

    public Ejercicio getEjercicio(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_EJERCICIOS, new String[]{ KEY_ID, KEY_NOMBRE, KEY_DESCRIPCION, KEY_IMG, KEY_PERSONA, KEY_TIPO }, KEY_ID + "=?", new String[]{ String.valueOf(id) }, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        String nombre = cursor.getString(1);
        String descripcion = cursor.getString(2);
        String img = cursor.getString(3);
        String persona = cursor.getString(4);
        String tipo = cursor.getString(5);

        Ejercicio ejercicios = new Ejercicio(Integer.parseInt(cursor.getString(0)), nombre, descripcion, img, persona, tipo);
        return ejercicios;
    }

    public List<Repeticion> getRepeticiones(Ejercicio ejercicio) {
        List<Repeticion> listaReps = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_REPETICIONES, new String[]{
                KEY_ID_EJERCICIO,
                KEY_FECHA,
                KEY_NUM_REPETICIONES}, KEY_ID_EJERCICIO + "=?", new String[]{ String.valueOf(ejercicio.getId()) }, null, null, null, null);

        int i = 0;

        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Repeticion r = new Repeticion();
                    r.setFecha(cursor.getString(1));
                    r.setNum_repeticiones(cursor.getInt(2));

                    listaReps.add(r);

                    i++;
                } while(cursor.moveToNext());
            }
        }
     //   Toast.makeText(context, , Toast.LENGTH_SHORT).show();
        return listaReps;
    }

    public int getRepeticionesTotal(Ejercicio ejercicio) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_REPETICIONES, new String[]{
                KEY_ID_EJERCICIO,
                KEY_FECHA,
                KEY_NUM_REPETICIONES}, KEY_ID_EJERCICIO + "=?", new String[]{ String.valueOf(ejercicio.getId()) }, null, null, null, null);

        int i = 0;

        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    i++;
                } while(cursor.moveToNext());
            }
        }
       // Toast.makeText(context,"Totalll = " + i, Toast.LENGTH_SHORT).show();
        return i;
    }

    public void addRepeticion(Repeticion repeticion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_EJERCICIO, repeticion.getEjercicio().getId());
        values.put(KEY_FECHA, repeticion.getFecha());
        values.put(KEY_NUM_REPETICIONES, repeticion.getNum_repeticiones());


        db.insert(TABLE_REPETICIONES, null, values);

        db.close();
    }
}