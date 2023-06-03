package org.example.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Utils.Utils;
import org.example.controller.VideoController;
import org.example.model.Video;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


public class AddVideo implements Initializable {


    Video video = new Video();

    ObservableList<String> stillKepList =
            FXCollections.observableArrayList();

    @FXML
    Label nameLabel;

    @FXML
    Label catLabel;

    @FXML
    Label descLabel;

    @FXML
    Label filenameLabel;

    @FXML
    Label dateLabel;

    @FXML
    Label stillLabel;

    @FXML
    Label uploaderLabel;

    @FXML
    Label still_pic;

    @FXML
    TextField nameField;

    @FXML
    TextField catField;

    @FXML
    TextField descField;

    @FXML
    TextField uploaderField;

    @FXML
    DatePicker datePicker;

    @FXML
    Button canceluploadBtn;

    @FXML
    Button insertBtn;


    @FXML
    ChoiceBox <String> stillpicCBox;

    @FXML
    public ChoiceBox<String> choiceFajl;


    @FXML
    private void savevideo() {



        Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (!(nameField.getText().isEmpty()) && !(catField.getText().isEmpty()) && !(descField.getText().isEmpty())
                && !(uploaderField.getText().isEmpty())) {

            video.setCim(nameField.getText());
            video.setKategoria(catField.getText());
            video.setLeiras(descField.getText());
            video.setFeltoltesDatuma(String.valueOf(date));
            video.setStillKep(stillpicCBox.getValue());
            String title = choiceFajl.getSelectionModel().getSelectedItem();
            video.setFajlNeve(title);
            video.setFeltoltoNeve(uploaderField.getText());




            VideoController.getInstance().addVideo(video);
            System.out.println("Sikeres feltoltes");

            nameField.clear();
            catField.clear();
            descField.clear();
            uploaderField.clear();

            Stage stage = (Stage) insertBtn.getScene().getWindow();
            stage.close();

        } else {
            Utils.showWarning("Minden mezőt ki kell tölteni!");
        }
    }


    @FXML
    public void cancelupload() {
        Stage stage = (Stage) canceluploadBtn.getScene().getWindow();
        stage.close();
    }

    private void loadData(){
        stillKepList.removeAll(stillKepList);
        String a="cica";
        String b="cica1";
        String c= "cica2";
        String d = "cica3";
        String e= "bagoly";
        String f= "kutyaa";
        String g = "kutya2";
        String h = "papagaj";
        String i = "zsiraf";
        String j = "vizilo";
        String k = "szurikata";
        String l = "pingvinek";
        String m = "suni";
        String n = "gyik";
        String o = "monkey";

        stillKepList.addAll(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o);
        stillpicCBox.getItems().addAll(stillKepList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        insertBtn.setStyle("-fx-background-color: #FF7F50; -fx-text-fill: #FFFFFF;");
        canceluploadBtn.setStyle("-fx-background-color: #FF7F50; -fx-text-fill: #FFFFFF;");


        choiceFajl.getItems().add("cat");
        choiceFajl.getItems().add("cat2");
        choiceFajl.getItems().add("cat3");
        choiceFajl.getItems().add("cat4");
        choiceFajl.getItems().add("dog1");
        choiceFajl.getItems().add("dog2");
        choiceFajl.getItems().add("owl");
        choiceFajl.getItems().add("parrotandcat");
        choiceFajl.getItems().add("penguin");
        choiceFajl.getItems().add("scary");
        choiceFajl.getItems().add("suni");
        choiceFajl.getItems().add("szurikata");
        choiceFajl.getItems().add("zsiraf");
        choiceFajl.getItems().add("hippo");
        choiceFajl.getItems().add("monkey");




        loadData();


    }
}