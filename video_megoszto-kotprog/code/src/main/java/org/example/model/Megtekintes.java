package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Megtekintes {

    private StringProperty felhasznalonev = new SimpleStringProperty();
    private IntegerProperty videoID = new SimpleIntegerProperty();

    public Megtekintes() {
    }

    public Megtekintes(String felhasznalonev, int videoID) {
        this.felhasznalonev.set(felhasznalonev);
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
