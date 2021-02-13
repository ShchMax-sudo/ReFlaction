package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import sample.audio.Flac;

public class Main extends Application {
    @FXML
    public Button settings_button;

    Flac det;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxmls/Main.fxml"));
        primaryStage.setTitle("ReFlaction");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
        det = new Flac(new File("/Users/shchmax/Document/ReFlaction/src/sample/samples/test.wav"));
        det.play(true);
    }

    public void stop_playing(ActionEvent actionEvent) {
        det.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
