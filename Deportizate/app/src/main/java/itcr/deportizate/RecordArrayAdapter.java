package itcr.deportizate;

/**
 * Created by Boga on 04.06.2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Boga on 07.05.2016.
 */
public class RecordArrayAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;
    private List records;
    private int resource;

    public RecordArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.records = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }

        final Ejercicio ejercicio = (Ejercicio) records.get(position);
        TextView tv = (TextView) convertView.findViewById(R.id.dt_nombre_ejercicio);
        tv.setText(ejercicio.getNombre());

        return convertView;
    }
}

