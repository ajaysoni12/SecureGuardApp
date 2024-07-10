package com.example.secureguard.Model;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert(onConflict = REPLACE)
    void saveItem(Message message);

    @Query("select * from messagedb order by creationTime desc")
    List<Message> getAllMessage();

    @Delete
    void delete(Message message);

    @Query("delete from messagedb")
    void deleteAllMessage();
}
