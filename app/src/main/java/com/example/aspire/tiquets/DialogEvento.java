package com.example.aspire.tiquets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Aspire on 13/06/2017.
 */

public class DialogEvento extends DialogFragment {

    View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());
        view = getActivity().
                getLayoutInflater().
                inflate(R.layout.dialog_evento,null) ;
        //crear el dialogo
        builder.setView(view).
                setTitle("Información del evento").
                setPositiveButton("Adquirir Entradas", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO adquirirEntradas()

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        //return super.onCreateDialog(savedInstanceState);
        return builder.create();
    }

}
