package com.shchmax.audio;

import com.shchmax.audio.Audio;

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
                audios.add(new Audio(file.getAbsolutePath(), audios.size()));
            }
        }
    }

    public void absolutelyPlay() throws IOException {
        audioProcess = Runtime.getRuntime().exec("ffplay -nodisp -autoexit " + audios.get(now).path());
        isPlaying = true;
        System.out.println("Now playing " + audios.get(now).path());
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
}
