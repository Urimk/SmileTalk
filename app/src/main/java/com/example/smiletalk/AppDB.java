package com.example.smiletalk;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Chat.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ChatDao chatDao();
}
