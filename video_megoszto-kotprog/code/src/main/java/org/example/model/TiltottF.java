package org.example.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TiltottF extends Felhasznalo {

    private BooleanProperty tiltottFelhasznalo = new SimpleBooleanProperty();

    public TiltottF() {
    }

    public TiltottF(boolean tiltottFelhasznalo) {
        this.tiltottFelhasznalo.set(tiltottFelhasznalo);
    }

    public TiltottF(String felhasznalonev, boolean tiltottFelhasznalo) {
        super(felhasznalonev);
        this.tiltottFelhasznalo.set(tiltottFelhasznalo);
    }

    public boolean isTiltottFelhasznalo() {
        return tiltottFelhasznalo.get();
    }

}
