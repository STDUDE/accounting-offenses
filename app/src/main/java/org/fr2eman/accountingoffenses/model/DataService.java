package org.fr2eman.accountingoffenses.model;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fr2eman on 25.03.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataService {

    private final String month[] = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа",
            "сентября", "октября", "ноября", "декабря"};

    private EmployeeModel currentEmployee;
    private String authtoken;
    private List<OffenseModel> historyOffenses;

    public DataService() {
        this.currentEmployee = new EmployeeModel(4, "59337", "Малич", "Степан", "Александрович",
                "Участковый инспектор милиции", " старший лейтенант милиции", "Ивановский РОВД");
        this.authtoken = "";
        this.historyOffenses = new ArrayList<OffenseModel>();
    }

    public void setCurrentEmployee(EmployeeModel employee) {
        this.currentEmployee = employee;
    }
    public EmployeeModel getCurrentEmployee() {
        return this.currentEmployee;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
    public String getAuthtoken() {
        return this.authtoken;
    }

    public String getMonthNameByNumber(int number) {
        if(number < 0 || number > 12) return "";
        return this.month[number];
    }

    public List<OffenseModel> getHistoryOffenses() {
        return this.historyOffenses;
    }
    public void setHistoryOffenses(List<OffenseModel> list) {
        this.historyOffenses.clear();
        this.historyOffenses.addAll(list);
    }
    public void addOffenseIntoHistory(OffenseModel offense) {
        this.historyOffenses.add(offense);
    }
    public void addOffenseIntoHistoryToFront(OffenseModel offense) {
        this.historyOffenses.add(0, offense);
    }
    public void clearOffensesHistory() {
        this.historyOffenses.clear();
    }
}
