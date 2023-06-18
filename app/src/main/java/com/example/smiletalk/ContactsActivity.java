package com.example.smiletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView rvContacts;
    private ContactAdapter adapter;
    private List<Chat> contactList;

    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        rvContacts = findViewById(R.id.rvContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        // Prepare data
        contactList = generateChats();
        curUser = new User("Uri", "Qqwwee11", "Uri", "1");
        // Set up the adapter
        adapter = new ContactAdapter(contactList, curUser);
        rvContacts.setAdapter(adapter);

        // Set click listener for the main activity layout
       /* findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Start the new activity when the layout is clicked
                //Intent intent = new Intent(ContactsActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });*/
    }

    private List<Chat> generateChats() {
        List<User> users1 = new ArrayList<>();
        List<User> users2 = new ArrayList<>();
        users1.add(new User("Uri", "Qqwwee11", "Uri", "1"));
        users1.add(new User("Roni", "Qqwwee11", "Roni", "1"));
        users2.add(new User("Aba", "Qqwwee11", "Aba", "1"));
        users2.add(new User("Ima", "Qqwwee11", "Ima", "1"));

        List<Message> msgs1 = new ArrayList<>();
        List<Message> msgs2 = new ArrayList<>();

        msgs1.add(new Message(users1.get(0), "00:00:00", "Hey!"));
        msgs1.add(new Message(users1.get(1), "00:00:00", "sup?"));

        msgs2.add(new Message(users2.get(4), "00:01:00", "Heyyyy!"));
        msgs1.add(new Message(users2.get(3), "00:01:00", "sup?"));


        List<Chat> chats = new ArrayList<>();

        // Add sample contacts with messages
        chats.add(new Chat((ArrayList<User>) users1, (ArrayList<Message>)msgs1));
        chats.add(new Chat((ArrayList<User>) users2, (ArrayList<Message>)msgs2));

        return chats;
    }


}
