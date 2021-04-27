package com.shchmax.app;

import com.shchmax.audio.Audio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Sound extends ListCell<Audio> {
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    public Sound() {
        setStyle("-fx-padding: 0px");
    }

    @Override
    protected void updateItem(Audio audio, boolean empty) {
        super.updateItem(audio, empty);
        if (empty || audio == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("../fxml/sound.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            label1.setText("Shit");
            label2.setText("Sheet");
            setText(null);
            setGraphic(gridPane);
        }
    }
}