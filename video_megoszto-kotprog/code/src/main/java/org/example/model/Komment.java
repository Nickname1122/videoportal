package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Komment {

    private StringProperty felhasznalonev = new SimpleStringProperty();
    private IntegerProperty kommentID = new SimpleIntegerProperty();
    private StringProperty kommentTartalom = new SimpleStringProperty();
    private IntegerProperty videoID = new SimpleIntegerProperty();

    public Komment() {
    }

    public Komment(String felhasznalonev, int kommentID, String kommentTartalom, int videoID) {
        this.felhasznalonev.set(felhasznalonev);
        this.kommentID.set(kommentID);
        this.kommentTartalom.set(kommentTartalom);
        this.videoID.set(videoID);
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

    public int getKommentID() {
        return kommentID.get();
    }

    public IntegerProperty kommentIDProperty() {
        return kommentID;
    }

    public void setKommentID(int komentID) {
        this.kommentID.set(komentID);
    }

    public String getKommentTartalom() {
        return kommentTartalom.get();
    }

    public StringProperty kommentTartalomProperty() {
        return kommentTartalom;
    }

    public void setKommentTartalom(String kommentTartalom) {
        this.kommentTartalom.set(kommentTartalom);
    }

    public int getVideoID() {
        return videoID.get();
    }

    public IntegerProperty videoIDProperty() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID.set(videoID);
    }
}
