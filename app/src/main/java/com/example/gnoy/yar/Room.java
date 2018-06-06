package com.example.gnoy.yar;

public class Room {
    String roomLocation;
    String roomTitle;
    String roomHeadcount;
    String roomID;

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

    public String getRoomHeadcount() {
        return roomHeadcount;
    }

    public void setRoomHeadcount(String roomHeadcount) {
        this.roomHeadcount = roomHeadcount;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomHeadcount = roomID;
    }

    public Room(String roomLocation, String roomTitle, String roomHeadcount) {
        this.roomLocation = roomLocation;
        this.roomTitle = roomTitle;
        this.roomHeadcount = roomHeadcount;
    }

    public Room(String roomLocation, String roomTitle, String roomHeadcount, String roomID) {
        this.roomLocation = roomLocation;
        this.roomTitle = roomTitle;
        this.roomHeadcount = roomHeadcount;
        this.roomID = roomID;
    }
}
