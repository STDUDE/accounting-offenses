package org.fr2eman.accountingoffenses;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.dialog.ConfirmAction;
import org.fr2eman.accountingoffenses.dialog.ConfirmDialog;
import org.fr2eman.accountingoffenses.dialog.DateDialog;
import org.fr2eman.accountingoffenses.model.ArticleModel;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.DataService;
import org.fr2eman.accountingoffenses.model.OffenseModel;
import org.fr2eman.accountingoffenses.model.VictimModel;
import org.fr2eman.accountingoffenses.model.ViolatorModel;
import org.fr2eman.accountingoffenses.model.WitnessModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@EActivity(R.layout.activity_edit_protocol)
public class EditProtocolActivity extends MenuActivity implements ConfirmAction {

    private static String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById(R.id.title_menu_actionbar_text_view)
    TextView textViewTitle;

    @ViewById(R.id.tabHost)
    TabHost tabHost;

    @ViewById(R.id.s_name_offender_edit)
    EditText secondNameOffenderEdit;

    @ViewById(R.id.f_name_offender_edit)
    EditText firstNameOffenderEdit;

    @ViewById(R.id.m_name_offender_edit)
    EditText middleNameOffenderEdit;

    @ViewById(R.id.brith_day_offender_edit)
    EditText brithDayOffenderEdit;

    @ViewById(R.id.address_offender_edit)
    EditText addressOffenderEdit;

    @ViewById(R.id.citizenship_offender_edit)
    EditText citizenshipOffenderEdit;

    @ViewById(R.id.work_offender_edit)
    EditText workOffenderEdit;

    @ViewById(R.id.position_offender_edit)
    EditText positionOffenderEdit;

    @ViewById(R.id.phone_offender_edit)
    EditText phoneOffenderEdit;

    @ViewById(R.id.article_offence_edit)
    EditText articleOffenceEdit;

    @ViewById(R.id.place_offence_edit)
    EditText addressOffenceEdit;

    @ViewById(R.id.description_offence_edit)
    EditText descriptionOffenceEdit;

    @ViewById(R.id.checkFirstWitness)
    CheckBox checkFirstWitness;

    @ViewById(R.id.checkSecondWitness)
    CheckBox checkSecondWitness;

    @ViewById(R.id.firstWitnessPanel)
    LinearLayout firstWitnessPanel;

    @ViewById(R.id.secondWitnessPanel)
    LinearLayout secondWitnessPanel;

    @ViewById(R.id.s_name_first_witness_edit)
    EditText sNameFirstWitnessEdit;

    @ViewById(R.id.f_name_first_witness_edit)
    EditText fNameFirstWitnessEdit;

    @ViewById(R.id.m_name_first_witness_edit)
    EditText mNameFirstWitnessEdit;

    @ViewById(R.id.address_first_witness_edit)
    EditText addressFirstWitnessEdit;

    @ViewById(R.id.phone_first_witness_edit)
    EditText phoneFirstWitnessEdit;

    @ViewById(R.id.s_name_second_witness_edit)
    EditText sNameSecondWitnessEdit;

    @ViewById(R.id.f_name_second_witness_edit)
    EditText fNameSecondWitnessEdit;

    @ViewById(R.id.m_name_second_witness_edit)
    EditText mNameSecondWitnessEdit;

    @ViewById(R.id.address_second_witness_edit)
    EditText addressSecondWitnessEdit;

    @ViewById(R.id.phone_second_witness_edit)
    EditText phoneSecondWitnessEdit;

    @ViewById(R.id.checkVictim)
    CheckBox checkVictim;

    @ViewById(R.id.victimPanel)
    LinearLayout victimPanel;

    @ViewById(R.id.s_name_victim_edit)
    EditText sNameVictimEdit;

    @ViewById(R.id.f_name_victim_edit)
    EditText fNameVictimEdit;

    @ViewById(R.id.m_name_victim_edit)
    EditText mNameVictimEdit;

    @ViewById(R.id.address_victim_edit)
    EditText addressVictimEdit;

    @ViewById(R.id.phone_victim_edit)
    EditText phoneVictimEdit;

    @Bean
    CacheService cache;

    private String reason;
    private String tabWarning;

    private String numberArticle;
    private int codex;

