package org.fr2eman.accountingoffenses.network;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Bean;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.model.DataService;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

/**
 * Created by fr2eman on 06.04.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ServerFacade {

    private static final int TIMEOUT_MILLISEC = 20000;

    private static final String TAG = "fr2eman";
    private static final String URL_API_V1 = "http://thawing-meadow-60395.herokuapp.com/webservice/api/v1/";
    private static final String URL_AUTHORIZATION = "authorization";
    private static final String URL_LOGOUT = "logout";
    private static final String URL_CHECK_OFFENSE = "offenses/check";
    private static final String URL_ADD_OFFENSE = "offenses/add";
    private static final String URL_HISTORY_OFFENSES = "offenses/history";
    private static final String URL_GET_OFFENSE = "offenses/get";
    private static final String URL_FILTER_OFFENSES = "offenses/filter";
    private static final String URL_GET_OFFENDER = "offender/get";
    private static final String URL_SEARCH_OFFENDER = "offender/search";
    private static final String URL_GET_ARTICLE = "articles/get";
    private static final String URL_SEARCH_ARTICLES = "articles/search";

    @Bean
    APIBuilder apiBuilder;

    @Bean
    ResponseParser responseParser;

    @Bean
    CacheService cache;

    @Bean
    DataService data;

    public boolean requestAuthorization() {

        HashMap<String, String> params = apiBuilder.getAuthorizationParams(
                cache.getInputUsername(), cache.getInputPassword());
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_AUTHORIZATION, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        return responseParser.parseAuthorizationResponse(responseData);
    }

    public boolean requestLogout() {
        HashMap<String, String> params = apiBuilder.getLogoutParams(
                data.getCurrentEmployee().getPrivateNumber(), data.getAuthtoken());
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_LOGOUT, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseLogoutResponse(responseData);
    }

    public boolean requestCheckOffense(String article, int codex) {
        HashMap<String, String> params = apiBuilder.getCheckOffenseParams(
                data.getAuthtoken(), article, codex);
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_CHECK_OFFENSE, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseCheckOffenseResponse(responseData);
    }

    public boolean requestAddOffense() {
        HashMap<String, String> params = apiBuilder.getAddOffenseParams();
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_ADD_OFFENSE, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseAddOffenseResponse(responseData);
    }

    public boolean requestHistoryOffenses() {
        HashMap<String, String> params = apiBuilder.getHistoryOffensesParams();
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_HISTORY_OFFENSES, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseHistoryOffensesResponse(responseData);
    }

    public boolean requestgetOffense(String protocolNumber) {
        HashMap<String, String> params = apiBuilder.getGetOffenseParams(protocolNumber);
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_GET_OFFENSE, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseGetOffenseResponse(responseData);
    }

    public boolean requestFilterOffenses(boolean isAll, Date begin, Date end) {
        HashMap<String, String> params = apiBuilder.getFilterOffensesParams(isAll, begin, end);
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_FILTER_OFFENSES, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseFilterOffensesResponse(responseData);
    }

    public boolean requestSearchOffenders(String s_name, String f_name, String m_name) {
        HashMap<String, String> params = apiBuilder.getSearchOffendersParams(s_name, f_name, m_name);
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_SEARCH_OFFENDER, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseListOffenderResponse(responseData);
    }

    public boolean requestGetOffender(int id) {
        HashMap<String, String> params = apiBuilder.getGetOffenderParams(id);
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_GET_OFFENDER, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseGetOffenderResponse(responseData);
    }

    public boolean requestSearchArticles(String number_article, String description) {
        HashMap<String, String> params = apiBuilder.getSearchArticlesParams(number_article, description);
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_SEARCH_ARTICLES, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseListArticlesResponse(responseData);
    }

    public boolean requestGetArticle(int id_article) {
        HashMap<String, String> params = apiBuilder.getGetArticleParams(id_article);
        String response = send(apiBuilder.buildURL(URL_API_V1 + URL_GET_ARTICLE, params));
        JSONObject responseData = null;
        try {
            responseData = new JSONObject(response);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }
        return responseParser.parseGetArticleResponse(responseData);
    }

    private String send(String urlAddress) {
        BufferedReader reader=null;
        try {
            Log.i(TAG, "HTTP REQUEST:" + urlAddress);
            URL url=new URL(urlAddress);
            HttpURLConnection c=(HttpURLConnection)url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(TIMEOUT_MILLISEC);
            c.connect();
            reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf=new StringBuilder();
            String line=null;
            while ((line=reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            Log.i(TAG, "HTTP RESPONSE:" + buf.toString());
            return buf.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "{'STATUS':'ERROR', 'MESSAGE':'Сервер недоступен'}";
    }
}
