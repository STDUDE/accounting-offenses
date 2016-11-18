package org.fr2eman.accountingoffenses.network;

import android.text.format.DateFormat;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.fr2eman.accountingoffenses.model.ArticleModel;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.DataService;
import org.fr2eman.accountingoffenses.model.EmployeeModel;
import org.fr2eman.accountingoffenses.model.OffenseModel;
import org.fr2eman.accountingoffenses.model.VictimModel;
import org.fr2eman.accountingoffenses.model.ViolatorModel;
import org.fr2eman.accountingoffenses.model.WitnessModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Asus on 13.04.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ResponseParser {

    private static final String TAG = "fr2eman";

    private static final String STATUS = "STATUS";
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_ERROR = "ERROR";
    private static final String BODY = "BODY";
    private static final String MESSAGE = "MESSAGE";
    private static final String SECOND_NAME = "second_name";
    private static final String FIRST_NAME = "first_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String POSITION = "position";
    private static final String PLACE_OF_WORK = "place_of_work";
    private static final String RANK = "rank";
    private static final String AUTH_TOKEN = "authtoken";
    private static final String PROTOCOL_NUMBER = "protocol_number";
    private static final String DATE = "date";
    private static final String OFFENSES = "offenses";
    private static final String ID = "id";
    private static final String PLACE = "place";
    private static final String CODEX = "codex";
    private static final String ARTICLE = "article";
    private static final String ARTICLE_NUMBER = "article_number";
    private static final String DESCRIPTION = "description";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String EMPLOYEE = "employee";
    private static final String VICTIM = "victim";
    private static final String FIRST_WITNESS = "first_witness";
    private static final String SECOND_WITNESS = "second_witness";
    private static final String BIRTH_DAY = "birth_day";
    private static final String WORK = "work";
    private static final String CITIZENSHIP = "citizenship";
    private static final String OFFENDER = "offender";
    private static final String OFFENDERS = "offenders";
    private static final String ARTICLES = "articles";
    private static final String NUMBER_ARTICLE = "number_article";
    private static final String NAME = "name";
    private static final String CODEX_TYPE = "codex_type";
    private static final String WARNING = "warning";
    private static final String MIN_MULCT = "min_mulct";
    private static final String MAX_MULCT = "max_mulct";

    @Bean
    CacheService cache;

    @Bean
    DataService data;

    public String getString(JSONObject json, String key) {
        try {
            return json.getString(key);
        } catch(JSONException e) {
            Log.e(TAG, "JSONObject error, key = " + key);
            e.printStackTrace();
            return "";
        }
    }

    public int getInteger(JSONObject json, String key) {
        try {
            return json.getInt(key);
        } catch(JSONException e) {
            Log.e(TAG, "JSONObject error, key = " + key);
            e.printStackTrace();
            return 0;
        }
    }

    public boolean getBoolean(JSONObject json, String key) {
        try {
            return json.getBoolean(key);
        } catch(JSONException e) {
            Log.e(TAG, "JSONObject error, key = " + key);
            e.printStackTrace();
            return false;
        }
    }

    public boolean getStatus(JSONObject response) {
        try {
            String status = (String)response.getString(STATUS);
            if(status.contains(STATUS_SUCCESS)) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean parseAuthorizationResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            JSONObject body = null;
            try {
                body = response.getJSONObject(BODY);
            } catch (JSONException e) {
                e.printStackTrace();
                cache.setErrorMessage("Ошибка обработки JSON");
                return false;
            }
            String authtoken = getString(body, AUTH_TOKEN);
            String secondName = getString(body, SECOND_NAME);
            String firstName = getString(body, FIRST_NAME);
            String middleName = getString(body, MIDDLE_NAME);
            String position = getString(body, POSITION);
            String rank = getString(body, RANK);
            String work = getString(body, PLACE_OF_WORK);
            data.setAuthtoken(authtoken);

            EmployeeModel employee = data.getCurrentEmployee();
            employee.setSecondName(secondName);
            employee.setFirstName(firstName);
            employee.setMiddleName(middleName);
            employee.setPosition(position);
            employee.setPrivateNumber(cache.getInputUsername());
            employee.setRank(rank);
            employee.setPlaceOfWork(work);
        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseLogoutResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(!status) {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseCheckOffenseResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            JSONObject body = null;
            try {
                body = response.getJSONObject(BODY);
            } catch (JSONException e) {
                e.printStackTrace();
                cache.setErrorMessage("Ошибка обработки JSON");
                return false;
            }
            String number_protocol = getString(body, PROTOCOL_NUMBER);
            String current_date = getString(body, DATE);

            OffenseModel offense = cache.getCurrentOffense();
            offense.setNumberProtocol(number_protocol);
            try {
                Log.i(TAG, "date: " + current_date);
                offense.setDateOffense(new SimpleDateFormat("dd.MM.yyyy").parse(current_date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cache.setCurrentOffense(offense);

            Log.i(TAG, "offense date = " + new SimpleDateFormat("dd.MM.yyyy").format(offense.getDateOffense())
                    + " ; day = " + offense.getDateOffense().getDay());

        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseAddOffenseResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            String message = getString(response, MESSAGE);
            Log.i(TAG, "server message = " + message);
            cache.setServerMessage(message);
        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseHistoryOffensesResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            JSONObject body = null;
            try {
                body = response.getJSONObject(BODY);
            } catch (JSONException e) {
                String message = null;
                Log.i(TAG, "BODY didn't find");
                try {
                    message = response.getString(MESSAGE);
                    cache.setServerMessage(message);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Log.i(TAG, "Ошибка обработки истории протоколов");
                    cache.setErrorMessage("Ошибка обработки истории протоколов");
                    return false;
                }
                return true;
            }
            Log.i(TAG, "BODY found");
            JSONArray arrayOfffenses = null;
            try {
                arrayOfffenses = body.getJSONArray(OFFENSES);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG, "Ошибка обработки истории протоколов");
                cache.setErrorMessage("Ошибка обработки истории протоколов");
                return false;
            }
            List<OffenseModel> offensesList = new ArrayList<OffenseModel>();
            for(int i = 0; i < arrayOfffenses.length(); i++) {
                Log.i(TAG, "parse 1-th offense");
                try {
                    JSONObject offense = arrayOfffenses.getJSONObject(i);
                    int id = getInteger(offense, ID);
                    String protocolNumber = getString(offense, PROTOCOL_NUMBER);
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy").parse(getString(offense, DATE));
                    } catch (ParseException e) {
                        Log.i(TAG, "Ошибка обработки даты протокола");
                        e.printStackTrace();
                    }
                    String place = getString(offense, PLACE);
                    String article = getString(offense, ARTICLE);
                    String codex = getString(offense, CODEX);
                    Log.i(TAG, "1-th parsed");
                    offensesList.add(0, new OffenseModel(id, protocolNumber, date, place,
                            new ArticleModel(article, codex)));
                    Log.i(TAG, "1-th added in list");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "Ошибка обработки протокола");
                    cache.setServerMessage("Ошибка обработки протокола");
                }
            }
            data.setHistoryOffenses(offensesList);
            Log.i(TAG, "add list in history");
        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseGetOffenseResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            JSONObject body = null;
            try {
                body = response.getJSONObject(BODY);
            } catch (JSONException e) {
                cache.setErrorMessage("Ошибка обработки JSON");
                Log.i(TAG, "BODY didn't find");
                return false;
            }
            int id = getInteger(body, ID);
            String number_protocol = getString(body, PROTOCOL_NUMBER);
            Date date = null;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(getString(body, DATE));
            } catch (ParseException e) {
                Log.i(TAG, "Ошибка обработки даты протокола");
                e.printStackTrace();
            }
            String place = getString(body, PLACE);
            String description = getString(body, DESCRIPTION);
            String article_number = getString(body, ARTICLE_NUMBER);
            String codex = getString(body, CODEX);

            EmployeeModel employee = null;
            try {
                JSONObject employeeJSON = body.getJSONObject(EMPLOYEE);
                String s_name_employee = getString(employeeJSON, SECOND_NAME);
                String f_name_employee = getString(employeeJSON, FIRST_NAME);
                String m_name_employee = getString(employeeJSON, MIDDLE_NAME);
                String position_employee = getString(employeeJSON, POSITION);
                Log.i(TAG, "position employee: " + position_employee);
                String rank_employee = getString(employeeJSON, RANK);
                String work_employee = getString(employeeJSON, PLACE_OF_WORK);
                employee = new EmployeeModel(s_name_employee, f_name_employee, m_name_employee,
                        position_employee, rank_employee, work_employee);
            } catch(JSONException e) {
                Log.i(TAG, "Ошибка обработки данных о сотруднике");
                e.printStackTrace();
            }

            ViolatorModel offender = null;
            try {
                JSONObject witnessJSON = body.getJSONObject(OFFENDER);
                String s_name = getString(witnessJSON, SECOND_NAME);
                String f_name = getString(witnessJSON, FIRST_NAME);
                String m_name = getString(witnessJSON, MIDDLE_NAME);
                Date birth_day = null;
                try {
                    birth_day = new SimpleDateFormat("dd/MM/yyyy").
                            parse(getString(witnessJSON, BIRTH_DAY));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String address = getString(witnessJSON, ADDRESS);
                String citizenship = getString(witnessJSON, CITIZENSHIP);
                String work = getString(witnessJSON, WORK);
                String position = getString(witnessJSON, POSITION);
                String phone = getString(witnessJSON, PHONE);
                offender = new ViolatorModel(s_name, f_name, m_name, birth_day,
                        address, citizenship, work, position, phone);
            } catch(JSONException e) {
                Log.i(TAG, "Ошибка обработки данных о нарушителе");
                e.printStackTrace();
            }

            VictimModel victim = null;
            try {
                JSONObject victimJSON = body.getJSONObject(VICTIM);
                String s_name = getString(victimJSON, SECOND_NAME);
                String f_name = getString(victimJSON, FIRST_NAME);
                String m_name = getString(victimJSON, MIDDLE_NAME);
                String address = getString(victimJSON, ADDRESS);
                String phone = getString(victimJSON, PHONE);
                victim = new VictimModel(s_name, f_name, m_name, address, phone);
            } catch(JSONException e) {
                Log.i(TAG, "Ошибка обработки данных о потерпевшем");
                e.printStackTrace();
            }

            WitnessModel first_witness = null;
            try {
                JSONObject witnessJSON = body.getJSONObject(FIRST_WITNESS);
                String s_name = getString(witnessJSON, SECOND_NAME);
                String f_name = getString(witnessJSON, FIRST_NAME);
                String m_name = getString(witnessJSON, MIDDLE_NAME);
                String address = getString(witnessJSON, ADDRESS);
                String phone = getString(witnessJSON, PHONE);
                first_witness = new WitnessModel(s_name, f_name, m_name, address, phone);
            } catch(JSONException e) {
                Log.i(TAG, "Ошибка обработки данных о первом свидетеле");
                e.printStackTrace();
            }

            WitnessModel second_witness = null;
            try {
                JSONObject witnessJSON = body.getJSONObject(SECOND_WITNESS);
                String s_name = getString(witnessJSON, SECOND_NAME);
                String f_name = getString(witnessJSON, FIRST_NAME);
                String m_name = getString(witnessJSON, MIDDLE_NAME);
                String address = getString(witnessJSON, ADDRESS);
                String phone = getString(witnessJSON, PHONE);
                second_witness = new WitnessModel(s_name, f_name, m_name, address, phone);
            } catch(JSONException e) {
                Log.i(TAG, "Ошибка обработки данных о втором свидетеле");
                e.printStackTrace();
            }

            cache.setCurrentOffense(new OffenseModel(id, number_protocol, employee,
                    date, place, new ArticleModel(article_number, codex), description,
                    offender, victim, first_witness, second_witness));

            Log.i(TAG, "employee: " + employee.toString());

        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseFilterOffensesResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            JSONObject body = null;
            try {
                body = response.getJSONObject(BODY);
            } catch (JSONException e) {
                String message = null;
                Log.i(TAG, "BODY didn't find");
                try {
                    message = response.getString(MESSAGE);
                    cache.setServerMessage(message);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Log.i(TAG, "Ошибка обработки истории протоколов");
                    cache.setErrorMessage("Ошибка обработки истории протоколов");
                    return false;
                }
                return true;
            }
            Log.i(TAG, "BODY found");
            JSONArray arrayOfffenses = null;
            try {
                arrayOfffenses = body.getJSONArray(OFFENSES);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG, "Ошибка обработки истории протоколов");
                cache.setErrorMessage("Ошибка обработки истории протоколов");
                return false;
            }
            List<OffenseModel> offensesList = new ArrayList<OffenseModel>();
            for(int i = 0; i < arrayOfffenses.length(); i++) {
                Log.i(TAG, "parse 1-th offense");
                try {
                    JSONObject offense = arrayOfffenses.getJSONObject(i);
                    int id = getInteger(offense, ID);
                    String protocolNumber = getString(offense, PROTOCOL_NUMBER);
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy").parse(getString(offense, DATE));
                    } catch (ParseException e) {
                        Log.i(TAG, "Ошибка обработки даты протокола");
                        e.printStackTrace();
                    }
                    String place = getString(offense, PLACE);
                    String article = getString(offense, ARTICLE);
                    String codex = getString(offense, CODEX);
                    Log.i(TAG, "1-th parsed");
                    offensesList.add(0, new OffenseModel(id, protocolNumber, date, place,
                            new ArticleModel(article, codex)));
                    Log.i(TAG, "1-th added in list");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "Ошибка обработки протокола");
                    cache.setServerMessage("Ошибка обработки протокола");
                }
            }
            cache.setListOffenses(offensesList);
            Log.i(TAG, "add list in history");
        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseListOffenderResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            JSONObject body = null;
            try {
                body = response.getJSONObject(BODY);
            } catch (JSONException e) {
                String message = null;
                Log.i(TAG, "BODY didn't find");
                try {
                    message = response.getString(MESSAGE);
                    cache.setServerMessage(message);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Log.i(TAG, "Ошибка обработки ответа");
                    cache.setErrorMessage("Ошибка обработки ответа");
                    return false;
                }
                return true;
            }
            Log.i(TAG, "BODY found");
            JSONArray arrayOfffenses = null;
            try {
                arrayOfffenses = body.getJSONArray(OFFENDERS);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG, "Ошибка обработки списка правонарушителей");
                cache.setErrorMessage("Ошибка обработки списка правонарушителей");
                return false;
            }
            List<ViolatorModel> offendersList = new ArrayList<ViolatorModel>();
            for(int i = 0; i < arrayOfffenses.length(); i++) {
                try {
                    JSONObject offender = arrayOfffenses.getJSONObject(i);
                    int id = getInteger(offender, ID);
                    String s_name = getString(offender, SECOND_NAME);
                    String f_name = getString(offender, FIRST_NAME);
                    String m_name = getString(offender, MIDDLE_NAME);
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy").parse(getString(offender, BIRTH_DAY));
                    } catch (ParseException e) {
                        Log.i(TAG, "Ошибка обработки даты рождения");
                        e.printStackTrace();
                    }

                    offendersList.add(0, new ViolatorModel(id, s_name, f_name, m_name, date));
                    Log.i(TAG, "1-th added in list");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "Ошибка обработки json");
                    cache.setServerMessage("Ошибка обработки ответа");
                }
            }
            cache.setListOffenders(offendersList);
            Log.i(TAG, "save list offenders");
        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseGetOffenderResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            JSONObject body = null;
            try {
                body = response.getJSONObject(BODY);
            } catch (JSONException e) {
                e.printStackTrace();
                cache.setErrorMessage("Ошибка обработки JSON");
                return false;
            }
            String secondName = getString(body, SECOND_NAME);
            String firstName = getString(body, FIRST_NAME);
            String middleName = getString(body, MIDDLE_NAME);
            Date birth_date = null;
            try {
                birth_date = new SimpleDateFormat("dd/MM/yyyy").parse(getString(body, BIRTH_DAY));
            } catch (ParseException e) {
                Log.i(TAG, "Ошибка обработки даты рождения");
                e.printStackTrace();
            }
            String address = getString(body, ADDRESS);
            String citizenship = getString(body, CITIZENSHIP);
            String work = getString(body, WORK);
            String position = getString(body, POSITION);
            String phone = getString(body, PHONE);
            cache.setOffenderDetails(new ViolatorModel(secondName, firstName,
                    middleName, birth_date, address, citizenship, work, position, phone));

        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseListArticlesResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            JSONObject body = null;
            try {
                body = response.getJSONObject(BODY);
            } catch (JSONException e) {
                String message = null;
                Log.i(TAG, "BODY didn't find");
                try {
                    message = response.getString(MESSAGE);
                    cache.setServerMessage(message);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Log.i(TAG, "Ошибка обработки ответа");
                    cache.setErrorMessage("Ошибка обработки ответа");
                    return false;
                }
                return true;
            }
            Log.i(TAG, "BODY found");
            JSONArray arrayArticles = null;
            try {
                arrayArticles = body.getJSONArray(ARTICLES);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG, "Ошибка обработки списка статей");
                cache.setErrorMessage("Ошибка обработки списка статей");
                return false;
            }
            List<ArticleModel> articlesList = new ArrayList<ArticleModel>();
            for(int i = 0; i < arrayArticles.length(); i++) {
                try {
                    JSONObject articleJSON = arrayArticles.getJSONObject(i);
                    int id = getInteger(articleJSON, ID);
                    String number_article = getString(articleJSON, NUMBER_ARTICLE);
                    String codex_name = getString(articleJSON, CODEX);
                    String article_name = getString(articleJSON, NAME);
                    articlesList.add(0, new ArticleModel(id, number_article, codex_name, article_name));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "Ошибка обработки json");
                    cache.setServerMessage("Ошибка обработки ответа");
                }
            }
            cache.setListArticles(articlesList);
            Log.i(TAG, "save list offenders");
        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean parseGetArticleResponse(JSONObject response) {
        boolean status = getStatus(response);
        if(status) {
            JSONObject body = null;
            try {
                body = response.getJSONObject(BODY);
            } catch (JSONException e) {
                e.printStackTrace();
                cache.setErrorMessage("Ошибка обработки JSON");
                return false;
            }
            String numberArticle = getString(body, NUMBER_ARTICLE);
            String codexType = getString(body, CODEX_TYPE);
            String name = getString(body, NAME);
            String description = getString(body, DESCRIPTION);
            boolean warning = getBoolean(body, WARNING);
            int minMulct = getInteger(body, MIN_MULCT);
            int maxMulct = getInteger(body, MAX_MULCT);
            cache.setArticleDetails(new ArticleModel(0, numberArticle,
                    codexType, name, description, warning, minMulct, maxMulct));

        } else {
            try {
                cache.setErrorMessage(response.getString(MESSAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

}
