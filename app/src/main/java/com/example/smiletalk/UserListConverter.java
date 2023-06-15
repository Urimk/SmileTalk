package com.example.smiletalk;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserListConverter {
    @TypeConverter
    public static ArrayList<User> fromString(String value) {
        Type listType = new TypeToken<ArrayList<User>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<User> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
