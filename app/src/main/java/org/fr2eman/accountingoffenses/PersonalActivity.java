package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.dialog.LogoutAction;
import org.fr2eman.accountingoffenses.dialog.LogoutDialog;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.DataService;
import org.fr2eman.accountingoffenses.model.EmployeeModel;
import org.fr2eman.accountingoffenses.network.ServerFacade;

@EActivity(R.layout.activity_personal)
public class PersonalActivity extends MenuActivity {

    private static String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById (R.id.personal_second_name_view)
    TextView secondNameView;

    @ViewById (R.id.personal_first_name_view)
    TextView firstNameView;

    @ViewById (R.id.personal_middle_name_view)
    TextView middleNameView;

    @ViewById (R.id.personal_position_view)
    TextView positionView;

    @ViewById (R.id.personal_rank_view)
    TextView rankView;

    @ViewById (R.id.personal_work_view)
    TextView workView;

    @Bean
    CacheService cache;

    @AfterViews
    void init() {
        Log.i("fr2eman", "AfterView");
        initMenu();

        EmployeeModel employee = data.getCurrentEmployee();
        secondNameView.setText(employee.getSecondName());
        firstNameView.setText(employee.getFirstName());
        middleNameView.setText(employee.getMiddleName());
        positionView.setText(employee.getPosition());
        rankView.setText(employee.getRank());
        workView.setText(employee.getPlaceOfWork());

        if(cache.getCurrentMode() == CacheService.Mode.MODE_AUTHORIZATION) {
            progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                    "Загрузка истории протоколов", true);
            loadHistoryProtocols();
        }
        cache.setCurrentMode(CacheService.Mode.NONE);
    }

    @Background
    void loadHistoryProtocols() {
        boolean status = webService.requestHistoryOffenses();
        if (status) {
            successLoadHistory();
        } else {
            failedLoadHistory();
        }
    }

    @UiThread
    void successLoadHistory() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, "История протоколов загружена", Toast.LENGTH_LONG);
        toast.show();
    }

    @UiThread
    void failedLoadHistory() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, cache.getErrorMessage(), Toast.LENGTH_LONG);
        toast.show();
    }

    @Click({R.id.menu_button})
    void slidingMenuButtonClick() {
        menu.toggle();
    }

    @Click({R.id.personal_logout_button})
    void clickLogout() {
        LogoutDialog dialog = new LogoutDialog();
        dialog.show(getFragmentManager(), TAG);
    }

}
