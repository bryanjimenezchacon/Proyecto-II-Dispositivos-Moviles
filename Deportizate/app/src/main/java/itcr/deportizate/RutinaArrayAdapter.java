package itcr.deportizate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by gesab on 6/10/2016.
 */
public class RutinaArrayAdapter extends ArrayAdapter<String> {

        private LayoutInflater inflater;
        private String[] names;
        private int resource;

        public RutinaArrayAdapter(Context context, int resource, String[] nombres) {
            super(context, resource, nombres);
            this.names = nombres;
            this.resource = resource;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(resource, parent, false);
            }

            TextView tv = (TextView) convertView.findViewById(R.id.dt_nombre_ejercicio);
            tv.setText(names[position]);

            return convertView;
        }
    }

