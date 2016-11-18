package org.fr2eman.accountingoffenses.model;

import java.util.Date;

/**
 * Created by fr2eman on 08.04.2016.
 */
public class ViolatorModel {

    private int id;
    private String secondName;
    private String firstName;
    private String middleName;
    private Date dateOfBrith;
    private String address;
    private String citizenship;
    private String work;
    private String position;
    private String phone;

    public ViolatorModel() {
        this.id = 0;
        this.secondName = "";
        this.firstName = "";
        this.middleName = "";
        this.dateOfBrith = new Date();
        this.address = "";
        this.citizenship = "";
        this.work = "";
        this.position = "";
        this.phone = "";
    }

    public ViolatorModel(int id, String secondName, String firstName, String middleName,
                         Date dateOfBrith, String address, String citizenship,
                         String work, String position, String phone) {
        this.id = id;
        this.secondName = secondName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBrith = dateOfBrith;
        this.address = address;
        this.citizenship = citizenship;
        this.work = work;
        this.position = position;
        this.phone = phone;
    }

    public ViolatorModel(int id, String secondName, String firstName, String middleName,
                         Date dateOfBrith) {
        this.id = id;
        this.secondName = secondName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBrith = dateOfBrith;
        this.address = "";
        this.citizenship = "";
        this.work = "";
        this.position = "";
        this.phone = "";
    }

    public ViolatorModel(String secondName, String firstName, String middleName,
                         Date dateOfBrith, String address, String citizenship,
                         String work, String position, String phone) {
        this.id = 0;
        this.secondName = secondName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBrith = dateOfBrith;
        this.address = address;
        this.citizenship = citizenship;
        this.work = work;
        this.position = position;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object object) {
        ViolatorModel violator = (ViolatorModel) object;
        if(this.id == violator.id) {
            return true;
        } else return false;
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }

    public void setSecondName(String secondName) { this.secondName = secondName; }
    public String getSecondName() { return this.secondName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getFirstName() { return this.firstName; }

    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getMiddleName() { return this.middleName; }

    public void setDateOfBrith(Date dateOfBrith) { this.dateOfBrith = dateOfBrith; }
    public Date getDateOfBrith() { return this.dateOfBrith; }

    public void setAddress(String address) { this.address = address; }
    public String getAddress() { return this.address; }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }
    public String getCitizenship() {
        return this.citizenship;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return this.position;
    }

    public void setWork(String work) {
        this.work = work;
    }
    public String getWork() {
        return this.work;
    }

    public void setPhone(String phone) { this.phone = phone; }
    public String getPhone() { return this.phone; }
}
