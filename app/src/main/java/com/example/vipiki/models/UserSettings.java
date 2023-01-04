package com.example.vipiki.models;

public class UserSettings {
    private String UID;
    private String name;
    private String post;
    private String schedule;
    private String sector;
    private int postIndex;
    private int scheduleIndex;
    private int sectorIndex;

    public UserSettings() {}

    public UserSettings(String UID, String name, String post, String schedule, String sector, int postIndex, int scheduleIndex, int sectorIndex) {
        this.UID = UID;
        this.name = name;
        this.post = post;
        this.schedule = schedule;
        this.sector = sector;
        this.postIndex = postIndex;
        this.scheduleIndex = scheduleIndex;
        this.sectorIndex = sectorIndex;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(int postIndex) {
        this.postIndex = postIndex;
    }

    public int getScheduleIndex() {
        return scheduleIndex;
    }

    public void setScheduleIndex(int scheduleIndex) {
        this.scheduleIndex = scheduleIndex;
    }

    public int getSectorIndex() {
        return sectorIndex;
    }

    public void setSectorIndex(int sectorIndex) {
        this.sectorIndex = sectorIndex;
    }
}
