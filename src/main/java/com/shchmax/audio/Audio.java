package com.shchmax.audio;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Audio class
 *
 * @author ShchMax
 */
public class Audio {
    private final String audioURL;
    private final String coverURL;
    private FFmpegProbeResult probe;
    private String name = null;
    private String genre = null;
    private String artist = null;
    private String album = null;

    private static int covernum = 0;

    /**
     * Constructor, which creates new Audio
     *
     * @param path Path to audio
     * @throws IOException Can be thrown, when path is incorrect
     * @throws InterruptedException It can be thrown, when java can't wait for end of ffprobe
     */
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
        if (probe.format.tags != null) {
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
    }

    /**
     * This method checks, is this file - audio file
     *
     * @param file path to file
     * @return Is this file an audio
     * @throws IOException It can be thrown, when path is incorrect
     * @throws InterruptedException It can be thrown, when java can't wait for end of ffmpeg
     */
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

    /**
     * @return Path
     */
    public String getPath() {
        return audioURL;
    }

    /**
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @return Path with slashes
     */
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

    /**
     * @return Duration
     */
    public long getDuration() {
        return (long) (probe.format.duration * 1000);
    }
}
