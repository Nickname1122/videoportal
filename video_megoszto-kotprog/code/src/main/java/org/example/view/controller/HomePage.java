package org.example.view.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.controller.AjanlasController;
import org.example.controller.FelhasznaloController;
import org.example.controller.ListaController;
import org.example.controller.VideoController;
import org.example.model.Felhasznalo;
import org.example.model.LejatszasiLista;
import org.example.model.Video;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomePage implements Initializable {

    private static HomePage single_instance = null;

    private Felhasznalo felhasznalo = new Felhasznalo();

    @FXML
    private TitledPane mainWindow;

    @FXML
    private TableView<Video> videosTableView;

    @FXML
    private TableColumn<Video, String> videoStillPicColumn;

    @FXML
    private TableColumn<Video, String> videoTitleColumn;

    @FXML
    private TableColumn<Video, Integer> videoViewColumn;

    @FXML
    private TableColumn<Video, String> videoCategoryColumn;

    @FXML
    private TableColumn<Video, String> videoDateColumn;

    @FXML
    private TableColumn<Video, String> videoUploaderColumn;

    @FXML
    private TableColumn<Video, Void> videoElinditTabelC;

    @FXML
    private TextField videoTextF;

    @FXML
    private TableView<Video> videoKeresettTableView;

    @FXML
    private TableColumn<Video, String> videoTabelC;

    @FXML
    private TableColumn<Video, String> cimTabelC;

    @FXML
    private TableColumn<Video, String> datumTabelC;

    @FXML
    private TableColumn<Video, String> feltoltoTabelC;

    @FXML
    private TextField felhasznaloTextF;

    @FXML
    private TableView<Felhasznalo> searchedUserTableView;

    @FXML
    private TableColumn<Felhasznalo, String> felhasznalonevTabelC;

    @FXML
    private TableColumn<Felhasznalo, String> emailTabelC;

    @FXML
    private TableView<Video> recommendVideo;

    @FXML
    private TableColumn<Video, String> stillKep;

    @FXML
    private TableColumn<Video, String> cim;

    @FXML
    private TableView<Video> profilVideoTableView;

    @FXML
    private TableColumn<Video, String> profilVideoTableColumn;

    @FXML
    private TableColumn<Video, String> profilCimTableColumn;

    @FXML
    private TableView<LejatszasiLista> listatListaz;

    @FXML
    private TableColumn<LejatszasiLista, String> listaNev;

    @FXML
    private TableColumn<LejatszasiLista, Void> torles;

    @FXML
    private Label profilUsernameLabel;

    @FXML
    private Label profilBirthyearLabel;

    @FXML
    private Label profilEmailLabel;

    public HomePage() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //listták listázása
        List<LejatszasiLista> listlist = ListaController.getInstance().getList(felhasznalo.getFelhasznalonev());
        listatListaz.setItems(FXCollections.observableList(listlist));
        listaNev.setCellValueFactory(new PropertyValueFactory<>("listaCime"));


        //videók feltöltése
        List<Video> list = VideoController.getInstance().getVideo();
        videosTableView.setItems(FXCollections.observableList(list));

        videoStillPicColumn.setPrefWidth(170);
        videoStillPicColumn.setCellValueFactory(new PropertyValueFactory<>("stillView"));
        videoTitleColumn.setCellValueFactory(new PropertyValueFactory<>("cim"));
        videoViewColumn.setCellValueFactory(new PropertyValueFactory<>("megtekintesekSzama"));
        videoCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        videoDateColumn.setCellValueFactory(new PropertyValueFactory<>("feltoltesDatuma"));
        videoUploaderColumn.setCellValueFactory(new PropertyValueFactory<>("feltoltoNeve"));

        videoElinditTabelC.setCellFactory(actions -> new TableCell<>() {
            private final Button elinditButton = new Button("Elindit");

            {
                elinditButton.setOnAction(event -> {
                    Video video = getTableView().getItems().get(getIndex());
                    playVideo(video);
                    VideoController.getInstance().updateViewCount(video.getVideoID(), Integer.parseInt(video.getMegtekintesekSzama()) + 1 );

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(elinditButton);
                    setGraphic(container);
                }
            }

        });


        //ajanlott videok feltöltése
        List<Video> ajanlottak = AjanlasController.getInstance().ajanlas();
        recommendVideo.setItems(FXCollections.observableList(ajanlottak));
        cim.setCellValueFactory(new PropertyValueFactory<>("cim"));


        //profil feltöltése
        felhasznalo.felhasznalonevProperty().bindBidirectional(profilUsernameLabel.textProperty());
        felhasznalo.szuletesiDatumProperty().bindBidirectional(profilBirthyearLabel.textProperty());
        felhasznalo.emailProperty().bindBidirectional(profilEmailLabel.textProperty());

    }

    @FXML
    public void refreshTable() {
        List<Video> list = VideoController.getInstance().getVideo();
        videosTableView.setItems(FXCollections.observableList(list));
    }


    @FXML
    public void frissit() {
        List<Video> userVideos = VideoController.getInstance().getUserVideo(felhasznalo.felhasznalonevProperty().getValue());
        profilVideoTableView.setItems(FXCollections.observableList(userVideos));

        profilVideoTableColumn.setCellValueFactory(new PropertyValueFactory<>("stillView"));
        profilCimTableColumn.setCellValueFactory(new PropertyValueFactory<>("cim"));
    }


    @FXML
    public void keresVideo() {
        List<Video> list = VideoController.getInstance().getSearchVideo(videoTextF.getText());

        videoKeresettTableView.setItems(FXCollections.observableList(list));


        cimTabelC.setCellValueFactory(new PropertyValueFactory<>("cim"));
        datumTabelC.setCellValueFactory(new PropertyValueFactory<>("feltoltesDatuma"));
        feltoltoTabelC.setCellValueFactory(new PropertyValueFactory<>("feltoltoNeve"));
    }


    @FXML
    public void keresFelhasznalo() {
        List<Felhasznalo> list = FelhasznaloController.getInstance().searchUser(felhasznaloTextF.getText());

        searchedUserTableView.setItems(FXCollections.observableList(list));

        felhasznalonevTabelC.setCellValueFactory(new PropertyValueFactory<>("felhasznalonev"));
        emailTabelC.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    public static HomePage getInstance() {
        if (single_instance == null) {
            single_instance = new HomePage();
        }

        return single_instance;
    }


    private void playVideo(Video video) {
        System.out.println(video.getFajlNeve());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/example/view/videolejatszo.fxml"));
            Parent root = loader.load();
            Videoplayer controller = loader.getController();
            controller.initVideo(video);
            controller.mukodj(video.getFajlNeve());

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(App.class.getResource("/org/example/view/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("OurTube");
            stage.showAndWait();

        } catch (IOException e) {
            System.out.println("[VIDEOPLAYER] " + e.toString());
            e.printStackTrace();
        }


    }


    @FXML
    public void frissites() {

        List<LejatszasiLista> listlist = ListaController.getInstance().getList(felhasznalo.felhasznalonevProperty().getValue());
        listatListaz.setItems(FXCollections.observableList(listlist));

        listaNev.setCellValueFactory(new PropertyValueFactory<>("listaCime"));

    }


    @FXML
    public void insertVideo() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/view/add_video.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void insertLista() {
        Parent lista;
        try {
            lista = FXMLLoader.load(getClass().getResource("/org/example/view/add_lista.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(lista);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initFelhasznalo(Felhasznalo felhasznalo) {
        felhasznalo.copyTo(this.felhasznalo);
    }
}
