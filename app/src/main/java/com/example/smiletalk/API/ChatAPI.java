package com.example.smiletalk.API;

import com.example.smiletalk.entities.Chat;
import com.example.smiletalk.entities.Message;
import com.example.smiletalk.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatAPI {
    @GET("Chats")
    Call<List<Chat>> getChats(@Header("Authorization") String authorization);

    @GET("Chats/{id}")
    Call<Chat> getChat(@Path("id") String id,@Header("Authorization") String authorization);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Path("id") String id, @Header("Authorization") String authorization);

    @POST("Chats/{id}/Messages")
    Call<Message> postMessege(@Path("id") String id,@Body Message msg,
                              @Header("Authorization") String authorization);

    @POST("Chats")
    Call<Chat> createChat(@Body User contact, @Header("Authorization") String authorization);

    @DELETE("Chats/{id}")
    Call<Chat> deleteChat(@Path("id") String id, @Header("Authorization") String authorization);
}

