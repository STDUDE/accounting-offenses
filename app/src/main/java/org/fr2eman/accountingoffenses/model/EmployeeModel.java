package org.fr2eman.accountingoffenses.model;

/**
 * Created by fr2eman on 08.04.2016.
 */
public class EmployeeModel {

    private int id;
    private String privateNumber;
    private String secondName;
    private String firstName;
    private String middleName;
    private String position;
    private String rank;
    private String placeOfWork;

    public EmployeeModel() {
        this.id = 0;
        this.privateNumber = "";
        this.secondName = "";
        this.firstName = "";
        this.middleName = "";
        this.position = "";
        this.rank = "";
        this.placeOfWork = "";
    }

    public EmployeeModel(int id, String privateNumber, String secondName, String firstName,
                          String middleName, String position, String rank, String placeOfWork) {
        this.id = id;
        this.privateNumber  = privateNumber;
        this.secondName = secondName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.rank = rank;
        this.placeOfWork = placeOfWork;
    }

    public EmployeeModel(String secondName, String firstName,
                         String middleName, String position, String rank, String placeOfWork) {
        this.id = 0;
        this.privateNumber  = "";
        this.secondName = secondName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.rank = rank;
        this.placeOfWork = placeOfWork;
    }

    @Override
    public boolean equals(Object object) {
        EmployeeModel employee = (EmployeeModel) object;
        if(this.privateNumber.equals(employee.privateNumber)) {
            return true;
        } else return false;
    }

    @Override
    public String toString() {
        return "Employee (" + id + ", " + privateNumber + ", " + secondName + ", " + firstName + ", "
                + middleName + ", " + position + ", " + rank + ", " + placeOfWork + ")";
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public void setPrivateNumber(String privateNumber) {
        this.privateNumber = privateNumber;
    }
    public String getPrivateNumber() {
        return this.privateNumber;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getSecondName() {
        return this.secondName;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getFirstName() {
        return this.firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getMiddleName() {
        return this.middleName;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return this.position;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
    public String getRank() {
        return this.rank;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }
    public String getPlaceOfWork() {
        return this.placeOfWork;
    }

}
