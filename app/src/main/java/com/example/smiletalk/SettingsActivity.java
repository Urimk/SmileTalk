package com.example.smiletalk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String DARK_MODE_KEY = "darkModeKey";
    public static final String IP_ADDRESS_KEY = "ipAddressKey";

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

        EditText ipEditText = findViewById(R.id.ipEditText);
        Button setButton = findViewById(R.id.setButton);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipAddress = ipEditText.getText().toString().trim();
                if (!isValidIpAddress(ipAddress)) {
                    Toast.makeText(SettingsActivity.this, "Illegal address", Toast.LENGTH_SHORT).show();
                } else {
                    saveIpAddress(ipAddress);
                    Toast.makeText(SettingsActivity.this, "IP address changed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView resetIpText = findViewById(R.id.resetIpText);
        resetIpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIpAddress(getString(R.string.BaseUrl));
                Toast.makeText(SettingsActivity.this, "IP address reset to default", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView backBtn = findViewById(R.id.backIcon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    private boolean isValidIpAddress(String ipAddress) {
        // Regular expression pattern for IPv4 address with port number
        String ipv4Pattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(:\\d{1,5})?$";

        return ipAddress.matches(ipv4Pattern);
    }

    private void saveIpAddress(String ipAddress) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IP_ADDRESS_KEY, ipAddress);
        editor.apply();
    }
}



