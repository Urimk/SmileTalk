package com.example.smiletalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Login extends AppCompatActivity {
    private Server server;
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
                    User user = new User(username,password);
                            server.loginUser(user, this).thenAccept(result -> {
                                if (result) {
                                    // Handle the true value
                                    server.getUser(user,this);
                                    //go to login screen
                                    Intent intent = new Intent(this, ContactsActivity.class);
                                    intent.putExtra("user", user);
                                    try {
                                        startActivity(intent);
                                    }catch (Exception e){
                                        System.out.println(e);
                                    }
                                } else {
                                    usernameLable.setError("Incorrect userName or password");
                                }
                            });

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

}