package com.goodall.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Lob;

public class ViewEvent {

    String title;

    @JsonProperty("img-id")
    String imgId;

//    @Lob
//    private byte[] eventImg;

    String description;

    @JsonProperty("start-time")
    String startTime;

    @JsonProperty("end-time")
    String endTime;

    String location;

    String artist;

    String date;

    String user;//reference to the user id

    public ViewEvent() {
    }

    public ViewEvent(String title, String imgId, String description, String startTime, String endTime, String location, String artist, String date, String user) {
        this.title = title;
        this.imgId = imgId;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.artist = artist;
        this.date = date;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}