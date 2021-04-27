package com.shchmax.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public SplitPane horSplit;
    public SplitPane vertSplit;
    public AnchorPane leftPane;
    public AnchorPane rightPane;
    public AnchorPane upPane;
    public AnchorPane downPane;
    public ListView songList;

    private ObservableList<Audio> audioObservableList;

    public Menu() throws IOException, InterruptedException {
            audioObservableList = FXCollections.observableArrayList();

            audioObservableList.addAll(
                    new Audio("/Users/admin/Documents/IF/ReFlaction/src/main/resources/com/shchmax/Samples/051_Another_Medium.flac"),
                    new Audio("/Users/admin/Documents/IF/ReFlaction/src/main/resources/com/shchmax/Samples/051_Another_Medium.flac")
            );
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
}
