package com.shchmax.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.StringTokenizer;

import javafx.application.Application;

/**
 * Main class
 *
 * @author ShchMax
 */
public class Main extends Application {
    static private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static private StringTokenizer in = null;

    /**
     * Method, that returns new string token from standard input stream
     *
     * @return String token from System.in
     * @throws IOException I don't know, when it throws, but it can
     */
    public static String nextToken() throws IOException {
        while (in == null || !in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }
        return in.nextToken();
    }

    /**
     * This method creates a folder
     *
     * @param path Path to folder
     */
    public static void createDir(String path) {
        File theDir = new File(path);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }

    /**
     * This method deletes a folder
     *
     * @param path Path to folder
     */
    public static void deleteDir(String path) {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException ignored) {}
    }


    /**
     * This method initialize some template folders
     */
    public static void templateInitialize() {
        createDir("/tmp/ReFlaction");
        deleteDir("/tmp/ReFlaction/Covers");
        createDir("/tmp/ReFlaction/Covers");
        deleteDir("/tmp/ReFlaction/Logs");
        createDir("/tmp/ReFlaction/Logs");
    }

    /**
     * Main java method, entry point
     *
     * @param args Java standard arguments from terminal
     */
    static public void main(String[] args) {
        templateInitialize();
        Application.launch(args);
    }

    /**
     * Method which starts a new stage
     *
     * @param stage JavaFX application stage
     * @throws Exception Exception from incorrect fxml
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menu.fxml"));
        Scene scene;
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("ReFlaction");

        stage.show();
    }
}
