package com.example.smiletalk;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;
import java.util.List;

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

    public boolean add(String token, User other) {
       return repository.add(token, other);
    }

    public boolean delete(String chatId) {
     return repository.delete(chatId);
    }

    public boolean sendMassage(String token, Message msg, String chatID) {
        return repository.sendMassge(token, msg,chatID);
    }

    public void reload(String token) {
        repository.reload(token);
    }
}
