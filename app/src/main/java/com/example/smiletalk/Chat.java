package com.example.smiletalk;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.ArrayList;

@Entity(tableName = "chats")
public class Chat {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private ArrayList<User> users;
    private ArrayList<Message> messages;

    public Chat(ArrayList<User> users, ArrayList<Message> messages) {
        this.users = users;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

