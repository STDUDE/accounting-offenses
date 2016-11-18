package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.format.Formatter;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.model.ArticleModel;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.DataService;
import org.fr2eman.accountingoffenses.model.EmployeeModel;
import org.fr2eman.accountingoffenses.model.OffenseModel;
import org.fr2eman.accountingoffenses.model.VictimModel;
import org.fr2eman.accountingoffenses.model.ViolatorModel;
import org.fr2eman.accountingoffenses.model.WitnessModel;
import org.fr2eman.accountingoffenses.network.ServerFacade;

import java.text.SimpleDateFormat;
import java.util.Date;

@EActivity(R.layout.activity_blank_protocol)
public class BlankProtocolActivity extends Activity {

    private static String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById(R.id.title_back_actionbar_text_view)
    TextView textViewTitle;

    @ViewById(R.id.protocol_blank_number_text)
    TextView protocolNumber;

    @ViewById(R.id.protocol_date_place)
    TextView protocolDate;

    @ViewById(R.id.protocol_employee)
    TextView protocolEmployee;

    @ViewById(R.id.protocol_fio_offender)
    TextView protocolFIOOffender;

    @ViewById(R.id.protocol_brithday_offender)
    TextView protocolBrithdayOffender;

    @ViewById(R.id.protocol_address_offender)
    TextView protocolAddressOffender;

    @ViewById(R.id.protocol_phone_offender)
    TextView protocolPhoneOffender;

    @ViewById(R.id.protocol_work_offender)
    TextView protocolWorkOffender;

    @ViewById(R.id.protocol_position_offender)
    TextView protocolPositionOffender;

    @ViewById(R.id.protocol_citizenship_offender)
    TextView protocolCitizenshipOffender;

    @ViewById(R.id.protocol_description)
    TextView protocolDescription;

    @ViewById(R.id.protocol_article)
    TextView protocolArticle;

    @ViewById(R.id.protocol_victim)
    TextView protocolVictim;

    @ViewById(R.id.protocol_s_name_victim)
    TextView protocolSNameVictim;

    @ViewById(R.id.protocol_f_name_victim)
    TextView protocolFNameVictim;

    @ViewById(R.id.protocol_m_name_victim)
    TextView protocolMNameVictim;

    @ViewById(R.id.protocol_address_victim)
    TextView protocolAddressVictim;

    @ViewById(R.id.protocol_phone_victim)
    TextView protocolPhoneVictim;

    @ViewById(R.id.protocol_witnesses)
    TextView protocolWitnesses;

    @ViewById(R.id.protocol_s_name_first_witness)
    TextView protocolSNameFirstWitness;

    @ViewById(R.id.protocol_f_name_first_witness)
    TextView protocolFNameFirstWitness;

    @ViewById(R.id.protocol_m_name_first_witness)
    TextView protocolMNameFirstWitness;

    @ViewById(R.id.protocol_address_first_witness)
    TextView protocolAddressFirstWitness;

    @ViewById(R.id.protocol_phone_first_witness)
    TextView protocolPhoneFirstWitness;

    @ViewById(R.id.protocol_s_name_second_witness)
    TextView protocolSNameSecondWitness;

    @ViewById(R.id.protocol_f_name_second_witness)
    TextView protocolFNameSecondWitness;

    @ViewById(R.id.protocol_m_name_second_witness)
    TextView protocolMNameSecondWitness;

    @ViewById(R.id.protocol_address_second_witness)
    TextView protocolAddressSecondWitness;

    @ViewById(R.id.protocol_phone_second_witness)
    TextView protocolPhoneSecondWitness;

    @ViewById(R.id.save_button)
    ImageButton saveButton;

    @Bean
    CacheService cache;

    @Bean
    DataService data;

    @Bean
    ServerFacade webService;

    @AfterViews
    public void init() {

        boolean mode;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            mode = false;
        } else {
            mode= extras.getBoolean("CREATING_PROTOCOL");
        }
        if(mode) {
            saveButton.setVisibility(View.VISIBLE);
        } else {
            saveButton.setVisibility(View.INVISIBLE);
        }

