package com.example.smiletalk;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class Server {
    private UserAPI user;
    private ChatAPI chat;

    private TokenAPI token;
    private Retrofit retrofit;

    public UserAPI getUser() {
        return user;
    }

    public void setUser(UserAPI user) {
        this.user = user;
    }

    public ChatAPI getChat() {
        return chat;
    }

    public void setChat(ChatAPI chat) {
        this.chat = chat;
    }


    public Server() {
        // Create an instance of HttpLoggingInterceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Create an instance of OkHttpClient and add the interceptor
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(loggingInterceptor);
        OkHttpClient httpClient = httpClientBuilder.build();

        // Create Retrofit instance with the OkHttpClient
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .client(httpClient) // Set the OkHttpClient
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        user = retrofit.create(UserAPI.class);
        chat = retrofit.create(ChatAPI.class);
        token = retrofit.create(TokenAPI.class);
    }



    public CompletableFuture<Boolean> createUser(User user, Context sign)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<User> call = this.user.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    future.complete(true);
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Sign", "Sign API call failed: " + t.getMessage());
                Toast.makeText(sign, "Server did not respond", Toast.LENGTH_SHORT).show();
                future.complete(false);
            }
        });


      return future;
    }


    public CompletableFuture<Boolean> loginUser(User user, Context login) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Call<String> call = this.token.logIn(new LoginUser(user.getUserName(),user.getPassword()));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                        user.setToken(token);
                        future.complete(true);

                } else {
                    future.complete(false);
                    Toast.makeText(login, "Server did not respond " + response.code(), Toast.LENGTH_SHORT).show();
                    System.out.println("Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Sign", "Token API call failed: " + t.getMessage());
                Toast.makeText(login, "Server did not respond!!!!!", Toast.LENGTH_SHORT).show();
                future.complete(false);
            }
        });

        return future;
    }

    public CompletableFuture<Boolean> getUser(User user, Context login) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Call<User> call = this.user.getUser(user.getUserName(),user.getToken());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    future.complete(true);
                    assert response.body() != null;
                    user.setProfilePic(response.body().getProfilePic());
                    user.setDisplayName(response.body().getDisplayName());


                } else {
                    future.complete(false);
                    Toast.makeText(login, "Server did not respond " + response.code(), Toast.LENGTH_SHORT).show();
                    System.out.println("Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Sign", "Token API call failed: " + t.getMessage());
                Toast.makeText(login, "Server did not respond!!!!!", Toast.LENGTH_SHORT).show();
                future.complete(false);
            }
        });

        return future;
    }

    public CompletableFuture<Boolean> createChat(String token,User otherUser,Chat empty , Context chat)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Chat> call = this.chat.createChat(otherUser,token);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, @NonNull Response<Chat> response) {
                if (response.isSuccessful()) {
                    future.complete(true);
                    assert response.body() != null;
                    empty.setId(response.body().getId());
                    empty.setUsers(response.body().getUsers());
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Toast.makeText(chat, "Server did not respond", Toast.LENGTH_SHORT).show();
                future.complete(false);
            }
        });


        return future;
    }
    public CompletableFuture<Boolean> deleteChat(int chatID, Context chat)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Chat> call = this.chat.deleteChat(chatID);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, @NonNull Response<Chat> response) {
                if (response.code() == 204) {
                    future.complete(true);
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Toast.makeText(chat, "Server did not respond", Toast.LENGTH_SHORT).show();
                future.complete(false);
            }
        });


        return future;
    }

    public CompletableFuture<Boolean> getChats(String token, List<Chat> chats, Context chat)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<List<Chat>> call = this.chat.getChats(token);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    future.complete(true);
                    assert response.body() != null;
                    chats.addAll(response.body());
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                Toast.makeText(chat, "Server did not respond", Toast.LENGTH_SHORT).show();
                future.complete(false);
            }
        });


        return future;
    }
    public CompletableFuture<Boolean> getChat(String token, int chatID, Chat empty,Context chat)  {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Call<Chat> call = this.chat.getChat(chatID,token);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, @NonNull Response<Chat> response) {
                if (response.isSuccessful()) {
                    future.complete(true);
                    assert response.body() != null;
                    empty.setId(response.body().getId());
                    empty.setUsers(response.body().getUsers());
                    empty.setMessages(response.body().getMessages());
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Toast.makeText(chat, "Server did not respond", Toast.LENGTH_SHORT).show();
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
                    future.complete(true);
                    assert response.body() != null;
                    messages.addAll(response.body());
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
                    future.complete(true);
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
