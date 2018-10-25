package com.daikirah.joshua.gamebacklog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Game implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private int gid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "platform")
    private String platform;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "last_updated")
    private String date;

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    Game(String title, String platform, String notes, String status) {
        this.title = title;
        this.platform = platform;
        this.notes = notes;
        this.status = status;
        this.date = dateFormat.format(new Date());
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
