package com.example.smiletalk;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

@Entity(tableName = "chats")
public class Chat {
    public int getPrimery() {
        return primery;
    }

    public void setPrimery(int primery) {
        this.primery = primery;
    }

    @PrimaryKey(autoGenerate = true)
    private int primery;
    private String id;
    @TypeConverters(UserListConverter.class)
    private ArrayList<User> users;

    @TypeConverters(MessageListConverter.class)
    private ArrayList<Message> messages;

    public Chat(ArrayList<User> users, ArrayList<Message> messages) {
        this.users = users;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}

