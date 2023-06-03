package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Kereses {

    private StringProperty tag = new SimpleStringProperty();
    private StringProperty felhasznalonev = new SimpleStringProperty();
    private IntegerProperty videoID = new SimpleIntegerProperty();

    public Kereses() {
    }

    public Kereses(String tag, String felhasznalonev, int videoID) {
        this.tag.set(tag);
        this.felhasznalonev.set(felhasznalonev);
        this.videoID.set(videoID);
    }

    public String getTag() {
        return tag.get();
    }

    public StringProperty tagProperty() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag.set(tag);
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
