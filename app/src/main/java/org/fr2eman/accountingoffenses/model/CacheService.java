package org.fr2eman.accountingoffenses.model;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fr2eman on 25.03.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class CacheService {

    public enum Mode {NONE, MODE_ADDED_OFFENSE, MODE_AUTHORIZATION };

    private String errorMessage;
    private String serverMessage;
    private String inputUsername;
    private String inputPassword;
    private OffenseModel currentOffense;
    private Mode current_mode;
    private List<OffenseModel> offensesList;
    private List<ViolatorModel> offendersList;
    private List<ArticleModel> articlesList;
    private ViolatorModel offenderDetails;
    private ArticleModel articleDetails;

    @Bean
    DataService data;

    public CacheService() {
        this.errorMessage = "";
        this.inputUsername = "";
        this.inputPassword = "";
        this.serverMessage = "";
        this.currentOffense = new OffenseModel();
        this.current_mode = Mode.NONE;
        this.offensesList = new ArrayList<OffenseModel>();
        this.offendersList = new ArrayList<ViolatorModel>();
        this.articlesList = new ArrayList<ArticleModel>();
        this.offenderDetails = new ViolatorModel();
        this.articleDetails = new ArticleModel();
    }

    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }
    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setInputUsername(String login) {
        this.inputUsername = login;
    }
    public String getInputUsername() {
        return this.inputUsername;
    }

    public void setInputPassword(String pass) {
        this.inputPassword = pass;
    }
    public String getInputPassword() {
        return this.inputPassword;
    }

    public void setCurrentOffense(OffenseModel offense) {
        this.currentOffense = offense;
    }
    public OffenseModel getCurrentOffense() {
        return this.currentOffense;
    }

    public void setServerMessage(String message) {
        this.serverMessage = message;
    }
    public String getServerMessage() {
        return this.serverMessage;
    }

    public void setCurrentMode(Mode mode) {
        this.current_mode = mode;
    }
    public Mode getCurrentMode() {
        return this.current_mode;
    }

    public void setListOffenses(List<OffenseModel> offensesList) {
        this.offensesList = offensesList;
    }
    public List<OffenseModel> getListOffenses() {
        return this.offensesList;
    }

    public void setListOffenders(List<ViolatorModel> offendersList) {
        this.offendersList = offendersList;
    }
    public List<ViolatorModel> getListOffenders() {
        return this.offendersList;
    }

    public void setOffenderDetails(ViolatorModel model) {
        this.offenderDetails = model;
    }
    public ViolatorModel getOffenderDetails() {
        return this.offenderDetails;
    }

    public void setListArticles(List<ArticleModel> articlesList) {
        this.articlesList = articlesList;
    }
    public List<ArticleModel> getListArticles() {
        return this.articlesList;
    }

    public void setArticleDetails(ArticleModel model) {
        this.articleDetails = model;
    }
    public ArticleModel getArticleDetails() {
        return this.articleDetails;
    }
}
