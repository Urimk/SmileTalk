package com.example.smiletalk;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Chat.class, MessageList.class}, version = 5, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ChatDao chatDao();
    public abstract MessagesDao mesDao();

    private static AppDB instance;


    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDB.class, "appDB")
                    .build();
        }
        return instance;
    }
}
