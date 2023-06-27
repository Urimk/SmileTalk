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

    public void add(String token, User other) {
        repository.add(token, other);
    }

    public void delete(int chatId) {
        repository.delete(chatId);
    }

    public void reload(String token) {
        repository.reload(token);
    }
}
