package com.shchmax.audio;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private Process audioProcess;
    private ArrayList<Audio> audios;
    private int now = 0;
    private boolean isPlaying = false;

    public static void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
        }
    }

    public Player(String[] dirs) throws IOException, InterruptedException {
        ArrayList<File> audioList = new ArrayList<File>();
        for (String dir : dirs) {
            listf(dir, audioList);
        }
        audios = new ArrayList<Audio>();
        for (File file : audioList) {
            if (Audio.isAudio(file.getAbsolutePath())) {
                audios.add(new Audio(file.getAbsolutePath()));
            }
        }
    }

    public void absolutelyPlay() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("ffplay", "-nodisp", "-autoexit", audios.get(now).getPath());
        audioProcess = builder.start();
        isPlaying = true;
        notifier("ReFlaction", "Now playing " + audios.get(now).getName());
        while (isPlaying && audioProcess.isAlive()) {}
        if (isPlaying) {
            stop();
            now = (now + 1) % audios.size();
        }
    }

    public void play() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    absolutelyPlay();
                } catch (IOException err) {
                    err.printStackTrace();
                    System.out.println(err);
                }
            }
        }).start();
    }

    public void stop() {
        isPlaying = false;
        audioProcess.destroy();
    }

    public void next() {
        stop();
        now = (now + 1) % audios.size();
        play();
    }

    public void prev() {
        stop();
        now = (now + audios.size() - 1) % audios.size();
        play();
    }

    public void update(ObservableList<Audio> a) {
        audios.clear();
        for (Audio p : a) {
            audios.add(p);
        }
    }

    public ArrayList<Audio> getList() {
        return this.audios;
    }

    private static void notifier(String pTitle, String pMessage) {
        Platform.runLater(() -> {
                    Stage owner = new Stage(StageStyle.TRANSPARENT);
                    StackPane root = new StackPane();
                    root.setStyle("-fx-background-color: TRANSPARENT");
                    Scene scene = new Scene(root, 1, 1);
                    scene.setFill(Color.TRANSPARENT);
                    owner.setScene(scene);
                    owner.setWidth(1);
                    owner.setHeight(1);
                    owner.toBack();
                    owner.show();
                    Notifications.create().title(pTitle).text(pMessage).showInformation();
                }
        );
    }
}
