package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Click;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.network.ServerFacade;

@EActivity(R.layout.activity_search_violator)
public class SearchViolatorActivity extends MenuActivity {

    private static final String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById(R.id.search_violator_s_name_edit)
    EditText secondNameEdit;

    @ViewById(R.id.search_violator_f_name_edit)
    EditText firstNameEdit;

    @ViewById(R.id.search_violator_m_name_edit)
    EditText middleNameEdit;

    @Bean
    ServerFacade webService;

    @Bean
    CacheService cache;

    @AfterViews
    public void init() {
        initMenu();
    }

    @Click({R.id.menu_button})
    void slidingMenuButtonClick() {
        menu.toggle();
    }

    @Click(R.id.search_piple_button)
    public void search() {
        if(secondNameEdit.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, "Введите фамилию", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                "Поиск в базе", true);
        searchOffenders();
    }

    @Background
    void searchOffenders() {
        boolean status = webService.requestSearchOffenders(
                secondNameEdit.getText().toString(),
                firstNameEdit.getText().toString(),
                middleNameEdit.getText().toString());
        if(status) {
            success();
        } else {
            failed();
        }
    }

    @UiThread
    void success() {
        progressDialog.hide();
        Intent intent = new Intent(this, OffenderListActivity_.class);
        startActivity(intent);
    }

    @UiThread
    void failed() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, cache.getErrorMessage(), Toast.LENGTH_LONG);
        toast.show();
    }

}
