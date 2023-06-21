package com.example.smiletalk;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM chats")
    List<Chat> index();

    @Query("SELECT * FROM chats WHERE id = :id")
    Chat get(int id);

    @Insert
    void insert(Chat... chats);

    @Update
    void update(Chat... chats);

    @Delete
    void delete(Chat... chats);

    @Query("DELETE FROM chats")
    void clear();


}
