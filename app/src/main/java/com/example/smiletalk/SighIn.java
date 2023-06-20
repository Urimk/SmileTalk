package com.example.smiletalk;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class SighIn extends AppCompatActivity {
    private Server server;
    private Bitmap selectedImageBitmap;
    private static int MAXLENGTH = 200;

    public Context getContext() {
        return context;
    }

    public Context context;


    private boolean checkPassword(String password){
        //check that the password is 8 long and has uppercase letter and lowercase letter
        // Check if password contains an uppercase letter
        boolean hasUppercase = password.matches(".*[A-Z].*");

// Check if password contains a lowercase letter
        boolean hasLowercase = password.matches(".*[a-z].*");

// Check if password is at least 8 characters long
        boolean isAtLeast8Characters = password.length() >= 6;
        if(!(hasLowercase&&hasUppercase&&isAtLeast8Characters)){
            return false;
        }
        return true;
    }

    /*
    handle upload picture from phone
     */
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
        context = getApplicationContext();
        server = new Server();
        //initilize the default profilePic
        ImageView img = findViewById(R.id.chosenPictureImageView);
        selectedImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.smiletalklogo);

        //handle user's submit
        Button signInBottom = findViewById(R.id.signInBottom);
        signInBottom.setOnClickListener(
                view -> {
                    EditText usernameLable = findViewById(R.id.userName);
                    String username = usernameLable.getText().toString();

                    EditText passwordLable = findViewById(R.id.password);
                    String password = passwordLable.getText().toString();

                    EditText verificationLable = findViewById(R.id.Verif_password);
                    String verification = verificationLable.getText().toString();

                    EditText displayNameLable = findViewById(R.id.displayName);
                    String displayName = displayNameLable.getText().toString();
                    if(username.equals("") || password.equals("") || verification.equals("")||
                    displayName.equals("")){
                        EditText[] fields = {usernameLable,passwordLable,displayNameLable,verificationLable};
                        for(EditText l : fields){
                            if(l.getText().length()==0) {
                                l.setError("please fill this field");
                            }
                        }
                    } else if (username.length() > MAXLENGTH || password.length() > MAXLENGTH ||
                            verification.length() > MAXLENGTH || displayName.length() > MAXLENGTH) {
                        EditText[] fields = {usernameLable,passwordLable,displayNameLable,verificationLable};
                        for(EditText l : fields){
                            if(l.getText().length() > MAXLENGTH) {
                                l.setError("This field is too long");
                            }
                        }

                    } else {
                        if (!checkPassword(password)){
                            passwordLable.setError("The password needs to be at least 6 letters long and" +
                                    " to contain upper" +
                                    " case and lower case letters");
                        }else {
                            if(!password.equals(verification)){
                                verificationLable.setError("verification does not match");
                            }else{
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                // Compress the bitmap into a PNG format
                                selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 10, baos);
                                byte[] imageBytes = baos.toByteArray();
                                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                                User newUser = new User(username,
                                        password,
                                        displayName, encodedImage);
                                   server.createUser(newUser,this).thenAccept(result -> {
                                    if (result) {
                                        // Handle the true value
                                        //go to login screen
                                        Intent intent = new Intent(this, Login.class);
                                        try {
                                            startActivity(intent);
                                        }catch (Exception e){
                                        }
                                    } else {
                                        usernameLable.setError("This userName is already exist, pic " + "another one");
                                    }
                                });

                            }
                    }
                    }
                    }

        );
        Button addProfilePicButton = findViewById(R.id.addProfilePicButton);
        addProfilePicButton.setOnClickListener(view -> {
            imageChooser();
           img.setImageBitmap(selectedImageBitmap);
        });

        TextView registered = findViewById(R.id.loginLinkTextView);
        registered.setOnClickListener(View -> {
                // Define the action to start the login activity here
                Intent intent = new Intent(this, Login.class);
                try {
                    startActivity(intent);
                }catch (Exception e){
                }

            }
        );


    }


}
