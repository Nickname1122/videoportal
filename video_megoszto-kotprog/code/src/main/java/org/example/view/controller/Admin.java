package org.example.view.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.example.controller.FelhasznaloController;
import org.example.controller.VideoController;
import org.example.model.Felhasznalo;
import org.example.model.Video;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Admin implements Initializable {

    @FXML
    private TableView<Felhasznalo> adminUser;
    @FXML
    private TableColumn<Felhasznalo, String> felhasznaloFelhasznalonevTabelC;
    @FXML
    private TableColumn<Felhasznalo, String> felhasznaloEmailTabelC;
    @FXML
    private TableColumn<Felhasznalo, String> felhasznaloJelszoTabelC;
    @FXML
    private TableColumn<Felhasznalo, Void> felhasznaloMuveletekTabelC;
    @FXML
    private TableView<Video> adminVideo;
    @FXML
    private TableColumn<Video, String> videoVideoTabelC;
    @FXML
    private TableColumn<Video, String> videoCimTabelC;
    @FXML
    private TableColumn<Video, String> videoLeirasTabelC;
    @FXML
    private TableColumn<Video, String> videoFeltoltoTabelC;
    @FXML
    private TableColumn<Video, Void> videoMuveletekTabelC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Felhasznalo> users = FelhasznaloController.getInstance().allUser();
        List<Video> videos = VideoController.getInstance().allVideo();

        adminUser.setItems(FXCollections.observableList(users));
        adminVideo.setItems(FXCollections.observableList(videos));

        felhasznaloFelhasznalonevTabelC.setCellValueFactory(new PropertyValueFactory<>("felhasznalonev"));
        felhasznaloEmailTabelC.setCellValueFactory(new PropertyValueFactory<>("email"));
        felhasznaloJelszoTabelC.setCellValueFactory(new PropertyValueFactory<>("jelszo"));
        felhasznaloMuveletekTabelC.setCellFactory(actions -> new TableCell<>() {
            private final Button deleteButton = new Button("Törlés");
            private final Button banButton = new Button("Tiltás");

            {
                deleteButton.setOnAction(event -> {
                    Felhasznalo felhasznalo = getTableView().getItems().get(getIndex());
                    deleteFelhasznalo(felhasznalo.getFelhasznalonev());
                });

                banButton.setOnAction(event -> {
                    Felhasznalo felhasznalo = getTableView().getItems().get(getIndex());
                    banFelhasznalo(felhasznalo.getFelhasznalonev());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(deleteButton, banButton);
                    setGraphic(container);
                }
            }

        });

        videoVideoTabelC.setCellValueFactory(new PropertyValueFactory<>("stillKep"));
        videoCimTabelC.setCellValueFactory(new PropertyValueFactory<>("cim"));
        videoLeirasTabelC.setCellValueFactory(new PropertyValueFactory<>("leiras"));
        videoFeltoltoTabelC.setCellValueFactory(new PropertyValueFactory<>("feltoltoNeve"));
        videoMuveletekTabelC.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Törlés");
            private final Button banButton = new Button("Tiltás");

            {
                deleteButton.setOnAction(event -> {
                    Video video = getTableView().getItems().get(getIndex());
                    deleteVideo(video.getVideoID());
                });

                banButton.setOnAction(event -> {
                    Video video = getTableView().getItems().get(getIndex());
                    banVideo(video.getVideoID());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(deleteButton, banButton);
                    setGraphic(container);
                }
            }
        });

    }


    private void banFelhasznalo(String username) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztos, hogy tiltod a felhasználót: '" + username + "'",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                FelhasznaloController.getInstance().banUser(username);
            }
        });
    }


    private void deleteFelhasznalo(String username) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztos, hogy törlöd a felhasználót: '" + username + "'",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                FelhasznaloController.getInstance().deleteUser(username);
            }
        });
    }


    private void banVideo(int videoID) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztos, hogy tiltod a videót? ''",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                VideoController.getInstance().banVideo(videoID);
            }
        });
    }


    private void deleteVideo(int videoID) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztos, hogy törlöd a videót? ''",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                VideoController.getInstance().deleteVideo(videoID);
            }
        });
    }
}
