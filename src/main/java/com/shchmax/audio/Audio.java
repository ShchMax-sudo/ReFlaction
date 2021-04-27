package com.shchmax.audio;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.*;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

public class Audio {
    private final String audioURL;
    private final String coverURL;
    private FFmpegProbeResult probe;
    private String name = null;
    private String genre = null;
    private String artist = null;
    private String album = null;

    private static int covernum = 0;

    public Audio(String path) throws IOException, InterruptedException {
        audioURL = path;
        Process imageCreating = Runtime.getRuntime().exec("ffmpeg -i " + path + " /tmp/ReFlaction/Covers/" + covernum + ".png");
        imageCreating.waitFor();
        File coverFile = new File("/tmp/ReFlaction/Covers/" + covernum + ".png");
        covernum++;
        if(coverFile.exists()) {
            coverURL = coverFile.getAbsolutePath();
        } else {
            coverURL = null;
        }
        probe = new FFprobe("/usr/local/bin/ffprobe").probe(audioURL);
        for (String tag : probe.format.tags.keySet()) {
            switch (tag.toLowerCase()) {
                case ("title"):
                    name = probe.format.tags.get(tag);
                    break;
                case ("album"):
                    album = probe.format.tags.get(tag);
                    break;
                case ("artist"):
                    artist = probe.format.tags.get(tag);
                    break;
                case ("genre"):
                    genre = probe.format.tags.get(tag);
                    break;
                default:
                    break;
            }
        }
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

    public String getPath() {
        return audioURL;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getSlashPath() {
        StringBuilder sb = new StringBuilder();
        for (char p : this.audioURL.toCharArray()) {
            if (p == ' ') {
                sb.append('\\');
                sb.append('\\');
            }
            sb.append(p);
        }
        return sb.toString();
    }
}
