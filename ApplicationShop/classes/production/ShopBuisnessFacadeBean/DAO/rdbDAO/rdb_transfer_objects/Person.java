package DAO.rdbDAO.rdb_transfer_objects;

import java.io.Serializable;

public class Person implements Serializable {
    private int personID;
    private int fkAdvicerID;
    private String lastname;
    private String firstname;
    private Person advicer;


    public Person(int personID) {
        this.personID = personID;
    }

    public Person(int personID, String lastname, String firstname, int advicerID) {
        this.personID = personID;
        this.lastname = lastname;
        this.firstname = firstname;
        this.fkAdvicerID = advicerID;
    }

    public Person(String lastname, String firstname, int advicerID) {
        this.personID = 0;
        this.lastname = lastname;
        this.firstname = firstname;
        this.fkAdvicerID = advicerID;
    }

    public Person(String lastname, String firstname) {
        this.personID = 0;
        this.lastname = lastname;
        this.firstname = firstname;
        this.fkAdvicerID = 0;
    }

    public Person(int personID, String lastname, String firstname) {
        this.personID = personID;
        this.lastname = lastname;
        this.firstname = firstname;
        fkAdvicerID = 0;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getFkAdvicerID() {
        return fkAdvicerID;
    }

    public void setFkAdvicerID(int fkAdvicerID) {
        this.fkAdvicerID = fkAdvicerID;
    }


    public Person getAdvicer() {
        return advicer;
    }

    public void setAdvicer(Person advicer) {
        this.advicer = advicer;
    }

    public String toString() {
        if (getLastname()!=null && getFirstname()!=null){
            return /*getPersonID() +": "+ */getLastname() + " " + getFirstname();
        }
        return "--";
    }
}
