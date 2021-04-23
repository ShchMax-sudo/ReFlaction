package com.shchmax.app;

import com.shchmax.audio.Player;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
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
        } catch (IOException e) {}
    }

    public static void templateInitialize() {
        createDir("/tmp/ReFlaction");
        deleteDir("/tmp/ReFlaction/Covers");
        createDir("/tmp/ReFlaction/Covers");
    }

    static public void main(String[] args) throws IOException, InterruptedException {
        templateInitialize();
        String[] paths = new String[1];
        paths[0] = "/Users/admin/Documents/IF/ReFlaction/src/main/java/com/shchmax/Samples";
        Player pl = new Player(paths);
        System.out.println("Player ready");
        while (true) {
            String p = nextToken();
            switch (p) {
                case ("play"):
                    pl.play();
                    break;
                case ("stop"):
                    pl.stop();
                    break;
                case ("next"):
                    pl.next();
                    break;
                case ("prev"):
                    pl.prev();
                    break;
                default:
                    break;
            }
        }
    }
}
