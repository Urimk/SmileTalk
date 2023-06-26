package com.example.smiletalk;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface MessagesDao {
    @Query("SELECT * FROM messages WHERE chatID = :chatID")
    MessageList get(String chatID);

    @Insert
    void insert(MessageList... messageLists);

    @Update
    void update(MessageList... messageLists);

    @Delete
    void delete(MessageList... messageLists);

    @Query("DELETE FROM messages")
    void clear();

}
