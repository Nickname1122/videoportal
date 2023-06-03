package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.FelhasznaloController;

import java.io.IOException;

public class App extends Application {
    private FelhasznaloController contorl = new FelhasznaloController();

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/view/login.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
            stage.setTitle("OurTube");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("[LAUNCH] " + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        contorl.logoutUser();
    }
}
