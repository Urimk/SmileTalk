package com.example.smiletalk.entities;

import androidx.room.TypeConverter;

import java.util.ArrayList;

import com.example.smiletalk.entities.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class MessageListConverter {
    @TypeConverter
    public static ArrayList<Message> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Message>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Message> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
