package com.example.smiletalk.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smiletalk.R;
import com.example.smiletalk.screens.DarkFragment;

public class LogoutFragment extends DarkFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.logout, container, false);

        Button logoutButton = rootView.findViewById(R.id.rightButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
                getParentFragmentManager().popBackStack();
            }
        });

        Button cancelButton = rootView.findViewById(R.id.leftButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

}
