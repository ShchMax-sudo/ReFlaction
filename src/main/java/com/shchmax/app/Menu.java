package com.shchmax.app;

import com.shchmax.audio.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.shchmax.audio.Audio;

public class Menu implements Initializable {
    @FXML
    private SplitPane horSplit;
    @FXML
    private SplitPane vertSplit;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private AnchorPane upPane;
    @FXML
    private AnchorPane downPane;
    @FXML
    private ListView songList;
    @FXML
    private Button playButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;

    private ObservableList<Audio> audioObservableList;

    private Player player;

    public Menu() throws IOException, InterruptedException {
            audioObservableList = FXCollections.observableArrayList();

            String[] paths = {"/Users/admin/Documents/IF/ReFlaction/src/main/resources/com/shchmax/Samples/"};

            player = new Player(paths);

            audioObservableList.addAll(player.getList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftPane.maxWidthProperty().bind(horSplit.widthProperty().multiply(horSplit.getDividerPositions()[0]));
        leftPane.minWidthProperty().bind(horSplit.widthProperty().multiply(horSplit.getDividerPositions()[0]));
        upPane.maxHeightProperty().bind(vertSplit.heightProperty().multiply(vertSplit.getDividerPositions()[0]));
        upPane.minHeightProperty().bind(vertSplit.heightProperty().multiply(vertSplit.getDividerPositions()[0]));

        songList.setItems(audioObservableList);
        songList.setCellFactory(studentListView -> new Sound());

        MultipleSelectionModel<String> selectionModel = songList.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void play(ActionEvent e) {
        player.play();
    }

    public void stop(ActionEvent e) {
        player.stop();
    }

    public void next(ActionEvent e) {
        player.next();
    }

    public void prev(ActionEvent e) {
        player.prev();
    }
}
