package com.example.smiletalk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String DARK_MODE_KEY = "darkModeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        sharedPreferences = getSharedPreferences(DARK_MODE_KEY, MODE_PRIVATE);

        boolean isDarkModeOn = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        Switch darkModeSwitch = findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setChecked(isDarkModeOn);

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean(DARK_MODE_KEY, isChecked).apply();
                applyTheme(isChecked);
                recreate(); // Recreate the activity to apply the new theme
            }
        });
    }

    private void applyTheme(boolean isDarkModeOn) {
        int mode = isDarkModeOn ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    protected void onStop() {
        super.onStop();

        // Save the state of the switch in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DARK_MODE_KEY, ((Switch) findViewById(R.id.darkModeSwitch)).isChecked());
        editor.apply();
    }


}

