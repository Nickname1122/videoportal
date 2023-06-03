package org.example.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Admin extends Felhasznalo {

    private StringProperty adminID = new SimpleStringProperty();

    public Admin() {
    }

    public Admin(String felhasznalonev, String jelszo, String email, String szuletesiDatum, String adminID) {
        super(felhasznalonev, jelszo, email, szuletesiDatum);
        this.adminID.set(adminID);
    }

    public String getAdminID() {
        return adminID.get();
    }

    public StringProperty adminIDProperty() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID.set(adminID);
    }
}
