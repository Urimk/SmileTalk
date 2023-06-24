package com.example.smiletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements AddContactListener, DeleteContactListener  {

    private RecyclerView rvContacts;
    private ContactAdapter adapter;
    private ViewModelChat viewModel;
    public static Context context;
    private User curUser;
    private List<Chat> contactList;

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

        contactList = new ArrayList<Chat>();

        // Set up the adapter
        adapter = new ContactAdapter(contactList, curUser, this);
        rvContacts.setAdapter(adapter);

        // Set up the adapter
        adapter = new ContactAdapter(viewModel.get().getValue(), curUser, this);
        rvContacts.setAdapter(adapter);

        //tests
        //create chat with another user

        Intent i = getIntent();
        curUser = (User) i.getSerializableExtra("user");

        viewModel.reload(curUser.getToken());
      //  viewModel.sendMassage(curUser.getToken(),new Message(curUser,"now","hey"),"64928ad84332e2b54b26882b");
      //  viewModel.delete("64928ad84332e2b54b26882b");
      //  viewModel.add(curUser.getToken(),c);

   //     viewModel.get().observe(this, chats -> {
     //        adapter.setContactList(chats);
       //      adapter.notifyDataSetChanged();
        //     });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
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

