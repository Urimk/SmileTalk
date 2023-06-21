package com.example.smiletalk;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "users")
public class User implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;

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

    public User(String userName, String password, String displayName, String profilePic) {
        this.id = 0;
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }
@Ignore
    public User(String userName, String password) {
        this.id = 0;
        this.userName = userName;
        this.password = password;
        this.displayName = "";
        this.profilePic = "";
        this.token = "";
    }

    @Ignore
    public User(String userName) {
        this.id = 0;
        this.userName = userName;
        this.password = "";
        this.displayName = "";
        this.profilePic = "";
        this.token = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}


