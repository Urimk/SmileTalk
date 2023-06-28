package com.example.smiletalk.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smiletalk.entities.Chat;
import com.example.smiletalk.entities.Message;
import com.example.smiletalk.entities.User;
import com.example.smiletalk.reposetory.ChatsRepository;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ViewModelChat extends ViewModel implements Serializable {
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

    public void delete(String token, Chat rem) {
        repository.delete(token,rem);
    }

    public void reload(String token, String username) {
        repository.reload(token,username);
    }

    public void getUserChats(String username){
        repository.getUserChats(username);
    }

    public  void sendMessage(String token, String username, Message msg, String chatID){
        repository.sendMassge(token,username,msg,chatID);
    }
}
