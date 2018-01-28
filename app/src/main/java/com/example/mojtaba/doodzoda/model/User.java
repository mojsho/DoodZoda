package com.example.mojtaba.doodzoda.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private int userId;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("date_joined")
    @Expose
    private String dateJoined;

    @SerializedName("date_stopped")
    @Expose
    private String dateStopped;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getDateStopped() {
        return dateStopped;
    }

    public void setDateStopped(String dateStopped) {
        this.dateStopped = dateStopped;
    }

    public User(int userId, String username, String name, String dateJoined, String dateStopped) {

        this.userId = userId;
        this.username = username;
        this.name = name;
        this.dateJoined = dateJoined;
        this.dateStopped = dateStopped;
    }
}
