package org.fr2eman.accountingoffenses.model;

import java.util.Date;

/**
 * Created by fr2eman on 08.04.2016.
 */
public class OffenseModel {

    private int id;
    private String numberProtocol;
    private EmployeeModel employeeMIA;
    private Date dateOffense;
    private String place;
    private ArticleModel article;
    private String description;
    private ViolatorModel offender;
    private VictimModel victim;
    private WitnessModel firstWitness;
    private WitnessModel secondWitness;

    public OffenseModel() {
        this.id = 0;
        this.numberProtocol = "";
        this.employeeMIA = new EmployeeModel();
        this.dateOffense = new Date();
        this.place = "";
        this.article = new ArticleModel();
        this.description = "";
        this.offender = new ViolatorModel();
        this.victim = new VictimModel();
        this.firstWitness = null;
        this.secondWitness = null;
    }

    public OffenseModel(int id, String numberProtocol, EmployeeModel employee, Date dateOffense,
                        String place, ArticleModel article, String description, ViolatorModel offender,
                        VictimModel victim, WitnessModel firstWitness, WitnessModel secondWitness) {
        this.id = id;
        this.numberProtocol = numberProtocol;
        this.employeeMIA = employee;
        this.dateOffense = dateOffense;
        this.place = place;
        this.article = article;
        this.description = description;
        this.offender = offender;
        this.victim = victim;
        this.firstWitness = firstWitness;
        this.secondWitness = secondWitness;
    }

    public OffenseModel(int id, String numberProtocol, Date dateOffense,
                        String place) {
        this.id = id;
        this.numberProtocol = numberProtocol;
        this.employeeMIA = null;
        this.dateOffense = dateOffense;
        this.place = place;
        this.article = null;
        this.description = "";
        this.offender = null;
        this.victim = null;
        this.firstWitness = null;
        this.secondWitness = null;
    }

    public OffenseModel(int id, String numberProtocol, Date dateOffense,
                        String place, ArticleModel articleModel) {
        this.id = id;
        this.numberProtocol = numberProtocol;
        this.employeeMIA = null;
        this.dateOffense = dateOffense;
        this.place = place;
        this.article = articleModel;
        this.description = "";
        this.offender = null;
        this.victim = null;
        this.firstWitness = null;
        this.secondWitness = null;
    }

    @Override
    public boolean equals(Object object) {
        OffenseModel offense = (OffenseModel) object;
        if(this.id == offense.id || this.numberProtocol == offense.numberProtocol) {
            return true;
        } else return false;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public void setNumberProtocol(String number) {
        this.numberProtocol = number;
    }
    public String getNumberProtocol() {
        return this.numberProtocol;
    }

    public void setEmployeeMIA(EmployeeModel employee) {
        this.employeeMIA = employee;
    }
    public EmployeeModel getEmployeeMIA() {
        return this.employeeMIA;
    }

    public void setDateOffense(Date dateOffense) {
        this.dateOffense = dateOffense;
    }
    public Date getDateOffense() {
        return this.dateOffense;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    public String getPlace() {
        return this.place;
    }

    public void setArticle(ArticleModel article) {
        this.article = article;
    }
    public ArticleModel getArticle() {
        return this.article;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }

    public void setOffender(ViolatorModel offender) {
        this.offender = offender;
    }
    public ViolatorModel getOffender() {
        return this.offender;
    }

    public void setVictim(VictimModel victim) {
        this.victim = victim;
    }
    public VictimModel getVictim() {
        return this.victim;
    }

    public void setFirstWitness(WitnessModel witness) {
        this.firstWitness = witness;
    }
    public WitnessModel getFirstWitness() {
        return this.firstWitness;
    }

    public void setSecondWitness(WitnessModel witness) {
        this.secondWitness = witness;
    }
    public WitnessModel getSecondWitness() {
        return this.secondWitness;
    }

}
