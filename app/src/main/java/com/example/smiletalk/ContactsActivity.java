package com.example.smiletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements AddContactListener, DeleteContactListener  {

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
        contactList = new ArrayList<Chat>();
        curUser = new User("Uri", "Qqwwee11", "Uri", "1");

        // Set up the adapter
        adapter = new ContactAdapter(contactList, curUser, this);
        rvContacts.setAdapter(adapter);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the AddContactFragment is already added
                Fragment addContactFragment = getSupportFragmentManager().findFragmentByTag("AddContactFragment");
                Fragment deleteContactFragment = getSupportFragmentManager().findFragmentByTag("DeleteContactFragment");

                if (addContactFragment == null && deleteContactFragment == null) {
                    // Neither AddContactFragment nor DeleteContactFragment is added, proceed with adding AddContactFragment
                    AddContactFragment fragment = new AddContactFragment();
                    fragment.setAddContactListener(ContactsActivity.this);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, fragment, "AddContactFragment")
                            .addToBackStack(null)
                            .commit();

                    findViewById(R.id.grayOutOverlay).setVisibility(View.VISIBLE);
                }
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
        contactList.remove(position);

        // Notify the adapter of the data change
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
        contactList.addAll(chats);

        // Notify the adapter of the data change
        adapter.notifyDataSetChanged();

        // Hide the overlay
        findViewById(R.id.grayOutOverlay).setVisibility(View.GONE);
    }
}