package com.example.smiletalk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface TokenAPI {
    @POST("Tokens/")
    Call<String> sighnIn(@Body User user);
}
