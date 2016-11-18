package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.ViolatorModel;

import java.text.SimpleDateFormat;

@EActivity(R.layout.activity_offender_details)
public class OffenderDetailsActivity extends Activity {

    private static String TAG = "fr2eman";

    @ViewById(R.id.fio_offender_details)
    TextView fioOffenderView;

    @ViewById (R.id.birth_day_offender_details)
    TextView birthDayOffenderView;

    @ViewById (R.id.address_offender_details)
    TextView addressOffenderView;

    @ViewById (R.id.phone_offender_details)
    TextView phoneOffenderView;

    @ViewById (R.id.work_offender_details)
    TextView workOffenderView;

    @ViewById (R.id.position_offender_details)
    TextView positionOffenderView;

    @Bean
    CacheService cache;

    @AfterViews
    void init() {
        Log.i("fr2eman", "open offender details");

        ViolatorModel offenderDetails = cache.getOffenderDetails();

        fioOffenderView.setText(offenderDetails.getSecondName() + " "
                + offenderDetails.getFirstName() + " "
                + offenderDetails.getMiddleName());
        birthDayOffenderView.setText(new SimpleDateFormat("dd/MM/yyyy").
                format(offenderDetails.getDateOfBrith()) + " года рождения");
        addressOffenderView.setText(offenderDetails.getAddress());
        phoneOffenderView.setText("Телефон: " + offenderDetails.getPhone());
        workOffenderView.setText("Место работы: " + offenderDetails.getWork());
        positionOffenderView.setText("Должность: " + offenderDetails.getPosition());
    }

    @Click(R.id.back_button)
    void back() {
        finish();
    }

}
