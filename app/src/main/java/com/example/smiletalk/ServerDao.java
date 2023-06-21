package com.example.smiletalk;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerDao {
    private MutableLiveData<List<Chat>> chatListData;
    private ChatDao dao;
    private Retrofit retrofit;
    private ChatAPI chat;

    public ServerDao(MutableLiveData<List<Chat>> chatListData, ChatDao dao) {
        this.chatListData = chatListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chat = retrofit.create(ChatAPI.class);
    }

    public void get(String token) {
        Call<List<Chat>> call = chat.getChats(token);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                new Thread(() -> {
                    List<Chat> chats = response.body();
                    dao.clear();
                    assert chats != null;
                    for(Chat c : chats)
                        dao.insert(c);
                    chatListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                // Handle failure case
                Toast.makeText(ContactsActivity.context, "Server on Room did not respond!!!!!", Toast.LENGTH_SHORT).show();
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
                    new Thread(() -> {
                        future.complete(true);
                        assert response.body() != null;
                        Chat newChat = response.body();
                        dao.insert(newChat);
                        chatListData.postValue(dao.index());
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
    public CompletableFuture<Boolean> deleteChat(int chatID)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Chat> call = this.chat.deleteChat(chatID);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, @NonNull Response<Chat> response) {
                if (response.code() == 204) {
                    new Thread(() -> {
                        future.complete(true);
                        assert response.body() != null;
                        Chat delChat = response.body();
                        dao.delete(delChat);
                        chatListData.postValue(dao.index());
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

    public CompletableFuture<Boolean> getChat(String token, int chatID)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Chat> call = this.chat.getChat(chatID,token);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, @NonNull Response<Chat> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        future.complete(true);
                        assert response.body() != null;
                        Chat newChat = response.body();
                        dao.insert(newChat);
                        chatListData.postValue(dao.index());
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

    public CompletableFuture<Boolean> getMessages(String token, int chatID,List<Message> messages,Context chat)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<List<Message>> call = this.chat.getMessages(chatID,token);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        future.complete(true);
                        assert response.body() != null;
                        //refresh all the chats
                        get(token);
                    }).start();

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

    public CompletableFuture<Boolean> sendMessage(String token, int chatID,Message msg,Context chat)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Message> call = this.chat.postMessege(chatID,msg,token);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        future.complete(true);
                        assert response.body() != null;
                        //refresh all the chats
                        get(token);
                    }).start();
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



}
