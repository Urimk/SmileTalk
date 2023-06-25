package com.example.smiletalk;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login extends AppCompatActivity {
    private Server server;
    public static User curUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        server = new Server();

        Button loginInBottom = findViewById(R.id.loginBottom);
        loginInBottom.setOnClickListener(
                view -> {
                    EditText usernameLable = findViewById(R.id.userName);
                    String username = usernameLable.getText().toString();

                    EditText passwordLable = findViewById(R.id.password);
                    String password = passwordLable.getText().toString();
                    User user = new User(username, password);
                    try {
                        server.loginUser(user, this).thenAccept(res -> {
                            if (res) {
                                // Handle the true value

                                getUser(user,this);
                            }else{
                                usernameLable.setError("Incorrect userName or password");
                            }

                        });
                    }catch (Exception e){
                        System.out.println(e);
                    }

                });



        TextView registered = findViewById(R.id.registerLinkTextView);
        registered.setOnClickListener(View -> {
                    // Define the action to start the login activity here
                    Intent intent = new Intent(this, SighIn.class);
                    try {
                        startActivity(intent);
                    }catch (Exception e){
                        System.out.println(e);
                    }

                }
        );
    }

    public CompletableFuture<Boolean> getUser(User user,Context login) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Call<User> call = server.getUser().getUser(user.getUsername(),user.getToken());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    future.complete(true);
                    assert response.body() != null;
                    user.setProfilePic(response.body().getProfilePic());
                    user.setDisplayName(response.body().getDisplayName());
                    curUser = user;
                    runOnUiThread(() -> {
                        Intent i = new Intent(login.getApplicationContext(), ContactsActivity.class);
                        startActivity(i);
                    });
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

}