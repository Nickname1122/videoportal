package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LejatszasiLista {

    private IntegerProperty videokSzama = new SimpleIntegerProperty();
    private StringProperty listaCime = new SimpleStringProperty();
    private StringProperty felhasznalonev = new SimpleStringProperty();
    private IntegerProperty videoID = new SimpleIntegerProperty();

    public LejatszasiLista() {
    }

    public LejatszasiLista(int videokSzama, String listaCime, String felhasznalonev, int videoID) {
        this.videokSzama.set(videokSzama);
        this.listaCime.set(listaCime);
        this.felhasznalonev.set(felhasznalonev);
        this.videoID.set(videoID);
    }

    public int getVideokSzama() {
        return videokSzama.get();
    }

    public IntegerProperty videokSzamaProperty() {
        return videokSzama;
    }

    public void setVideokSzama(int videokSzama) {
        this.videokSzama.set(videokSzama);
    }

    public String getListaCime() {
        return listaCime.get();
    }

    public StringProperty listaCimeProperty() {
        return listaCime;
    }

    public void setListaCime(String listaCime) {
        this.listaCime.set(listaCime);
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

    public void copyTo(LejatszasiLista li) {

        li.setListaCime(this.getListaCime());
        li.setFelhasznalonev(this.getFelhasznalonev());
    }
}
