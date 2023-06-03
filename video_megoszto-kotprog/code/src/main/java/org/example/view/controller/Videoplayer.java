package org.example.view.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.App;
import org.example.controller.AjanlasController;
import org.example.controller.KommentController;
import org.example.controller.VideoController;
import org.example.model.Komment;
import org.example.model.Video;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Videoplayer implements Initializable {

    private Video video = new Video();

    @FXML
    private MediaView videoMediaPlayer;
    @FXML
    private TableView<Video> ajanlottVideoTableView;
    @FXML
    private TableColumn<Video, String> ajanlottVideoTableColumn;
    @FXML
    private TableView<Komment> osszesKommentTableView;
    @FXML
    private TableColumn<Komment, String> kommenteloTableColumn;
    @FXML
    private TableColumn<Komment, String> kommentTartalomTableColumn;
    @FXML
    private Button homepageButton;
    @FXML
    private TextField videoSearchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button likeButton;
    @FXML
    private Label videoCimLabel;
    @FXML
    private Label likeCountLabel;
    @FXML
    private TextArea leirasTextArea;
    @FXML
    private Button kommentSubmitButton;
    @FXML
    private TextField kommentUsernameTextField;
    @FXML
    private TextField kommentTextfield;
    @FXML
    private Label uploaderLabel;
    @FXML
    private Label viewCount;

    private MediaPlayer mp;
    private Media me;


    @FXML
    public void refreshTable() {
        List<Komment> comments = KommentController.getInstance().getKomment(video.getVideoID());
        osszesKommentTableView.setItems(FXCollections.observableList(comments));
    }


    @FXML
    public void sendKomment() {

        Komment komment = new Komment();

        komment.setVideoID(video.getVideoID());

        if (!(kommentTextfield.getText().isEmpty()) && !(kommentUsernameTextField.getText().isEmpty())) {
            komment.setKommentTartalom(kommentTextfield.getText());
            komment.setFelhasznalonev(kommentUsernameTextField.getText());
        }

        KommentController.getInstance().addKomment(komment);

        kommentTextfield.clear();
        kommentUsernameTextField.clear();

    }


    @FXML
    public void backToHomepage() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/view/felulet.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.getStylesheets().add(App.class.getResource("/org/example/view/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("OurTube");
            stage.show();
        } catch (IOException e) {
            System.out.println("[BACK TO HOMEPAGE] " + e.toString());
        }

        Stage stage = (Stage) homepageButton.getScene().getWindow();
        stage.close();

    }


    @FXML
    public void search() {
        Video video = new Video();
        if (!(videoSearchTextField.getText().isEmpty()) && !(kommentUsernameTextField.getText().isEmpty())) {
            video.setCim(videoSearchTextField.getText());
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/view/felulet.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.getStylesheets().add(App.class.getResource("/org/example/view/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("OurTube");
            stage.show();
        } catch (IOException e) {
            System.out.println("[BACK TO HOMEPAGE WITH SEARCH PART 1] " + e.toString());
        }
        Stage stage = (Stage) homepageButton.getScene().getWindow();
        stage.close();

    }


    @FXML
    public void like() {
        int kedveles = Integer.parseInt(likeCountLabel.getText());

        kedveles++;

        likeCountLabel.setText(String.valueOf(kedveles));

        VideoController.getInstance().updateLikes(video.getVideoID(), Integer.parseInt(likeCountLabel.getText()));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Komment> comments = KommentController.getInstance().getKomment(video.getVideoID() + 1);
        osszesKommentTableView.setItems(FXCollections.observableList(comments));

        kommenteloTableColumn.setCellValueFactory(new PropertyValueFactory<>("felhasznalonev"));
        kommentTartalomTableColumn.setCellValueFactory(new PropertyValueFactory<>("kommentTartalom"));

        video.cimProperty().bindBidirectional(videoCimLabel.textProperty());
        video.kedvelesekSzamaProperty().bindBidirectional(likeCountLabel.textProperty());
        video.leirasProperty().bindBidirectional(leirasTextArea.textProperty());
        video.feltoltoNeveProperty().bindBidirectional(uploaderLabel.textProperty());
        video.megtekintesekSzamaProperty().bindBidirectional(viewCount.textProperty());


        List<Video> ajanlatok = AjanlasController.getInstance().ajanlas();
        ajanlottVideoTableView.setItems(FXCollections.observableList(ajanlatok));

        ajanlottVideoTableColumn.setCellValueFactory(new PropertyValueFactory<>("cim"));

        kommentSubmitButton.disableProperty().bind(kommentTextfield.textProperty().isEmpty().or(kommentUsernameTextField.textProperty().isEmpty()));

    }


    public void mukodj(String mukodj) {
        File file = new File("videok/" + mukodj + ".mp4");
        System.out.println(file.toURI().toString());
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        videoMediaPlayer.setFitHeight(480);
        videoMediaPlayer.setFitWidth(888);
        mediaPlayer.setAutoPlay(true);
        videoMediaPlayer.setMediaPlayer(mediaPlayer);
    }


    public void initVideo(Video video) {
        video.copyTo(this.video);
    }


}