package com.example.timetableio.model;

import org.json.JSONObject;

import java.util.List;

public class Classroom {
    private long id;
    private String type;
    private String building;
    private int floor;
    private int roomNumber;
    private List<Schedule> schedules;

    public Classroom() {
    }

    public Classroom(String type, String building, int floor, int roomNumber) {
        this.type = type;
        this.building = building;
        this.floor = floor;
        this.roomNumber = roomNumber;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return id + " | " + building + " | Floor: " + floor + " | Room: " + roomNumber + " | " + type;
    }
}
