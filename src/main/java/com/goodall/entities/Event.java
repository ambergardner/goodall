package com.goodall.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    int img_id;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String startAndEndTime;

    @Column(nullable = false)
    LocalDateTime dateTime;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    String artist;

    @ManyToOne
    User user;

    public Event(String title, int img_id, String description, String startAndEndTime, LocalDateTime dateTime, String location, String artist) {
        this.title = title;
        this.img_id = img_id;
        this.description = description;
        this.startAndEndTime = startAndEndTime;
        this.dateTime = dateTime;
        this.location = location;
        this.artist = artist;
    }

    public Event(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartAndEndTime() {
        return startAndEndTime;
    }

    public void setStartAndEndTime(String startAndEndTime) {
        this.startAndEndTime = startAndEndTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
}
