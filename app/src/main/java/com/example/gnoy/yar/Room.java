package com.example.gnoy.yar;

public class Room {
    String roomLocation;
    String roomTitle;
    String roomHeadcount;

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

    public Room(String roomLocation, String roomTitle, String roomHeadcount) {
        this.roomLocation = roomLocation;
        this.roomTitle = roomTitle;
        this.roomHeadcount = roomHeadcount;
    }
}
