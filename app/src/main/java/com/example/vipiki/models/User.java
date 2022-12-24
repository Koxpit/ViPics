package com.example.vipiki.models;

public class User {
    private String name;
    private String post;
    private String sector;
    private String schedule;
    private String email;
    private String password;

    public User() {}

    public User(String name, String post, String sector, String schedule, String email) {
        this.name = name;
        this.post = post;
        this.sector = sector;
        this.schedule = schedule;
        this.email = email;
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

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
