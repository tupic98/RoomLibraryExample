package com.example.alejandro.roomexampleproject.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
                                            parentColumns = "id",
                                            childColumns = "user_id",
                                            onDelete = ForeignKey.CASCADE))
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "data")
    private String data;

    public Note(int id, String data, Date date, int userId) {
        this.id = id;
        this.data = data;
        this.date = date;
        this.userId = userId;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "user_id")
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
