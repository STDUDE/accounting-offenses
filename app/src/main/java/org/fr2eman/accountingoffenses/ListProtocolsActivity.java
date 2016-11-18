package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.adapter.ProtocolsListAdapter;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.OffenseModel;
import org.fr2eman.accountingoffenses.network.ServerFacade;

import java.util.List;

@EActivity(R.layout.activity_list_protocols)
public class ListProtocolsActivity extends Activity {

    private static final String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById(R.id.list_view_protocols)
    ListView protocolsList;

    @Bean
    ProtocolsListAdapter protocolsListAdapter;

    @Bean
    ServerFacade webService;

    @Bean
    CacheService cache;

    private String selected_protocol;

    @AfterViews
    void init() {
        List<OffenseModel> list = cache.getListOffenses();
        protocolsListAdapter.setItems(list);
        protocolsList.setAdapter(protocolsListAdapter);
    }

    @Click(R.id.back_button)
    void back() {
        finish();
    }

    @ItemClick(R.id.list_view_protocols)
    void clickOffenseItem(OffenseModel model) {
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                "Загрузка данных об административном правонарушении", true);
        selected_protocol = model.getNumberProtocol();
        Log.i(TAG, "click protocol = " + selected_protocol);
        loadOffenseData();
    }

    @Background
    void loadOffenseData() {
        boolean status = webService.requestgetOffense(selected_protocol);
        if(status) {
            success();
        } else {
            failed();
        }
    }

    @UiThread
    void success() {
        progressDialog.hide();
        Intent intent = new Intent(this, BlankProtocolActivity_.class);
        intent.putExtra("CREATING_PROTOCOL", false);
        startActivity(intent);
    }

    @UiThread
    void failed() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, cache.getErrorMessage(), Toast.LENGTH_LONG);
        toast.show();
    }

}
