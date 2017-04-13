package com.goodall.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.text.View;
import java.time.LocalDate;
import java.time.LocalTime;

public class ViewEvent {

    String title;

    String imgId;

    String description;

    String startTime;

    String duration;

    String location;

    String artist;

    String date;

    String user;

    public ViewEvent() {
    }

    public ViewEvent(String title, String imgId, String description, String startTime, String duration, String location, String artist, String date, String user) {
        this.title = title;
        this.imgId = imgId;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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