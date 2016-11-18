package org.fr2eman.accountingoffenses.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.fr2eman.accountingoffenses.R;

/**
 * Created by Asus on 15.04.2016.
 */

public class LogoutDialog extends android.app.DialogFragment {

    private static String TAG = "fr2eman";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_logout)
                .setTitle("Выход из аккаунта")
                .setMessage("Вы действительно хотите выйти из аккаунта?")
                .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "click ok!");
                        ((LogoutAction)getDialog().getOwnerActivity()).onLogout();
                    }
                })

                        // Negative Button
                .setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        Log.i(TAG, "click cancel!");
                    }
                }).create();
    }
}
