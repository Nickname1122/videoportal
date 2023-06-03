package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

import java.util.Arrays;

public class Video {


    private IntegerProperty videoID = new SimpleIntegerProperty();
    private StringProperty cim = new SimpleStringProperty();
    private StringProperty megtekintesekSzama = new SimpleStringProperty();
    private StringProperty kategoria = new SimpleStringProperty();
    private StringProperty leiras = new SimpleStringProperty();
    private StringProperty kedvelesekSzama = new SimpleStringProperty();
    private StringProperty stillKep = new SimpleStringProperty();
    private StringProperty feltoltesDatuma = new SimpleStringProperty();
    private StringProperty fajlNeve = new SimpleStringProperty();
    private StringProperty feltoltoNeve = new SimpleStringProperty();
    private ImageView stillView = new ImageView();


    public Video() {
    }

    public Video(int videoID) {
        this.videoID.set(videoID);
    }

    public Video(String cim, String kategoria, String leiras, String feltoltesDatuma, String fajlNeve, String feltoltoNeve) {
        this.cim.set(cim);
        this.kategoria.set(kategoria);
        this.leiras.set(leiras);
        this.feltoltesDatuma.set(feltoltesDatuma);
        this.fajlNeve.set(fajlNeve);
        this.feltoltoNeve.set(feltoltoNeve);
    }

    public Video(int videoID, String cim, String megtekintesekSzama, String kategoria, String leiras, String kedvelesekSzama, String stillKep, String feltoltesDatuma, String fajlNeve, String feltoltoNeve, ImageView stillView) {
        this.videoID.set(videoID);
        this.cim.set(cim);
        this.megtekintesekSzama.set(megtekintesekSzama);
        this.kategoria.set(kategoria);
        this.leiras.set(leiras);
        this.kedvelesekSzama.set(kedvelesekSzama);
        this.stillKep.set(stillKep);
        this.feltoltesDatuma.set(feltoltesDatuma);
        this.fajlNeve.set(fajlNeve);
        this.feltoltoNeve.set(feltoltoNeve);
        this.stillView = stillView;
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

    public String getCim() {
        return cim.get();
    }

    public StringProperty cimProperty() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim.set(cim);
    }

    public String getMegtekintesekSzama() {
        return megtekintesekSzama.get();
    }

    public StringProperty megtekintesekSzamaProperty() {
        return megtekintesekSzama;
    }

    public void setMegtekintesekSzama(String megtekintesekSzama) {
        this.megtekintesekSzama.set(megtekintesekSzama);
    }

    public String getKategoria() {
        return kategoria.get();
    }

    public StringProperty kategoriaProperty() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria.set(kategoria);
    }

    public String getLeiras() {
        return leiras.get();
    }

    public StringProperty leirasProperty() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras.set(leiras);
    }

    public String getKedvelesekSzama() {
        return kedvelesekSzama.get();
    }

    public StringProperty kedvelesekSzamaProperty() {
        return kedvelesekSzama;
    }

    public void setKedvelesekSzama(String kedvelesekSzama) {
        this.kedvelesekSzama.set(kedvelesekSzama);
    }

    public String getFeltoltesDatuma() {
        return feltoltesDatuma.get();
    }

    public StringProperty feltoltesDatumaProperty() {
        return feltoltesDatuma;
    }

    public void setFeltoltesDatuma(String feltoltesDatuma) {
        this.feltoltesDatuma.set(feltoltesDatuma);
    }

    public String getFeltoltoNeve() {
        return feltoltoNeve.get();
    }

    public StringProperty feltoltoNeveProperty() {
        return feltoltoNeve;
    }

    public void setFeltoltoNeve(String feltoltoNeve) {
        this.feltoltoNeve.set(feltoltoNeve);
    }

    public String getStillKep() {
        return stillKep.get();
    }

    public StringProperty stillKepProperty() {
        return stillKep;
    }

    public void setStillKep(String stillKep) {
        this.stillKep.set(stillKep);
    }

    public String getFajlNeve() {
        return fajlNeve.get();
    }

    public StringProperty fajlNeveProperty() {
        return fajlNeve;
    }

    public void setFajlNeve(String fajlNeve) {
        this.fajlNeve.set(fajlNeve);
    }

    public ImageView getStillView() {
        return stillView;
    }

    public void setStillView(ImageView stillView) {
        this.stillView = stillView;
    }

    public void copyTo(Video target) {
        target.videoID.set(this.getVideoID());
        target.cim.set(this.getCim());
        target.megtekintesekSzama.set(this.getMegtekintesekSzama());
        target.kategoria.set(this.getKategoria());
        target.leiras.set(this.getLeiras());
        target.kedvelesekSzama.set(this.getKedvelesekSzama());
        target.feltoltesDatuma.set(this.getFeltoltesDatuma());
        target.feltoltoNeve.set(this.getFeltoltoNeve());
        target.stillView = this.stillView;
    }

    @Override
    public String toString() {
        return "Video{" +
                "videoID=" + videoID +
                ", cim=" + cim +
                ", megtekintesekSzama=" + megtekintesekSzama +
                ", kategoria=" + kategoria +
                ", leiras=" + leiras +
                ", kedvelesekSzama=" + kedvelesekSzama +
                ", stillKep=" + stillKep +
                ", feltoltesDatuma=" + feltoltesDatuma +
                ", fajlNeve=" + fajlNeve +
                ", feltoltoNeve=" + feltoltoNeve +
                '}';
    }


}



