package com.example.smiletalk.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

@Entity(tableName = "users")
public class User implements Serializable {
    @NonNull
    @PrimaryKey
    private String username;

    private String password;
    private String displayName;
    private String profilePic;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = "bearer " + token;
    }

    private String token;

    public User(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    @Ignore
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.displayName = "";
        this.profilePic = "";
        this.token = "";
    }

    @Ignore
    public User(String username) {
        this.username = username;
        this.password = "";
        this.displayName = "";
        this.profilePic = "";
        this.token = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void savePathImage() {
        // Assuming you have the user object and the profile image base64 string
        String profileImageBase64 = getProfilePic();


        File imageDirectory = Environment.getExternalStorageDirectory();

// Step 2: Generate a unique file name
        String fileName = "profile_" + System.currentTimeMillis() + ".jpg";

// Step 3: Save the image file to the directory
        File imageFile = new File(imageDirectory, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            byte[] imageBytes = Base64.decode(profileImageBase64, Base64.DEFAULT);
            outputStream.write(imageBytes);
            outputStream.flush();
        } catch (IOException e) {
            // Handle the exception
        }

// Step 4: Set the file path on the User object
        String imagePath = imageFile.getAbsolutePath();
        setProfilePic(imagePath);

    }
}