        if(cache.getCurrentMode() == CacheService.Mode.MODE_ADDED_OFFENSE) {
            cache.setCurrentMode(CacheService.Mode.NONE);
            Toast toast = Toast.makeText(this, cache.getServerMessage(),
                    Toast.LENGTH_LONG);
            toast.show();
        }

        textViewTitle.setText("Протокол");
        OffenseModel currentOffense = cache.getCurrentOffense();
        Date date = currentOffense.getDateOffense();
        EmployeeModel employee = currentOffense.getEmployeeMIA();
        ViolatorModel offender = currentOffense.getOffender();
        ArticleModel article = currentOffense.getArticle();
        String tempContent = "";
        if(currentOffense.getNumberProtocol() == null
                || currentOffense.getNumberProtocol().isEmpty()) {
            tempContent = getString(R.string.protocol_number_format) + " ___________";
        } else {
            tempContent = getString(R.string.protocol_number_format) + " <u><i> " +
                    currentOffense.getNumberProtocol() + " </i> </u>";
        }
        Spannable content = (Spannable) Html.fromHtml(tempContent);
        protocolNumber.setText(content);

        content = (Spannable) Html.fromHtml("<i><u> '" + new SimpleDateFormat("dd").format(date) +
                "' </u> <u> " + data.getMonthNameByNumber(date.getMonth())
                + " </u> <u> " + new SimpleDateFormat("yyyy").format(date) + " </u></i>г. <i><u> " +
                currentOffense.getPlace() + " </u></i>");
        protocolDate.setText(content);
        Log.i(TAG, "employee: " + employee.toString());
        content = (Spannable) Html.fromHtml("<i><u> " + employee.getPosition() + " " +
                employee.getRank() + " " + employee.getSecondName() + " " +
                employee.getFirstName() + " " + employee.getMiddleName() +
                        " </u></i> руководствуясь статьями 3.30 10.2 ПИКоАП РБ, " +
                        "составил настоящий протокол о том, что <u><i>");
        protocolEmployee.setText(content);

        content = (Spannable) Html.fromHtml("<u><i>" + offender.getSecondName() + " " +
                offender.getFirstName() + " " + offender.getMiddleName() + "</i></u>");
        protocolFIOOffender.setText(content);

        content = (Spannable) Html.fromHtml("год рождения: <u><i> " + new SimpleDateFormat("dd.MM.yyyy").
                format(offender.getDateOfBrith()) + " </i></u>");
        protocolBrithdayOffender.setText(content);

        if(offender.getAddress() == null || offender.getAddress().isEmpty()) {
            tempContent = "место жительства(пребывания): __________________________________";
        } else {
            tempContent = "место жительства(пребывания): <u><i> " +
                    offender.getAddress() + " </i></u>";
        }
        content = (Spannable) Html.fromHtml(tempContent);
        protocolAddressOffender.setText(content);

        if(offender.getPhone() == null || offender.getPhone().isEmpty()) {
            tempContent = "телефон: _________________________";
        } else {
            tempContent = "телефон: <u><i> " + offender.getPhone() + " </i></u>";
        }
        content = (Spannable) Html.fromHtml(tempContent);
        protocolPhoneOffender.setText(content);

        if(offender.getWork() == null || offender.getWork().isEmpty()) {
            tempContent = "место работы(учёбы): _________________________";
        } else {
            tempContent = "место работы(учёбы): <u><i> " +
                    offender.getWork() + " </i></u>";
        }
        content = (Spannable) Html.fromHtml(tempContent);
        protocolWorkOffender.setText(content);

        if(offender.getPosition() == null || offender.getPosition().isEmpty()) {
            tempContent = "должность: _________________________";
        } else {
            tempContent = "должность: <u><i> " +
                    offender.getPosition() + " </i></u>";
        }
        content = (Spannable) Html.fromHtml(tempContent);
        protocolPositionOffender.setText(content);

