package org.fr2eman.accountingoffenses.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.fr2eman.accountingoffenses.R;

/**
 * Created by Asus on 17.04.2016.
 */
public class ConfirmDialog extends android.app.DialogFragment {

    private static String TAG = "fr2eman";

    private String title;
    private String message;
    private Object context;

    public ConfirmDialog() {
        this.title = "Предупреждение";
        this.title = "Вы уверены, что хотите выполнить следующую операцию?";
        this.context = null;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setContext(Object context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_warning)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "click ok!");
                        ((ConfirmAction)getDialog().getOwnerActivity()).onConfirm(context);
                    }
                })
                .setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        Log.i(TAG, "click cancel!");
                        ((ConfirmAction)getDialog().getOwnerActivity()).onNotConfirm();
                    }
                }).create();
    }

}
