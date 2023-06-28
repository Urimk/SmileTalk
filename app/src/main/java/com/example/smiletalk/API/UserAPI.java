package com.example.smiletalk.API;
import com.example.smiletalk.entities.TokenRequest;
import com.example.smiletalk.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {
    @GET("Users/{username}")
    Call<User> getUser(@Path("username") String userName, @Header("authorization") String authorization);

    @POST("Users")
    Call<User> createUser(@Body User user);

    @POST("Users/firebase")
    Call<User> sendToken(@Body TokenRequest tokenRequest);
}