package com.example.smiletalk.screens;

import com.example.smiletalk.entities.Chat;

import java.util.List;

public interface AddContactListener {
    void onChatsAdded(List<Chat> chats);
}
