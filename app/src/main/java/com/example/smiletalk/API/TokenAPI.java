package com.example.smiletalk.API;

import com.example.smiletalk.entities.LoginUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TokenAPI {
    @POST("Tokens")
    Call<String> logIn(@Body LoginUser user);
}
