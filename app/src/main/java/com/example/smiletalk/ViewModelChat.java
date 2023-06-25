package com.example.smiletalk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ViewModelChat extends ViewModel {
    private ChatsRepository repository;
    private LiveData<List<Chat>> chats;

    public ViewModelChat() {
        repository = new ChatsRepository();
        chats = repository.getAll();
    }

    public LiveData<List<Chat>> get() {
        return chats;
    }

    public CompletableFuture<Boolean> add(String token, User other) {
        return repository.add(token, other);
    }

    public void delete(String token,String chatId) {
        repository.delete(token,chatId);
    }

    public void reload(String token) {
        repository.reload(token);
    }

    public void getUserChats(String username){
        repository.getUserChats(username);
    }
}
