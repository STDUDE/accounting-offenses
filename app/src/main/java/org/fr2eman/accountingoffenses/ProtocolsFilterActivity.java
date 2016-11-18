package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.dialog.DateDialog;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.network.ServerFacade;

import java.text.SimpleDateFormat;
import java.util.Date;

@EActivity(R.layout.activity_protocols_filter)
public class ProtocolsFilterActivity extends Activity {

    private static final String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById(R.id.title_back_actionbar_text_view)
    TextView textViewTitle;

    @ViewById(R.id.beginDateEdit)
    EditText beginDate;

    @ViewById(R.id.endDateEdit)
    EditText endDate;

    @ViewById(R.id.checkAllTimes)
    CheckBox checkAllTimes;

    @ViewById(R.id.tableDates)
    TableLayout datesLayout;

    @Bean
    ServerFacade webService;

    @Bean
    CacheService cache;

    @AfterViews
    void init() {
        textViewTitle.setText("Поиск протоколов");
    }

    @Click(R.id.beginDateEdit)
    void selectBeginDate() {
        DialogFragment dateDialog = new DateDialog().setEditTextView(beginDate);
        dateDialog.show(getFragmentManager(), "datePicker");
    }

    @Click(R.id.endDateEdit)
    void selectEndDate() {
        DialogFragment dateDialog = new DateDialog().setEditTextView(endDate);
        dateDialog.show(getFragmentManager(), "datePicker");
    }

    @Click(R.id.checkAllTimes)
    void clickCheckAllTimes() {
        if(checkAllTimes.isChecked()) {
            datesLayout.setVisibility(View.INVISIBLE);
        } else {
            datesLayout.setVisibility(View.VISIBLE);
        }
    }

    @Click(R.id.back_button)
    void back() {
        finish();
    }

    @Click(R.id.search_button)
    void searchOffense() {
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                "Поиск административных правонарушений", true);
        if(!checkAllTimes.isChecked()) {
            if(beginDate.getText().toString().isEmpty() || endDate.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(this, "Не заполнены даты для поиска", Toast.LENGTH_LONG);
                progressDialog.hide();
                toast.show();
                return;
            }
        }
        loadListOffenses();
    }

    @Background
    void loadListOffenses() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        boolean status = false;
        Date from = null;
        Date to = null;
        try {
            if(!checkAllTimes.isChecked()) {
                from = formatDate.parse(beginDate.getText().toString());
                to = formatDate.parse(endDate.getText().toString());
            }
        } catch(Exception e) {
            e.printStackTrace();
            cache.setErrorMessage("Ошибка отправления запроса");
            failed();
            return;
        }
        status = webService.requestFilterOffenses(checkAllTimes.isChecked(),
                from, to);
        if(status) {
            success();
        } else {
            failed();
        }
    }

    @UiThread
    void success() {
        progressDialog.hide();
        Intent intent = new Intent(this, ListProtocolsActivity_.class);
        startActivity(intent);
    }

    @UiThread
    void failed() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, cache.getErrorMessage(), Toast.LENGTH_LONG);
        toast.show();
    }

}
