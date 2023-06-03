package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Ajanlas {

    private IntegerProperty vID = new SimpleIntegerProperty();
    private IntegerProperty vCIM = new SimpleIntegerProperty();

    public Ajanlas() {
    }

    public Ajanlas(IntegerProperty vID, IntegerProperty vCIM) {
        this.vID = vID;
        this.vCIM = vCIM;
    }

    public int getvID() {
        return vID.get();
    }

    public IntegerProperty vIDProperty() {
        return vID;
    }

    public int getvCIM() {
        return vCIM.get();
    }

    public IntegerProperty vCIMProperty() {
        return vCIM;
    }

    public void setvID(int vID) {
        this.vID.set(vID);
    }

    public void setvCIM(int vCIM) {
        this.vCIM.set(vCIM);
    }
}
