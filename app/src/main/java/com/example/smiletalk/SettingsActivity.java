package com.example.smiletalk;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private boolean isDarkModeOn = false;
    private String ipAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyTheme();

        setContentView(R.layout.settings_activity);

        TextView resetIpText = findViewById(R.id.resetIpText);
        EditText ipEditText = findViewById(R.id.ipEditText);

        resetIpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ipAddress = "http://10.0.2.2:5000/api/";
                ipEditText.setText(ipAddress);
            }
        });

        Switch darkModeSwitch = findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDarkModeOn = isChecked;
                applyTheme();
            }
        });
    }

    private void applyTheme() {
        if (isDarkModeOn) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        recreate();
    }
}
