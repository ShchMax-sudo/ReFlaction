package com.shchmax.audio;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.*;
import java.util.StringTokenizer;

public class Audio {
    private final String audioURL;
    private final String coverURL;
    private FFmpegProbeResult probe;
    private String genre;
    private String artist;
    private String album;

    public Audio(String path, int imageNum) throws IOException, InterruptedException {
        audioURL = path;
        Process imageCreating = Runtime.getRuntime().exec("ffmpeg -i " + path + " /tmp/ReFlaction/Covers/" + imageNum + ".png");
        imageCreating.waitFor();
        File coverFile = new File("/tmp/ReFlaction/Covers/" + imageNum + ".png");
        if(coverFile.exists()) {
            coverURL = coverFile.getAbsolutePath();
        } else {
            coverURL = null;
        }
        probe = new FFprobe("/usr/local/bin/ffprobe").probe(audioURL);
        artist = probe.format.tags.get("artist");
        genre = probe.format.tags.get("genre");
        album = probe.format.tags.get("album");
    }

    static public boolean isAudio(String file) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("ffmpeg", "-i", file);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        process.waitFor();
        InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringTokenizer in = null;
        boolean f = false;
        while (br.ready()) {
            while (in == null || !in.hasMoreTokens()) {
                in = new StringTokenizer(br.readLine());
            }
            String titl = in.nextToken();
            if (titl.contains("Audio")) {
                f = true;
            }
        }
        return f;
    }

    public String path() {
        return audioURL;
    }
}
