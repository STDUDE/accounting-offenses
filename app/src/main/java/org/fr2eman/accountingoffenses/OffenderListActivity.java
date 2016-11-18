package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.fr2eman.accountingoffenses.adapter.OffenderListAdapter;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.ViolatorModel;
import org.fr2eman.accountingoffenses.network.ServerFacade;

import java.util.List;

@EActivity(R.layout.activity_offender_list)
public class OffenderListActivity extends Activity {

    private static final String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById(R.id.list_view_offenders)
    ListView listOffender;

    @Bean
    OffenderListAdapter offenderListAdapter;

    @Bean
    ServerFacade webService;

    @Bean
    CacheService cache;

    private int id_offender;

    @AfterViews
    void init() {
        List<ViolatorModel> list = cache.getListOffenders();
        offenderListAdapter.setItems(list);
        listOffender.setAdapter(offenderListAdapter);
    }

    @Click(R.id.back_button)
    void back() {
        finish();
    }

    @ItemClick(R.id.list_view_offenders)
    void clickOffenseItem(ViolatorModel model) {
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                "Загрузка данных о правонарушителе", true);
        id_offender = model.getId();
        Log.i(TAG, "click offender_id = " + id_offender);
        loadOffenseData();
    }

    @Background
    void loadOffenseData() {
        boolean status = webService.requestGetOffender(id_offender);
        if(status) {
            success();
        } else {
            failed();
        }
    }

    @UiThread
    void success() {
        progressDialog.hide();
        Intent intent = new Intent(this, OffenderDetailsActivity_.class);
        startActivity(intent);
    }

    @UiThread
    void failed() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, cache.getErrorMessage(), Toast.LENGTH_LONG);
        toast.show();
    }

}
