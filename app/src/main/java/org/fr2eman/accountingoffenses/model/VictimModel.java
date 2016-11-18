package org.fr2eman.accountingoffenses.model;

/**
 * Created by fr2eman on 08.04.2016.
 */
public class VictimModel {

    public static final int VICTIM_NOT_EXIST = 0;
    public static final int VICTIM_EXIST = 1;

    private int id;
    private String secondName;
    private String firstName;
    private String middleName;
    private String address;
    private String phone;

    public VictimModel() {
        this.id = 0;
        this.secondName = "";
        this.firstName = "";
        this.middleName = "";
        this.address = "";
        this.phone = "";
    }

    public VictimModel(int id, String secondName, String firstName,
                       String middleName, String address, String phone) {
        this.id = id;
        this.secondName = secondName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.phone = phone;
    }

    public VictimModel(String secondName, String firstName,
                       String middleName, String address, String phone) {
        this.id = 0;
        this.secondName = secondName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object object) {
        VictimModel victim = (VictimModel) object;
        if(this.id == victim.id) {
            return true;
        } else return false;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getSecondName() {
        return this.secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getMiddleName() {
        return this.middleName;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return this.address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return this.phone;
    }

}
