package com.goodall.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "events")
public class Event implements HasId {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;

    @Column
    String title;

    @Column
    String imgId;

    @Column
    String description;

    @Column
    String startTime;

    @Column
    String duration;

    @Column
    String location;

    @Column
    String artist;

    @Column
    String date;

    @Column
    String time;

    @ManyToOne
    User user;

    public Event() {
    }

    public Event(String title, String imgId, String description, String startTime, String duration, String location, String artist, String date, String time, User user) {
        this.title = title;
        this.imgId = imgId;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
        this.location = location;
        this.artist = artist;
        this.date = date;
        this.time = time;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(User user) {
        this.user = user;
    }
}