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

/**
 * Main menu controller class
 *
 * @author ShchMax
 */
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

    private final Player player;

    /**
     * Constructor, which creates player (crutch)
     *
     * @throws IOException It can be thrown, when one of paths to player is incorrect
     * @throws InterruptedException It can be thrown, when java can't wait for end of ffprobe
     */
    public Menu() throws IOException, InterruptedException {
            audioObservableList = FXCollections.observableArrayList();

            String[] paths = {"/Users/admin/Documents/IF/ReFlaction/src/main/resources/com/shchmax/Samples/Undertale"};

            player = new Player(paths);

            audioObservableList.addAll(player.getList());
    }

    /**
     * Initializer
     *
     * @param location I don't know
     * @param resources I don't know
     */
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

    /**
     * Play button method
     *
     * @param e ActionEvent
     */
    public void play(ActionEvent e) {
        player.play();
    }

    /**
     * Stop button method
     *
     * @param e ActionEvent
     */
    public void stop(ActionEvent e) {
        player.stop();
    }

    /**
     * Next button method
     *
     * @param e ActionEvent
     */
    public void next(ActionEvent e) {
        player.next();
    }

    /**
     * Prev button method
     *
     * @param e ActionEvent
     */
    public void prev(ActionEvent e) {
        player.prev();
    }
}
