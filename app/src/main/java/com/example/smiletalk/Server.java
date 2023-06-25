package com.example.smiletalk;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
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
                .baseUrl("http://192.168.43.169:5000/api/")
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
        Call<String> call = this.token.logIn(new LoginUser(user.getUsername(),user.getPassword()));

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






}
