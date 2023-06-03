package org.example.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.controller.FelhasznaloController;
import org.example.model.Felhasznalo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    private Felhasznalo felhasznalo = new Felhasznalo();

    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registrationButton;
    @FXML
    private Label loginErrorLabel;

    public Login() {
    }


    @FXML
    public void submit() {

        felhasznalo.setFelhasznalonev(usernameTextField.getText());
        felhasznalo.setJelszo(String.valueOf(passwordField.getText()));

        if (FelhasznaloController.getInstance().loginUser(felhasznalo.getFelhasznalonev(), felhasznalo.getJelszo())) {
            if (FelhasznaloController.getInstance().isAdmin()) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/org/example/view/admin.fxml"));
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(App.class.getResource("/org/example/view/style.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println("[LOGIN BUTTON] " + e.toString());
//                    e.printStackTrace();
                }
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/org/example/view/felulet.fxml"));
                    Parent root = loader.load();
                    HomePage controller = loader.getController();
                    controller.initFelhasznalo(FelhasznaloController.getInstance().getData(usernameTextField.getText()));
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(App.class.getResource("/org/example/view/style.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
//                    System.out.println("[LOGIN BUTTON] " + e.toString());
                    e.printStackTrace();
                }
            }
        }


    }


    @FXML
    public void openRegistrationWindow() {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/org/example/view/regisztráció.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(App.class.getResource("/org/example/view/style.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            System.out.println("[REGISTRATION BUTTON] " + e.toString());
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
