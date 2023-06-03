package org.example.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.controller.FelhasznaloController;
import org.example.model.Felhasznalo;

import java.time.ZoneId;
import java.util.Date;

public class Registration {

    private Felhasznalo felhasznalo = new Felhasznalo();

    @FXML
    private Label regUsernameLabel;
    @FXML
    private Label regPasswordLabel;
    @FXML
    private Label regConfirmPasswordLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label szuletesiDatum;
    @FXML
    private Button regisztraciosgomb;
    @FXML
    private DatePicker szuldatum;
    @FXML
    private PasswordField jelszo;
    @FXML
    private PasswordField jelszomegerosit;
    @FXML
    private RadioButton adatkezelesradiogomb;
    @FXML
    private TextField email;
    @FXML
    private TextField felhasznalonev;

    @FXML
    public void register() {


        Date date = Date.from(szuldatum.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (!(felhasznalonev.getText().isEmpty())) {
            felhasznalo.setFelhasznalonev(felhasznalonev.getText());
            //if (FelhasznaloController.getInstance().usedUsername(felhasznalo.getFelhasznalonev())) {
            if (jelszo.getText().equals(jelszomegerosit.getText()) && !(jelszo.getText().isEmpty())) {
                felhasznalo.setJelszo(jelszo.getText());
            }

            felhasznalo.setSzuletesiDatum(String.valueOf(date));

            felhasznalo.setEmail(email.getText());

            //}

        }

        if (adatkezelesradiogomb.isSelected()) {
            FelhasznaloController.getInstance().addUser(felhasznalo);

            felhasznalonev.clear();
            jelszo.clear();
            jelszomegerosit.clear();
            email.clear();

            Stage stage = (Stage) regisztraciosgomb.getScene().getWindow();
            stage.close();


        }


    }

}
