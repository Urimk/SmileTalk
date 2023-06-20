package com.example.smiletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements AddContactListener {

    private RecyclerView rvContacts;
    private ContactAdapter adapter;
    private List<Chat> contactList;

    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Initialize RecyclerView
        rvContacts = findViewById(R.id.rvContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        // Prepare data
        contactList = generateChats();
        curUser = new User("Uri", "Qqwwee11", "Uri", "1");

        // Set up the adapter
        adapter = new ContactAdapter(contactList, curUser);
        rvContacts.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactFragment fragment = new AddContactFragment();
                fragment.setAddContactListener(ContactsActivity.this);

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, fragment)
                        .addToBackStack(null)
                        .commit();

                findViewById(R.id.grayOutOverlay).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        findViewById(R.id.grayOutOverlay).setVisibility(View.GONE);
    }

    private List<Chat> generateChats() {
        List<User> users1 = new ArrayList<>();
        List<User> users2 = new ArrayList<>();
        users1.add(new User("Uri", "Qqwwee11", "Uri", null));
        users1.add(new User("Roni", "Qqwwee11", "Roni", null));
        users2.add(new User("Aba", "Qqwwee11", "Aba", null));
        users2.add(new User("Ima", "Qqwwee11", "Ima", null));

        List<Message> msgs1 = new ArrayList<>();
        List<Message> msgs2 = new ArrayList<>();

        msgs1.add(new Message(users1.get(0), "00:00:00", "Hey!"));
        msgs1.add(new Message(users1.get(1), "00:00:00", "sup?"));

        msgs2.add(new Message(users2.get(0), "00:01:00", "Heyyyy!"));
        msgs1.add(new Message(users2.get(1), "00:01:00", "sup?"));

        List<Chat> chats = new ArrayList<>();

        // Add sample contacts with messages
        chats.add(new Chat((ArrayList<User>) users1, (ArrayList<Message>)msgs1));
        chats.add(new Chat((ArrayList<User>) users2, (ArrayList<Message>)msgs2));

        return chats;
    }

    public void onChatsAdded(List<Chat> chats) {
        // Add the new chat to the contactList
        contactList.addAll(chats);

        // Notify the adapter of the data change
        adapter.notifyDataSetChanged();

        // Hide the overlay
        findViewById(R.id.grayOutOverlay).setVisibility(View.GONE);
    }
}