        if(offender.getCitizenship() == null || offender.getCitizenship().isEmpty()) {
            tempContent = "гражданство: _________________________";
        } else {
            tempContent = "гражданство: <u><i> " +
                    offender.getCitizenship() + " </i></u>";
        }
        content = (Spannable) Html.fromHtml(tempContent);
        protocolCitizenshipOffender.setText(content);

        if(currentOffense.getDescription() == null ||
                currentOffense.getDescription().isEmpty()) {
            tempContent = "совершил(а) правонарушение " +
                    "______________________________________________________________";
        } else {
            tempContent = "совершил(а) правонарушение <u><i> " +
                    currentOffense.getDescription() + " </i></u>";
        }
        content = (Spannable) Html.fromHtml(tempContent);
        protocolDescription.setText(content);

        if(currentOffense.getDescription() == null ||
                currentOffense.getDescription().isEmpty()) {
            tempContent = "ответственность, за которое предусмотрена ______________________";
        } else {
            tempContent = "ответственность, за которое предусмотрена <u><i> " +
                    article.getNumberArticle() + " " + article.getCodexType() + " </i></u>";
        }
        content = (Spannable) Html.fromHtml(tempContent);
        protocolArticle.setText(content);

        VictimModel victim = currentOffense.getVictim();
        if(victim != null) {
            if (victim.getSecondName() == null ||
                    victim.getSecondName().isEmpty()) {
                tempContent = "Фамилия _______________";
            } else {
                tempContent = "Фамилия <u><i> " + victim.getSecondName() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolSNameVictim.setText(content);

            if (victim.getFirstName() == null ||
                    victim.getFirstName().isEmpty()) {
                tempContent = "Имя _______________";
            } else {
                tempContent = "Имя <u><i> " + victim.getFirstName() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolFNameVictim.setText(content);

            if (victim.getMiddleName() == null ||
                    victim.getMiddleName().isEmpty()) {
                tempContent = "Отчество _______________";
            } else {
                tempContent = "Отчество <u><i> " + victim.getMiddleName() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolMNameVictim.setText(content);

            if (victim.getAddress() == null ||
                    victim.getAddress().isEmpty()) {
                tempContent = "адрес места жительства(пребывания) _______________";
            } else {
                tempContent = "адрес места жительства(пребывания) <u><i> " + victim.getAddress() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolAddressVictim.setText(content);

            if (victim.getPhone() == null ||
                    victim.getPhone().isEmpty()) {
                tempContent = "телефон _______________";
            } else {
                tempContent = "телефон <u><i> " + victim.getPhone() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolPhoneVictim.setText(content);
        } else {
            protocolSNameVictim.setText("Фамилия _______________");
            protocolFNameVictim.setText("Имя _______________");
            protocolMNameVictim.setText("Отчество _______________");
            protocolAddressVictim.setText("адрес места жительства(пребывания) _______________");
            protocolPhoneVictim.setText("телефон _______________");
        }

        WitnessModel firstWItness = currentOffense.getFirstWitness();
        if(firstWItness != null) {
            if (firstWItness.getSecondName() == null ||
                    firstWItness.getSecondName().isEmpty()) {
                tempContent = "1.Фамилия _______________";
            } else {
                tempContent = "1.Фамилия <u><i> " + firstWItness.getSecondName() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolSNameFirstWitness.setText(content);

            if (firstWItness.getFirstName() == null ||
                    firstWItness.getFirstName().isEmpty()) {
                tempContent = "Имя _______________";
            } else {
                tempContent = "Имя <u><i> " + firstWItness.getFirstName() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolFNameFirstWitness.setText(content);

            if (firstWItness.getMiddleName() == null ||
                    firstWItness.getMiddleName().isEmpty()) {
                tempContent = "Отчество _______________";
            } else {
                tempContent = "Отчество <u><i> " + firstWItness.getMiddleName() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolMNameFirstWitness.setText(content);

            if (firstWItness.getAddress() == null ||
                    firstWItness.getAddress().isEmpty()) {
                tempContent = "адрес места жительства(пребывания) _______________";
            } else {
                tempContent = "адрес места жительства(пребывания) <u><i> " + firstWItness.getAddress() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolAddressFirstWitness.setText(content);

            if (firstWItness.getPhone() == null ||
                    firstWItness.getPhone().isEmpty()) {
                tempContent = "телефон _______________";
            } else {
                tempContent = "телефон <u><i> " + firstWItness.getPhone() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolPhoneFirstWitness.setText(content);
        } else {
            protocolSNameFirstWitness.setText("1.Фамилия _______________");
            protocolFNameFirstWitness.setText("Имя _______________");
            protocolMNameFirstWitness.setText("Отчество _______________");
            protocolAddressFirstWitness.setText("адрес места жительства(пребывания) _______________");
            protocolPhoneFirstWitness.setText("телефон _______________");
        }
        WitnessModel secondWitness = currentOffense.getSecondWitness();
        if(secondWitness != null) {
            if(secondWitness.getSecondName() == null ||
                    secondWitness.getSecondName().isEmpty()) {
                tempContent = "2.Фамилия _______________";
            } else {
                tempContent = "2.Фамилия <u><i> " + secondWitness.getSecondName() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolSNameSecondWitness.setText(content);

            if(secondWitness.getFirstName() == null ||
                    firstWItness.getFirstName().isEmpty()) {
                tempContent = "Имя _______________";
            } else {
                tempContent = "Имя <u><i> " + secondWitness.getFirstName() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolFNameSecondWitness.setText(content);

            if(secondWitness.getMiddleName() == null ||
                    secondWitness.getMiddleName().isEmpty()) {
                tempContent = "Отчество _______________";
            } else {
                tempContent = "Отчество <u><i> " + secondWitness.getMiddleName() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolMNameSecondWitness.setText(content);

            if(secondWitness.getAddress() == null ||
                    secondWitness.getAddress().isEmpty()) {
                tempContent = "адрес места жительства(пребывания) _______________";
            } else {
                tempContent = "адрес места жительства(пребывания) <u><i> " + secondWitness.getAddress() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolAddressSecondWitness.setText(content);

            if(secondWitness.getPhone() == null ||
                    secondWitness.getPhone().isEmpty()) {
                tempContent = "телефон _______________";
            } else {
                tempContent = "телефон <u><i> " + secondWitness.getPhone() + " </i></u>";
            }
            content = (Spannable) Html.fromHtml(tempContent);
            protocolPhoneSecondWitness.setText(content);
        } else {
            protocolSNameSecondWitness.setText("2.Фамилия _______________");
            protocolFNameSecondWitness.setText("Имя _______________");
            protocolMNameSecondWitness.setText("Отчество _______________");
            protocolAddressSecondWitness.setText("адрес места жительства(пребывания) _______________");
            protocolPhoneSecondWitness.setText("телефон _______________");
        }
    }

    @Click(R.id.back_button)
    void back() {
        finish();
    }

    @Click(R.id.save_button)
    void save() {
        Log.i(TAG, "save protocol");
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                getString(R.string.preparing_protocol_progress), true);
        saveProtocol();
    }

    @Background
    void saveProtocol() {
        boolean status = webService.requestAddOffense();
        if(status) {
            saveSuccess();
        } else {
            saveFailure();
        }
    }

    @UiThread
    void saveSuccess() {
        progressDialog.hide();
        cache.setCurrentMode(CacheService.Mode.MODE_ADDED_OFFENSE);
        OffenseModel offense = cache.getCurrentOffense();
        data.addOffenseIntoHistoryToFront(new OffenseModel(0, offense.getNumberProtocol(),
                offense.getDateOffense(), offense.getPlace(),
                offense.getArticle()));
        finish();
        Log.i(TAG, "server message: " + cache.getServerMessage());
    }

    @UiThread
    void saveFailure() {
        progressDialog.hide();
        Log.i(TAG, "error message: " + cache.getErrorMessage());
        Toast toast = Toast.makeText(this, cache.getErrorMessage(),
                Toast.LENGTH_LONG);
        toast.show();
    }

}
