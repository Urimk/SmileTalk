package com.example.smiletalk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class AddContactFragment extends Fragment {

    private EditText usernameEditText;

    public AddContactFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.add_contact, container, false);

        // Find the EditText for the username
        usernameEditText = rootView.findViewById(R.id.usernameEditText);

        // Find the Add button and set a click listener
        Button addButton = rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username entered by the user
                String username = usernameEditText.getText().toString();

                // Perform your desired action with the username, such as adding it to your contacts

                // You can also close the fragment if needed
                getParentFragmentManager().popBackStack();
            }
        });

        return rootView;
    }
}

