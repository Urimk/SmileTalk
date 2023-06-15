package com.example.smiletalk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {
    private Retrofit retrofit;
    private UserAPI user;
    private ChatAPI chat;

    private String url;


    public Server(String url){
        this.url = url;
        retrofit = new Retrofit.Builder()
                .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                .build();
        user = retrofit.create(UserAPI.class);
        chat = retrofit.create(ChatAPI.class);
    }
}
