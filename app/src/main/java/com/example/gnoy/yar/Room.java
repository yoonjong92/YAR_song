package com.example.gnoy.yar;

import android.view.View;

public class Room {
    String roomLocation;
    String roomTitle;
    String roomID;

    public View.OnClickListener DeleteClickListener;

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Room(String roomLocation, String roomTitle) {
        this.roomLocation = roomLocation;
        this.roomTitle = roomTitle;
    }

    public Room(String roomLocation, String roomTitle, String roomID) {
        this.roomLocation = roomLocation;
        this.roomTitle = roomTitle;
        this.roomID = roomID;
    }
}
