package org.example.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Felhasznalo {

    private StringProperty felhasznalonev = new SimpleStringProperty();
    private StringProperty jelszo = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty szuletesiDatum = new SimpleStringProperty();
    private BooleanProperty bejelentkezett = new SimpleBooleanProperty();

    public Felhasznalo() {
    }

    public Felhasznalo(String felhasznalonev) {
        this.felhasznalonev.set(felhasznalonev);
    }

    public Felhasznalo(String felhasznalonev, String jelszo, String email, String szuletesiDatum) {
        this.felhasznalonev.set(felhasznalonev);
        this.jelszo.set(jelszo);
        this.email.set(email);
        this.szuletesiDatum.set(szuletesiDatum);
    }

    public void copyTo(Felhasznalo felhasznalo) {
        felhasznalo.setFelhasznalonev(this.getFelhasznalonev());
        felhasznalo.setEmail(this.getEmail());
        felhasznalo.setSzuletesiDatum(this.getSzuletesiDatum());
    }

    public String getFelhasznalonev() {
        return felhasznalonev.get();
    }

    public StringProperty felhasznalonevProperty() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev.set(felhasznalonev);
    }

    public String getJelszo() {
        return jelszo.get();
    }

    public StringProperty jelszoProperty() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo.set(jelszo);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getSzuletesiDatum() {
        return szuletesiDatum.get();
    }

    public StringProperty szuletesiDatumProperty() {
        return szuletesiDatum;
    }

    public void setSzuletesiDatum(String szuletesiDatum) {
        this.szuletesiDatum.set(szuletesiDatum);
    }

    public boolean isBejelentkezett() {
        return bejelentkezett.get();
    }

    public BooleanProperty bejelentkezettProperty() {
        return bejelentkezett;
    }

    public void setBejelentkezett(boolean bejelentkezett) {
        this.bejelentkezett.set(bejelentkezett);
    }
}
