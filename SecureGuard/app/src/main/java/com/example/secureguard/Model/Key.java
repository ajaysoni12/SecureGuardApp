package com.example.secureguard.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "keydb")
public class Key implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "key")
    String key;

    @ColumnInfo(name = "messageBackup")
    Boolean messageBackup;

    @ColumnInfo(name = "security")
    Boolean securityKey;

    public Key() {
    }

    public Key(String key) {
        this.key = key;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getMessageBackup() {
        return messageBackup;
    }

    public void setMessageBackup(Boolean messageBackup) {
        this.messageBackup = messageBackup;
    }

    public Boolean getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(Boolean securityKey) {
        this.securityKey = securityKey;
    }
}
