package com.example.secureguard.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "messagedb")
public class Message implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "planText")
    String planText;

    @ColumnInfo(name = "encryptedText")
    String encryptedText;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "creationTime")
    Date creationTime;

    public Message() {

    }
    @Ignore
    public Message(String planText, String encryptedText, Date creationTime) {
        this.planText = planText;
        this.encryptedText = encryptedText;
        this.creationTime = creationTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPlanText() {
        return planText;
    }

    public void setPlanText(String planText) {
        this.planText = planText;
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
