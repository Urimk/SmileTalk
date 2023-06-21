package com.example.smiletalk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AddContactFragment extends Fragment {

    private EditText usernameEditText;

    private AddContactListener addContactListener;


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
                String username = usernameEditText.getText().toString();

                if (username.equals("Urimk")) {
                    // Perform the desired action if the username is "Urimk"
                    List<User> users = new ArrayList<>();
                    users.add(new User("Uri", "Qqwwee11", "Uri", null));
                    users.add(new User("Urimk", "Qqwwee11", "Roni", null));

                    List<Message> msgs = new ArrayList<>();

                    msgs.add(new Message(users.get(0), "00:00:00", "Hey!"));
                    msgs.add(new Message(users.get(1), "00:00:00", "what?"));

                    List<Chat> chats = new ArrayList<>();

                    // Add sample contacts with messages
                    chats.add(new Chat((ArrayList<User>) users, (ArrayList<Message>)msgs));

                    // Invoke the callback method and pass the chats data
                    if (addContactListener != null) {
                        addContactListener.onChatsAdded(chats);
                    }

                    // Close the fragment
                    getParentFragmentManager().popBackStack();
                } else {
                    // Display a toast or alert message if the username is not "Urimk"
                    Toast.makeText(getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }

    public void setAddContactListener(AddContactListener listener) {
        addContactListener = listener;
    }
}

