package com.shchmax.audio;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.util.ArrayList;

/**
 * Player class
 *
 * @author ShchMax
 */
public class Player {
    private Process audioProcess;
    private ArrayList<Audio> audios;
    private int now = 0;
    private boolean isPlaying = false;
    private long deltaT = 0;
    private long lastCP = 0;

    /**
     * This method pushes all files from directory recursively
     *
     * @param directoryName Path to directory
     * @param files ArrayList of files
     */
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

    /**
     * Constructor, which creates a player from array of paths
     *
     * @param dirs Array of path to directories
     * @throws IOException It can be thrown, when directory now found
     * @throws InterruptedException It can be thrown, when java can't wait for end of ffprobe
     */
    public Player(String[] dirs) throws IOException, InterruptedException {
        ArrayList<File> audioList = new ArrayList<>();
        for (String dir : dirs) {
            listf(dir, audioList);
        }
        audios = new ArrayList<>();
        for (File file : audioList) {
            if (Audio.isAudio(file.getAbsolutePath())) {
                audios.add(new Audio(file.getAbsolutePath()));
            }
        }
    }

    /**
     * This method just play an Audio
     *
     * @return Is player stopped manually
     * @throws IOException It can be thrown, when Audio path is incorrect
     */
    public boolean absolutelyPlay() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("ffplay", "-stats", "-nodisp", "-autoexit", audios.get(now).getPath());
        audioProcess = builder.start();
        isPlaying = true;
        notifier("ReFlaction", "Now playing " + audios.get(now).getName());
        try {
            audioProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isPlaying) {
            breakPlay();
            now = (now + 1) % audios.size();
            return true;
        }
        return false;
    }

    /**
     * This method plays in new thread
     */
    public void play() {
        new Thread(() -> {
            do {
                try {
                    absolutelyPlay();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (isPlaying);
        }).start();
    }

    /**
     * This method stop player manually
     */
    public void stop() {
        isPlaying = false;
        breakPlay();
    }

    /**
     * Next composition method
     */
    public void next() {
        stop();
        now = (now + 1) % audios.size();
        play();
    }

    /**
     * Previous composition method
     */
    public void prev() {
        stop();
        now = (now + audios.size() - 1) % audios.size();
        play();
    }

    /**
     * This method only stop playing
     */
    private void breakPlay() {
        audioProcess.destroy();
    }

    /**
     * This method updates playlist
     *
     * @param a ObservableList of Audios
     */
    public void update(ObservableList<Audio> a) {
        audios.clear();
        audios.addAll(a);
    }

    /**
     * This method returns playlist of Audios
     *
     * @return Playlist of Audios
     */
    public ArrayList<Audio> getList() {
        return this.audios;
    }

    /**
     * This method creates simple notification
     *
     * @param pTitle Title f notification
     * @param pMessage Message of notification
     */
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
