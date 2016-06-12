package itcr.deportizate.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.view.LayoutInflater;
import android.widget.EditText;

import itcr.deportizate.R;

/**
 * Created by Boga on 09.06.2016.
 */
public class DialogRepeticiones extends DialogFragment {

    public interface ListenerDialog {
        public void onDialogPositiveClick(DialogFragment dialog, String text);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    ListenerDialog listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.registro_repeticiones, null))
                // Add action buttons
                .setTitle("¡Listo! Agrega el número de repeticiones.")
                .setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;
                        EditText et = (EditText)d.findViewById(R.id.repeticiones);
                        String repeticiones = et.getText().toString();
                        listener.onDialogPositiveClick(DialogRepeticiones.this, repeticiones);
                    }
                })
                .setNegativeButton("No registrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(DialogRepeticiones.this);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (ListenerDialog) activity;
        } catch (ClassCastException e) {

        }
    }
}
