package com.example.gnoy.yar;

import android.view.View;

import java.io.Serializable;

public class MusicDto implements Serializable {
    private String id;
    private String title;
    private String artist;
    private String message;
    private long timestamp;

    public View.OnClickListener MessageClickListener;
    public View.OnClickListener DeleteClickListener;

    public MusicDto() {
    }

    public MusicDto(String id, String title, String artist, String message) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return timestamp;
    }

    public void setTime(long time) {
        this.timestamp = time;
    }
}