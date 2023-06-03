package org.example.view.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.controller.ListaController;
import org.example.controller.VideoController;
import org.example.model.LejatszasiLista;
import org.example.model.Video;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddLista implements Initializable {

    @FXML
    public ChoiceBox<String> choicer;
    @FXML
    private Button cancelButton;
    @FXML
    private  Button hozzaadButton;
    @FXML
    private Button letrehozButton;

    @FXML
    private TextField playlistnameTextField;

    @FXML
    private TextField addToListTextField;

    @FXML
    private TextField listUsernameField;

    @FXML
    private ChoiceBox<String> idChoiceBox;

    @FXML
    public void addToList() {
        LejatszasiLista lejatszasiLista = new LejatszasiLista();
        String title = choicer.getSelectionModel().getSelectedItem();

        int videoID;


        VideoController controller = new VideoController();
        videoID = controller.getIdFromTitle(title);
        System.out.println(videoID);
        lejatszasiLista.setVideoID(videoID);
        lejatszasiLista.setFelhasznalonev(listUsernameField.getText());
        lejatszasiLista.setListaCime(playlistnameTextField.getText());



        ListaController.getInstance().addList(lejatszasiLista);

    }


    @FXML
    public void newplaylist() {

        LejatszasiLista lejatszasiLista = new LejatszasiLista();
        String title = idChoiceBox.getSelectionModel().getSelectedItem();
        List<Video> videos = VideoController.getInstance().allVideo();
        int i = -1;
        int videoID;

        VideoController controller = new VideoController();
        videoID = controller.getIdFromTitle(title);
        System.out.println(videoID);
        lejatszasiLista.setVideoID(videoID);
        lejatszasiLista.setFelhasznalonev(listUsernameField.getText());
        lejatszasiLista.setListaCime(playlistnameTextField.getText());


        ListaController.getInstance().addList(lejatszasiLista);

    }


    @FXML
    private void backToHomePage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Video> videos = VideoController.getInstance().allVideo();
        List<String> titles = new ArrayList<>();

        for (Video video : videos) {
            String title = video.getCim();
            titles.add(title);
        }

        idChoiceBox.setItems(FXCollections.observableList(titles));
        choicer.setItems(FXCollections.observableList(titles));

        cancelButton.setStyle("-fx-background-color: #FF7F50; -fx-text-fill: #FFFFFF;");
        letrehozButton.setStyle("-fx-background-color: #FF7F50; -fx-text-fill: #FFFFFF;");
        hozzaadButton.setStyle("-fx-background-color: #FF7F50; -fx-text-fill: #FFFFFF;");

    }
}