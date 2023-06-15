package com.example.smiletalk;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SighIn extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    private ArrayList<User> users;
    private Bitmap selectedImageBitmap;

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "AppDB")
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        Button signInBottom = findViewById(R.id.signInBottom);
        signInBottom.setOnClickListener(
                view -> {
                    EditText username = findViewById(R.id.username);
                    EditText password = findViewById(R.id.password);
                    EditText verification = findViewById(R.id.Verif_password);
                    EditText displayName = findViewById(R.id.displayName);
                }

        );
        Button addProfilePicButton = findViewById(R.id.addProfilePicButton);
        addProfilePicButton.setOnClickListener(view -> {
            imageChooser();
            findViewById(R.id.signInBottom);
        });



    }


}