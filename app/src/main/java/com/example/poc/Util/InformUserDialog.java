package com.example.poc.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;

import com.example.poc.R;

public class InformUserDialog extends ContextWrapper {

    public InformUserDialog(Context base) {
        super(base);
    }

    public void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getBaseContext());
        alertDialogBuilder.setTitle(getString(R.string.dialog_title));
        alertDialogBuilder
                .setMessage(getString(R.string.dialog_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
