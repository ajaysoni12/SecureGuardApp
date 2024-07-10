package com.example.secureguard.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.secureguard.Model.Key;
import com.example.secureguard.Model.KeyDao;
import com.example.secureguard.Model.Message;
import com.example.secureguard.Model.MessageDao;

@Database(entities = {Message.class, Key.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static final String DATABASE_NAME = "HiddleDB";

    public synchronized static RoomDB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class,
                            DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MessageDao mainDao();
    public abstract KeyDao keyDao();
}
