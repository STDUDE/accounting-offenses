package org.fr2eman.accountingoffenses.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.fr2eman.accountingoffenses.R;

import java.util.Calendar;

/**
 * Created by Fr2eman on 28.03.2016.
 */
public class DateDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "fr2eman";

    EditText selectedEditText;

    public DateDialog setEditTextView(EditText view) {
        selectedEditText = view;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year = 0, month = 0, day = 0;
        try {
            if (!selectedEditText.getText().toString().equals("")) {
                Log.i(TAG, "set old date on DateDialog");
                String date[] = selectedEditText.getText().toString().split("/");
                Log.i(TAG, "get old Date; date = " +
                        selectedEditText.getText().toString() + " datesize = " + date.length);
                day = Integer.valueOf(date[0]);
                Log.i(TAG, "get old Date; date[0] = " + date[0]);
                month = Integer.valueOf(date[1]);
                Log.i(TAG, "get old Date; date[1] = " + date[1]);
                year = Integer.valueOf(date[2]);
                Log.i(TAG, "get old Date; date[2] = " + date[2]);
            } else throw new Exception();
        } catch(Exception e) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        Dialog picker = new DatePickerDialog(getActivity(), this,
                year, month, day);
        picker.setTitle(getResources().getString(R.string.title_date_dialog));

        return picker;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button setButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_NEGATIVE);

        cancelButton.setText(getResources().getString(R.string.cancel_button_date_dialog));
        setButton.setText(getResources().getString(R.string.set_button_date_dialog));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        ++monthOfYear;
        selectedEditText.setText((dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth) + "/" +
                (monthOfYear > 9 ? monthOfYear : "0" + monthOfYear) + "/" + year);
    }
}
