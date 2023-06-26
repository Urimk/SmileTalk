package com.example.smiletalk;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;
@Entity(tableName = "messages")
public class MessageList {
    @TypeConverters(MessageListConverter.class)
    private ArrayList<Message> messages;
    @PrimaryKey
    @NonNull
    private String chatID;

    public MessageList(ArrayList<Message> messages, String chatID) {
        this.messages = messages;
        this.chatID = chatID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }
}
