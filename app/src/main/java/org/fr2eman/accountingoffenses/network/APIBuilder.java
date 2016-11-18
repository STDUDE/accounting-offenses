package org.fr2eman.accountingoffenses.network;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.fr2eman.accountingoffenses.model.ArticleModel;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.DataService;
import org.fr2eman.accountingoffenses.model.OffenseModel;
import org.fr2eman.accountingoffenses.model.VictimModel;
import org.fr2eman.accountingoffenses.model.ViolatorModel;
import org.fr2eman.accountingoffenses.model.WitnessModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 13.04.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class APIBuilder {

    private static final String PRIVATE_EMPLOYEE = "number_employee";
    private static final String PASSWORD = "password";
    private static final String AUTH_TOKEN = "authtoken";
    private static final String NUMBER_ARTICLE = "number_article";
    private static final String CODEX = "codex";
    private static final String S_NAME_OFFENDER = "offender_sname";
    private static final String F_NAME_OFFENDER = "offender_fname";
    private static final String M_NAME_OFFENDER = "offender_mname";
    private static final String BIRTHDAY_OFFENDER = "offender_brithday";
    private static final String ADDRESS_OFFENDER = "offender_address";
    private static final String CITIZENSHIP_OFFENDER = "offender_citizen";
    private static final String WORK_OFFENDER = "offender_work";
    private static final String POSITION_OFFENDER = "offender_pos";
    private static final String PHONE_OFFENDER = "offender_phone";
    private static final String NUMBER_OFFENSE = "protocol_number";
    private static final String PLACE_OFFENSE = "offense_place";
    private static final String DATE_OFFENSE = "offense_date";
    private static final String DESCRIPTION = "description";
    private static final String WITNESS_FIRST_EXIST = "witness_first_exist";
    private static final String WITNESS_FIRST_S_NAME = "witness_first_sname";
    private static final String WITNESS_FIRST_F_NAME = "witness_first_fname";
    private static final String WITNESS_FIRST_M_NAME = "witness_first_mname";
    private static final String WITNESS_FIRST_ADDRESS = "witness_first_address";
    private static final String WITNESS_FIRST_PHONE = "witness_first_phone";
    private static final String WITNESS_SECOND_EXIST = "witness_second_exist";
    private static final String WITNESS_SECOND_S_NAME = "witness_second_sname";
    private static final String WITNESS_SECOND_F_NAME = "witness_second_fname";
    private static final String WITNESS_SECOND_M_NAME = "witness_second_mname";
    private static final String WITNESS_SECOND_ADDRESS = "witness_second_address";
    private static final String WITNESS_SECOND_PHONE = "witness_second_phone";
    private static final String VICTIM_EXIST = "victim_exist";
    private static final String VICTIM_S_NAME = "victim_sname";
    private static final String VICTIM_F_NAME = "victim_fname";
    private static final String VICTIM_M_NAME = "victim_mname";
    private static final String VICTIM_ADDRESS = "victim_address";
    private static final String VICTIM_PHONE = "victim_phone";
    private static final String FILTER_TYPE = "filter_type";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String SECOND_NAME = "second_name";
    private static final String FIRST_NAME = "first_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String ID = "id";
    private static final String SEARCH = "search";

    @Bean
    DataService data;

    @Bean
    CacheService cache;

    public String buildURL(String url, HashMap<String, String> params) {
        String urlAddress = url + "?";
        String tempValue = "";
        for (Map.Entry entry : params.entrySet()) {
            tempValue = ((String)entry.getValue()).replaceAll(" ", "%20");
            urlAddress += entry.getKey() + "=" + tempValue + "&";
        }
        if(!params.isEmpty()) urlAddress = urlAddress.substring(0, urlAddress.length() - 1);
        return urlAddress;
    }

    public HashMap<String, String> getAuthorizationParams(String username, String password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(PRIVATE_EMPLOYEE, username);
        params.put(PASSWORD, password);
        return params;
    }

    public HashMap<String, String> getLogoutParams(String username, String authtoken) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, authtoken);
        params.put(PRIVATE_EMPLOYEE, username);
        return params;
    }

    public HashMap<String, String> getCheckOffenseParams(String authtoken, String article, int codex) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, authtoken);
        params.put(NUMBER_ARTICLE, article);
        params.put(CODEX, Integer.toString(codex));
        return params;
    }

    public HashMap<String, String> getAddOffenseParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, data.getAuthtoken());

        OffenseModel offense = cache.getCurrentOffense();

        ViolatorModel offender = offense.getOffender();
        params.put(S_NAME_OFFENDER, offender.getSecondName());
        params.put(F_NAME_OFFENDER, offender.getFirstName());
        params.put(M_NAME_OFFENDER, offender.getMiddleName());
        params.put(ADDRESS_OFFENDER, offender.getAddress());
        params.put(BIRTHDAY_OFFENDER, new SimpleDateFormat("yyyy-MM-dd").format(offender.getDateOfBrith()));
        params.put(CITIZENSHIP_OFFENDER, offender.getCitizenship());
        if(offender.getWork() != null && !offender.getWork().isEmpty()) {
            params.put(WORK_OFFENDER, offender.getWork());
        }
        if(offender.getPosition() != null && !offender.getPosition().isEmpty()) {
            params.put(POSITION_OFFENDER, offender.getPosition());
        }
        if(offender.getPhone() != null && !offender.getPhone().isEmpty()) {
            params.put(PHONE_OFFENDER, offender.getPhone());
        }
        params.put(NUMBER_OFFENSE, offense.getNumberProtocol());
        params.put(PLACE_OFFENSE, offense.getPlace());
        params.put(DATE_OFFENSE, new SimpleDateFormat("yyyy-MM-dd").format(offense.getDateOffense()));
        params.put(DESCRIPTION, offense.getDescription());

        ArticleModel article = offense.getArticle();
        params.put(NUMBER_ARTICLE, article.getNumberArticle());
        params.put(CODEX, Integer.toString(ArticleModel.getIDCodexByName(article.getCodexType())));

        WitnessModel firstWitness = offense.getFirstWitness();
        if(firstWitness == null) {
            params.put(WITNESS_FIRST_EXIST, Integer.toString(WitnessModel.WITNESS_NOT_EXIST));
        } else {
            params.put(WITNESS_FIRST_EXIST, Integer.toString(WitnessModel.WITNESS_EXIST));
            params.put(WITNESS_FIRST_S_NAME, firstWitness.getSecondName());
            params.put(WITNESS_FIRST_F_NAME, firstWitness.getFirstName());
            params.put(WITNESS_FIRST_M_NAME, firstWitness.getMiddleName());
            params.put(WITNESS_FIRST_ADDRESS, firstWitness.getAddress());
            if(firstWitness.getPhone() != null && !firstWitness.getPhone().isEmpty()) {
                params.put(WITNESS_FIRST_PHONE, firstWitness.getPhone());
            }
        }

        WitnessModel secondWitness = offense.getSecondWitness();
        if(secondWitness == null) {
            params.put(WITNESS_SECOND_EXIST, Integer.toString(WitnessModel.WITNESS_NOT_EXIST));
        } else {
            params.put(WITNESS_SECOND_EXIST, Integer.toString(WitnessModel.WITNESS_EXIST));
            params.put(WITNESS_SECOND_S_NAME, secondWitness.getSecondName());
            params.put(WITNESS_SECOND_F_NAME, secondWitness.getFirstName());
            params.put(WITNESS_SECOND_M_NAME, secondWitness.getMiddleName());
            params.put(WITNESS_SECOND_ADDRESS, secondWitness.getAddress());
            if(secondWitness.getPhone() != null && !secondWitness.getPhone().isEmpty()) {
                params.put(WITNESS_SECOND_PHONE, secondWitness.getPhone());
            }
        }

        VictimModel victim = offense.getVictim();
        if(victim == null) {
            params.put(VICTIM_EXIST, Integer.toString(VictimModel.VICTIM_NOT_EXIST));
        } else {
            params.put(VICTIM_EXIST, Integer.toString(VictimModel.VICTIM_EXIST));
            params.put(VICTIM_S_NAME, victim.getSecondName());
            params.put(VICTIM_F_NAME, victim.getFirstName());
            params.put(VICTIM_M_NAME, victim.getMiddleName());
            params.put(VICTIM_ADDRESS, victim.getAddress());
            if(victim.getPhone() != null && !victim.getPhone().isEmpty()) {
                params.put(VICTIM_PHONE, victim.getPhone());
            }
        }

        return params;
    }

    public HashMap<String, String> getHistoryOffensesParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, data.getAuthtoken());
        return params;
    }

    public HashMap<String, String> getGetOffenseParams(String protocolNumber) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, data.getAuthtoken());
        params.put(NUMBER_OFFENSE, protocolNumber);
        return params;
    }

    public HashMap<String, String> getFilterOffensesParams(boolean isAll, Date begin, Date end) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, data.getAuthtoken());
        if (isAll) {
            params.put(FILTER_TYPE, "all");
        } else {
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            params.put(FROM, formatDate.format(begin));
            params.put(TO, formatDate.format(end));
        }
        return params;
    }

    public HashMap<String, String> getSearchOffendersParams(String s_name, String f_name, String m_name) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, data.getAuthtoken());
        params.put(SECOND_NAME, s_name);
        params.put(FIRST_NAME, f_name);
        params.put(MIDDLE_NAME, m_name);
        return params;
    }

    public HashMap<String, String> getGetOffenderParams(int id_offender) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, data.getAuthtoken());
        params.put(ID, Integer.toString(id_offender));
        return params;
    }

    public HashMap<String, String> getSearchArticlesParams(String number_article, String description) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, data.getAuthtoken());
        params.put(NUMBER_ARTICLE, number_article);
        params.put(SEARCH, description);
        return params;
    }

    public HashMap<String, String> getGetArticleParams(int id_article) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AUTH_TOKEN, data.getAuthtoken());
        params.put(ID, Integer.toString(id_article));
        return params;
    }

}
