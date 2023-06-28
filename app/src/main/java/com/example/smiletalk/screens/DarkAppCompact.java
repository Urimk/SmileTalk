package com.example.smiletalk.screens;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.smiletalk.R;

public class DarkAppCompact extends AppCompatActivity {
    public static final String DARK_MODE_KEY = "darkModeKeyCompact"; // Use a different key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set the theme based on the dark mode preference
        updateTheme();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTheme();
    }


    private void updateTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(DARK_MODE_KEY, MODE_PRIVATE);
        boolean isDarkModeOn = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        int mode = isDarkModeOn ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        if (mode == 1) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        //AppCompatDelegate.setDefaultNightMode(mode);
    }
}

