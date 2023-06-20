package com.example.smiletalk;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {
    @GET("Users/{userName}")
    Call<User> getUser(@Path("userName") String userName, @Header("Authorization") String authorization);

    @POST("Users")
    Call<User> createUser(@Body User user);
}

