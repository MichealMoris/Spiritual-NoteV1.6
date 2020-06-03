package com.example.spiritualnote;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "day")
    private String day;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "morningState")
    private String morningState;
    @ColumnInfo(name = "eveningState")
    private String eveningState;
    @ColumnInfo(name = "nightState")
    private String nightState;
    @ColumnInfo(name = "eucharistState")
    private String eucharistState;
    @ColumnInfo(name = "confessState")
    private String confessState;
    @ColumnInfo(name = "bibleState")
    private String bibleState;
    @ColumnInfo(name = "notes")
    private String notes;

    public Note(String day, String date, String morningState, String eveningState, String nightState, String eucharistState, String confessState, String bibleState, String notes) {
        this.day = day;
        this.date = date;
        this.morningState = morningState;
        this.eveningState = eveningState;
        this.nightState = nightState;
        this.eucharistState = eucharistState;
        this.confessState = confessState;
        this.bibleState = bibleState;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMorningState() {
        return morningState;
    }

    public void setMorningState(String morningState) {
        this.morningState = morningState;
    }

    public String getEveningState() {
        return eveningState;
    }

    public void setEveningState(String eveningState) {
        this.eveningState = eveningState;
    }

    public String getNightState() {
        return nightState;
    }

    public void setNightState(String nightState) {
        this.nightState = nightState;
    }

    public String getEucharistState() {
        return eucharistState;
    }

    public void setEucharistState(String eucharistState) {
        this.eucharistState = eucharistState;
    }

    public String getConfessState() {
        return confessState;
    }

    public void setConfessState(String confessState) {
        this.confessState = confessState;
    }

    public String getBibleState() {
        return bibleState;
    }

    public void setBibleState(String bibleState) {
        this.bibleState = bibleState;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
