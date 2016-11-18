package org.fr2eman.accountingoffenses;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
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
import org.fr2eman.accountingoffenses.adapter.ProtocolsListAdapter;
import org.fr2eman.accountingoffenses.dialog.DateDialog;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.DataService;
import org.fr2eman.accountingoffenses.model.OffenseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_history)
public class HistoryActivity extends MenuActivity {

    private static final String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @Bean
    ProtocolsListAdapter protocolsListAdapter;

    @ViewById(R.id.title_menu_actionbar_text_view)
    TextView textViewTitle;

    @ViewById(R.id.list_view_history_protocols)
    ListView protocolsList;

    @Bean
    CacheService cache;

    private String selected_protocol;

    @Click({R.id.menu_button})
    void slidingMenuButtonClick() {
        menu.toggle();
    }

    @AfterViews
    void init() {
        textViewTitle.setText("История протоколов");
        initMenu();

        List<OffenseModel> list = data.getHistoryOffenses();
        for(OffenseModel of : list) {
            Log.i(TAG, "offense id = " + of.getId() + ", num_offense = " + of.getNumberProtocol());
        }
        protocolsListAdapter.setItems(list);
        protocolsList.setAdapter(protocolsListAdapter);
        if(cache.getCurrentMode() == CacheService.Mode.MODE_ADDED_OFFENSE) {
            Intent intent = new Intent(this, BlankProtocolActivity_.class);
            intent.putExtra("CREATING_PROTOCOL", false);
            startActivity(intent);
        }
    }

    @Click(R.id.filter_button)
    void clickFilter() {
        Intent intent = new Intent(this, ProtocolsFilterActivity_.class);
        startActivity(intent);
    }
    @Override
    protected void clickHistoryProtocols() {
        Log.i(TAG, "user clicked on private office item(just close menu)");
        menu.toggle();
    }

    @ItemClick(R.id.list_view_history_protocols)
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
