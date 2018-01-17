package com.example.hp.musicapp;

/**
 * Created by hp on 1/8/2018.
 */

public class SongBean {


    private String  songname;
    private String trackId;

    public SongBean() {
    }
    public SongBean(String songname,String trackId) {
        this.songname = songname;
        this.trackId=trackId;
    }
    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }


    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }




}