    @AfterViews
    public void init() {
        textViewTitle.setText("Шаблон");
        initMenu();

        tabHost.setup();

        TabHost.TabSpec offenderTab = tabHost.newTabSpec("tagOffenderData");
        TabHost.TabSpec descriptionTab = tabHost.newTabSpec("tagDescription");
        TabHost.TabSpec victimsTab = tabHost.newTabSpec("tagVictims");
        TabHost.TabSpec witnessesTab = tabHost.newTabSpec("tagWitnesses");

        offenderTab.setIndicator("Данные нарушителя");
        offenderTab.setContent(R.id.dataOffenderPage);
        tabHost.addTab(offenderTab);

        descriptionTab.setIndicator("Описание");
        descriptionTab.setContent(R.id.descriptionPage);
        tabHost.addTab(descriptionTab);

        victimsTab.setIndicator("Свидетели");
        victimsTab.setContent(R.id.witnessesPage);
        tabHost.addTab(victimsTab);

        witnessesTab.setIndicator("Потерпевшие");
        witnessesTab.setContent(R.id.victimsPage);
        tabHost.addTab(witnessesTab);

        tabHost.setCurrentTabByTag("tagOffenderData");
    }

    @Click(R.id.next_button)
    void clickConfirmProtocol() {
        Log.i(TAG, "click confirm protocol");
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                getString(R.string.preparing_protocol_progress), true);
        reason = "";
        checkDatasProtocol();
        if(!reason.isEmpty()) {
            Log.i(TAG, "reason = " + reason + "; tag = " + tabWarning);
            progressDialog.hide();
            tabHost.setCurrentTabByTag(tabWarning);
            Toast toast = Toast.makeText(this, reason,
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        saveProtocol();
        sendProtocol();
    }

    @Background
    void checkDatasProtocol() {
        reason = "";
        tabWarning = "tagOffenderData";
        if(secondNameOffenderEdit.getText().toString().isEmpty()) {
            reason = getString(R.string.s_name_offender_toast);
        } else if(firstNameOffenderEdit.getText().toString().isEmpty()) {
            reason = getString(R.string.f_name_offender_toast);
        } else if(middleNameOffenderEdit.getText().toString().isEmpty()) {
            reason = getString(R.string.m_name_offender_toast);
        } else if(brithDayOffenderEdit.getText().toString().isEmpty()) {
            reason = getString(R.string.brithday_offender_toast);
        } else if(addressOffenderEdit.getText().toString().isEmpty()) {
            reason = getString(R.string.address_offender_toast);
        } else if(citizenshipOffenderEdit.getText().toString().isEmpty()) {
            reason = getString(R.string.citizenship_offender_toast);
        } else if(articleOffenceEdit.getText().toString().isEmpty()) {
            reason = getString(R.string.article_offence_toast);
            tabWarning = "tagDescription";
        } else if(addressOffenceEdit.getText().toString().isEmpty()) {
            reason = getString(R.string.address_offence_toast);
            tabWarning = "tagDescription";
        } else if(descriptionOffenceEdit.getText().toString().isEmpty()) {
            reason = getString(R.string.description_offence_toast);
            tabWarning = "tagDescription";
        } else if(!checkFirstWitness.isChecked()) {
            if(sNameFirstWitnessEdit.getText().toString().isEmpty() ||
                    fNameFirstWitnessEdit.getText().toString().isEmpty() ||
                    mNameFirstWitnessEdit.getText().toString().isEmpty() ||
                    addressFirstWitnessEdit.getText().toString().isEmpty()) {
                reason = getString(R.string.datas_witness_toast);
                tabWarning = "tagVictims";
            }
        } else if(!checkSecondWitness.isChecked()) {
            if(sNameSecondWitnessEdit.getText().toString().isEmpty() ||
                    fNameSecondWitnessEdit.getText().toString().isEmpty() ||
                    mNameSecondWitnessEdit.getText().toString().isEmpty() ||
                    addressSecondWitnessEdit.getText().toString().isEmpty()) {
                reason = getString(R.string.datas_witness_toast);
                tabWarning = "tagVictims";
            }
        } else if(!checkVictim.isChecked()) {
            if(sNameVictimEdit.getText().toString().isEmpty() ||
                    fNameVictimEdit.getText().toString().isEmpty() ||
                    mNameVictimEdit.getText().toString().isEmpty() ||
                    addressVictimEdit.getText().toString().isEmpty()) {
                reason = getString(R.string.datas_victim_toast);
                tabWarning = "tagWitnesses";
            }
        }
        if(reason.isEmpty()) {
            String article = articleOffenceEdit.getText().toString();
            Log.i(TAG, "input article: " + article);
            String array[] = article.split(" ");
            if(array.length == 2) {
                String articleNumber = array[0];
                Log.i(TAG, "number_article: " + articleNumber);
                String articleCodex = array[1];
                Log.i(TAG, "article_codex: " + articleCodex);
                this.numberArticle = articleNumber;
                this.codex = ArticleModel.getIDCodexByName(articleCodex);
                Log.i(TAG, "article_codex_id: " + this.codex);
                if(this.codex == 0) {
                    reason = "Не правильно указан административно правовой акт";
                    tabWarning = "tagDescription";
                }
            } else {
                reason = "Не правильно заполнена нарушенная статья";
                tabWarning = "tagDescription";
            }
        }
    }

    @Background
    void saveProtocol() {
        OffenseModel offense = new OffenseModel();
        Date brithDate = null;
        try {
            brithDate = new SimpleDateFormat("dd/MM/yyyy").parse(
                    brithDayOffenderEdit.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        offense.setOffender(new ViolatorModel(secondNameOffenderEdit.getText().toString(),
                firstNameOffenderEdit.getText().toString(),
                middleNameOffenderEdit.getText().toString(), brithDate,
                addressOffenderEdit.getText().toString(),
                citizenshipOffenderEdit.getText().toString(),
                workOffenderEdit.getText().toString(),
                positionOffenderEdit.getText().toString(),
                phoneOffenderEdit.getText().toString()));
        offense.setEmployeeMIA(data.getCurrentEmployee());
        offense.setPlace(addressOffenceEdit.getText().toString());
        offense.setDescription(descriptionOffenceEdit.getText().toString());
        offense.setArticle(new ArticleModel(numberArticle,
                codex));
        if(!checkFirstWitness.isChecked()) {
            offense.setFirstWitness(new WitnessModel(sNameFirstWitnessEdit.getText()
                    .toString(), fNameFirstWitnessEdit.getText().toString(),
                    mNameFirstWitnessEdit.getText().toString(),
                    addressFirstWitnessEdit.getText().toString(),
                    phoneFirstWitnessEdit.getText().toString()));
        } else {
            offense.setFirstWitness(null);
        }
        if(!checkSecondWitness.isChecked()) {
            offense.setSecondWitness(new WitnessModel(sNameSecondWitnessEdit.getText()
                    .toString(), fNameSecondWitnessEdit.getText().toString(),
                    mNameSecondWitnessEdit.getText().toString(),
                    addressSecondWitnessEdit.getText().toString(),
                    phoneSecondWitnessEdit.getText().toString()));
        } else {
            offense.setSecondWitness(null);
        }
        if(!checkVictim.isChecked()) {
            offense.setVictim(new VictimModel(sNameVictimEdit.getText()
                    .toString(), fNameVictimEdit.getText().toString(),
                    mNameVictimEdit.getText().toString(),
                    addressVictimEdit.getText().toString(),
                    phoneVictimEdit.getText().toString()));
        } else {
            offense.setVictim(null);
        }
        cache.setCurrentOffense(offense);
    }

    @Background
    void sendProtocol() {
        boolean status = webService.requestCheckOffense(numberArticle, codex);
        if(status) {
            successCheckProtocol();
        } else {
            failedCheckProtocol();
        }
    }

    @UiThread
    void successCheckProtocol() {
        progressDialog.hide();
        Intent intent = new Intent(this, BlankProtocolActivity_.class);
        intent.putExtra("CREATING_PROTOCOL", true);
        startActivity(intent);
    }

    @UiThread
    void failedCheckProtocol() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, cache.getErrorMessage(),
                Toast.LENGTH_LONG);
        toast.show();
    }

    @Click({R.id.menu_button})
    void slidingMenuButtonClick() {
        menu.toggle();
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "someware click!");
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setTitle(getString(R.string.confirm_title_dialog));
        dialog.setMessage(getString(R.string.close_edit_protocol_message_dialog));
        dialog.setContext(view);
        dialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void onConfirm(Object context) {
        super.onClick((View) context);
    }

    @Override
    public void onNotConfirm() {
        menu.toggle();
    }

    @Click(R.id.brith_day_offender_edit)
    void selectBrithdayDate() {
        DialogFragment dateDialog = new DateDialog().setEditTextView(brithDayOffenderEdit);
        dateDialog.show(getFragmentManager(), "datePicker");
    }

    @Click(R.id.checkFirstWitness)
    void checkFirstWithess() {
        if(checkFirstWitness.isChecked()) {
            firstWitnessPanel.setVisibility(View.INVISIBLE);
        } else {
            firstWitnessPanel.setVisibility(View.VISIBLE);
        }
    }

    @Click(R.id.checkSecondWitness)
    void checkSecondWithess() {
        if(checkSecondWitness.isChecked()) {
            secondWitnessPanel.setVisibility(View.INVISIBLE);
        } else {
            secondWitnessPanel.setVisibility(View.VISIBLE);
        }
    }

    @Click(R.id.checkVictim)
    void checkVictim() {
        if(checkVictim.isChecked()) {
            victimPanel.setVisibility(View.INVISIBLE);
        } else {
            victimPanel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(cache.getCurrentMode() == CacheService.Mode.MODE_ADDED_OFFENSE) {
            finish();
            Intent intent = new Intent(this, HistoryActivity_.class);
            startActivity(intent);
        }
    }
}
