package com.example.smiletalk;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

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
