package com.example.smiletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class ContactsActivity extends AppCompatActivity implements AddContactListener, DeleteContactListener  {


    private RecyclerView rvContacts;
    private ContactAdapter adapter;
    private ViewModelChat viewModel;
    public static Context context;
    private User curUser;

    private List<Chat> contactList;

    private AppDB appDB;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        context = getApplicationContext();
        Intent intent = getIntent();
        viewModel =  new ViewModelProvider(this).get(ViewModelChat.class);
        // Initialize RecyclerView
        rvContacts = findViewById(R.id.rvContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        // Prepare data
        User a = new User("a","11","a","11");
        a.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImEiLCJpYXQiOjE2ODc1MDUzOTF9.KeoQ1_dwh3-01pR70Th3h-ooqzbdlomQHT8AuIwV0dY");
        User b = new User("b","11","a","11");
        User c = new User("c","11","a","11");
        //curUser = Login.curUser;
        curUser = a;
        new Thread(() -> {
            viewModel.getUserChats(curUser.getUsername());
            contactList = viewModel.get().getValue();
            runOnUiThread(() -> {
                // Set up the adapter
                adapter = new ContactAdapter(contactList, curUser, this);
                rvContacts.setAdapter(adapter);
            });
        }).start();
        viewModel.reload(curUser.getToken(),curUser.getUsername());
        viewModel.get().observe(this, chats -> {
            if (adapter != null) {
                adapter.setContactList(chats);
                adapter.notifyDataSetChanged();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Check if the AddContactFragment is already added
            Fragment addContactFragment = getSupportFragmentManager().findFragmentByTag("AddContactFragment");
            Fragment deleteContactFragment = getSupportFragmentManager().findFragmentByTag("DeleteContactFragment");

            if (addContactFragment == null && deleteContactFragment == null) {
                // Neither AddContactFragment nor DeleteContactFragment is added, proceed with adding AddContactFragment

                AddContactFragment fragment = new AddContactFragment(curUser, contactList, viewModel);
                fragment.setAddContactListener(ContactsActivity.this);

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, fragment, "AddContactFragment")
                        .addToBackStack(null)
                        .commit();

                findViewById(R.id.grayOutOverlay).setVisibility(View.VISIBLE);
            }
        });

    }

    public void showDeleteContactDialog(int position) {
        // Check if the AddContactFragment is already added
        Fragment addContactFragment = getSupportFragmentManager().findFragmentByTag("AddContactFragment");
        Fragment deleteContactFragment = getSupportFragmentManager().findFragmentByTag("DeleteContactFragment");

        if (addContactFragment == null && deleteContactFragment == null) {
            // Neither AddContactFragment nor DeleteContactFragment is added, proceed with adding DeleteContactFragment
            DeleteContactFragment fragment = new DeleteContactFragment(position);
            // Pass the index of the item to the fragment
            Bundle args = new Bundle();
            args.putInt("position", position);
            fragment.setArguments(args);

            // Set the listener
            fragment.setDeleteContactListener(this);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, "DeleteContactFragment")
                    .addToBackStack(null)
                    .commit();

            findViewById(R.id.grayOutOverlay).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onContactDeleted(int position) {
        // Remove the contact from the list
        //contactList.remove(position);

        // Notify the adapter of the data change
        Chat reChat = viewModel.get().getValue().get(position);
        viewModel.delete(curUser.getToken(),reChat);
        adapter.notifyItemRemoved(position);
        findViewById(R.id.grayOutOverlay).setVisibility(View.GONE);
    }

    public void onContactDeleteCanceled() {
        findViewById(R.id.grayOutOverlay).setVisibility(View.GONE);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        findViewById(R.id.grayOutOverlay).setVisibility(View.GONE);
    }


    public void onChatsAdded(List<Chat> chats) {
        // Add the new chat to the contactList
        adapter.setContactList(chats);
        // Notify the adapter of the data change
        adapter.notifyDataSetChanged();

        // Hide the overlay
        findViewById(R.id.grayOutOverlay).setVisibility(View.GONE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Set the updated adapter to the RecyclerView
        rvContacts.setAdapter(adapter);
    }
}