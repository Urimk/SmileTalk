package com.example.smiletalk.API;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.smiletalk.entities.Chat;
import com.example.smiletalk.Room.ChatDao;
import com.example.smiletalk.entities.MessageList;
import com.example.smiletalk.Room.MessagesDao;
import com.example.smiletalk.entities.User;
import com.example.smiletalk.Room.UserDao;

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
