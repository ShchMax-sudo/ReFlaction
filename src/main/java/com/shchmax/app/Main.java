package com.shchmax.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.StringTokenizer;

import javafx.application.Application;

public class Main extends Application {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer in = null;

    public static String nextToken() throws IOException {
        while (in == null || !in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }
        return in.nextToken();
    }

    public static void createDir(String path) {
        File theDir = new File(path);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }

    public static void deleteDir(String path) {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException ignored) {}
    }

    public static void templateInitialize() {
        createDir("/tmp/ReFlaction");
        deleteDir("/tmp/ReFlaction/Covers");
        createDir("/tmp/ReFlaction/Covers");
    }

    static public void main(String[] args) {
        templateInitialize();
        Application.launch(args);
    }

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
