package com.example.hp.musicapp;

/**
 * Created by Lincoln on 18/05/16.
 */
public class Main_Bean {
    private String name;
    private int numOfSongs;
    private int thumbnail;
    private String trackId;

    public Main_Bean() {
    }


    public Main_Bean(String name, int numOfSongs, int thumbnail, String trackId) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
}