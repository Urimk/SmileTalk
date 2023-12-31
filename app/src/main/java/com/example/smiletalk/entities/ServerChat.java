package com.example.smiletalk.entities;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.smiletalk.API.BaseUrl;
import com.example.smiletalk.API.ChatAPI;
import com.example.smiletalk.Room.MessagesDao;
import com.example.smiletalk.Room.ChatDao;
import com.example.smiletalk.screens.ContactsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerChat {
    private MutableLiveData<List<Chat>> chatListData;
    private ChatDao dao;
    private MessagesDao mesDao;
    private Retrofit retrofit;
    private ChatAPI chat;

    public ServerChat(MutableLiveData<List<Chat>> chatListData, ChatDao dao,
                      MessagesDao mesDao) {
        this.chatListData = chatListData;
        this.dao = dao;
        this.mesDao = mesDao;

        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chat = retrofit.create(ChatAPI.class);
    }

    public void get(String token, String username) {
        Call<List<Chat>> call = chat.getChats(token);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                new Thread(() -> {
                    chatListData.postValue(index(username));
                    List<Chat> chats = response.body();
                    chatListData.postValue(chats);
                    for(Chat c : chats){
                        insert(c);
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                // Handle failure case
                Toast.makeText(ContactsActivity.context, "server did not respond, check internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public CompletableFuture<Boolean> createChat(String token, User otherUser)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Chat> call = this.chat.createChat(otherUser,token);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, @NonNull Response<Chat> response) {
                if (response.isSuccessful()) {
                        future.complete(true);
                        assert response.body() != null;
                        Chat newChat = response.body();
                        List<Chat> currentChats = chatListData.getValue();
                        currentChats.add(newChat);
                        chatListData.postValue(currentChats);
                    new Thread(() -> {
                        insert(newChat);
                    }).start();
                } else {
                    Toast.makeText(ContactsActivity.context, "could not create the chat", Toast.LENGTH_SHORT).show();
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                future.complete(false);
            }
        });


        return future;
    }
    public CompletableFuture<Boolean> deleteChat(String token,String chatID)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Chat> call = this.chat.deleteChat(chatID,token);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, @NonNull Response<Chat> response) {
                if (response.code() == 204) {
                        future.complete(true);
                    new Thread(() -> {
                        delete(chatID);
                    }).start();
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                future.complete(false);
            }
        });


        return future;
    }

    public CompletableFuture<Boolean> getChat(String token, String chatID)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Chat> call = this.chat.getChat(chatID,token);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, @NonNull Response<Chat> response) {
                if (response.isSuccessful()) {
                    future.complete(true);
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                future.complete(false);
            }
        });


        return future;
    }

    public CompletableFuture<Boolean> getMessages(String token, String chatID,Context chat)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<List<Message>> call = this.chat.getMessages(chatID,token);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    future.complete(true);

                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(chat, "Server did not respond", Toast.LENGTH_SHORT).show();
                future.complete(false);
            }
        });


        return future;
    }

    public CompletableFuture<Boolean> sendMessage(String token,String username, String chatID,Message msg,Context chat)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Message> call = this.chat.postMessege(chatID,msg,token);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    future.complete(true);
                    //refresh all the chats
                    get(token, username);
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(chat, "Server did not respond", Toast.LENGTH_SHORT).show();
                future.complete(false);
            }
        });


        return future;
    }

    public void insert(Chat chat){
        if(mesDao.get(chat.getId()) != null)
            return;
        mesDao.insert(new MessageList(chat.getMessages(),chat.getId()));
        chat.setMessages(null);
        dao.insert(chat);
    }

    public void update(String chatID){
        Chat chat = dao.get(chatID);
        mesDao.update(new MessageList(chat.getMessages(),chat.getId()));
    }

    public void delete(String chatID){
        Chat chat = dao.get(chatID);
        mesDao.delete(new MessageList(chat.getMessages(),chat.getId()));
        chat.setMessages(null);
        dao.delete(chat);
    }

    public List<Chat> index(String username){
        List<Chat> chats = dao.getChatsWithUser(username);
        for(Chat c : chats){
            c.setMessages(mesDao.get(c.getId()).getMessages());
        }
        return chats;
    }
}
