package com.example.smiletalk.screens;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.smiletalk.R;

public class DarkFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Set the theme based on the dark mode preference
        updateTheme();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTheme();
    }

    private void updateTheme() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(DarkAppCompact.DARK_MODE_KEY, MODE_PRIVATE);
        boolean isDarkModeOn = sharedPreferences.getBoolean(DarkAppCompact.DARK_MODE_KEY, false);

        int mode = isDarkModeOn ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            requireContext().setTheme(R.style.DarkTheme);
        } else {
            requireContext().setTheme(R.style.LightTheme);
        }
    }
}

