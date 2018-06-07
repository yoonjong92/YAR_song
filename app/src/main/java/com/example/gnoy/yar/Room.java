package com.example.gnoy.yar;

import android.view.View;

public class Room {
    String roomName;
    String roomIntro;
    String roomID;

    public View.OnClickListener DeleteClickListener;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomName = roomName;
    }

    public String getRoomIntro() {
        return roomIntro;
    }

    public void setRoomIntro(String roomIntro) {
        this.roomIntro = roomIntro;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Room(String roomLocation, String roomTitle) {
        this.roomName = roomLocation;
        this.roomIntro = roomTitle;
    }

    public Room(String roomName, String roomIntro, String roomID) {
        this.roomName = roomName;
        this.roomIntro = roomIntro;
        this.roomID = roomID;
    }
}
