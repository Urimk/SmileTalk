package com.example.smiletalk;

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
    Call<Chat> getChat(@Header("Authorization") String authorization);

    @GET("Chats/{id}:Messages")
    Call<List<Message>> getMessages(@Header("Authorization") String authorization);

    @POST("Chats/{id}:Messages")
    Call<Message> postMessege(@Body Message msg,@Header("Authorization") String authorization);

    @POST("Chats")
    Call<Void> createChat(@Body User contact, @Header("Authorization") String authorization);

    @DELETE("Chats/{id}")
    Call<Void> deleteChat(@Path("id") int id);
}
