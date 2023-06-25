package com.example.smiletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;


public class ContactsActivity extends AppCompatActivity implements AddContactListener, DeleteContactListener  {

    private RecyclerView rvContacts;
    private ContactAdapter adapter;
    private ViewModelChat viewModel;
    public static Context context;
    private User curUser;
    private List<Chat> contactList;

    private List<Chat> contactList;

    private AppDB appDB;




    // To delete later
    private boolean databaseExists() {
        File dbFile = getDatabasePath("appDB");
        return dbFile.exists();
    }
    //



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






        // To delete later
        String username = "Uri";
        String password = "Qqwwee11";
        String displayName = "Uri";
        String profilePic = null;

        curUser = new User(username, password, displayName, profilePic);

        // Check if the database exists
        if (!databaseExists()) {
            // Create a new instance of AppDB and build the database
            appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "appDB").build();
        } else {
            // Database already exists, retrieve the existing instance
            appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "appDB").build();
        }
        User user1 =  new User("Bob", password, "Bob", profilePic);
        User user2 = new User("John", password, "John", profilePic);
        User user3 = new User("James", password, "James", profilePic);
        User user4 = new User("Alex", password, "Alex", profilePic);
        new Thread(() -> {
            UserDao userDao = appDB.userDao();
            userDao.insert(curUser);
            userDao.insert(user1);
            userDao.insert(user2);
            userDao.insert(user3);
            userDao.insert(user4);
        }).start();
        //

        /*
        User user1 =  new User("Bob", password, "Bob", profilePic);
        User user2 = new User("John", password, "John", profilePic);
        User user3 = new User("James", password, "James", profilePic);
        User user4 = new User("Alex", password, "Alex", profilePic);
        Message msg1 = new Message(user1,"00:00", "Hey");
        Message msg2 = new Message(user2,"00:00", "Sup?");
        Message msg3 = new Message(user1,"00:00", "All Good");
        Message msg4 = new Message(user3,"00:00", "Hi");
        Message msg5 = new Message(user4,"00:00", "Bye");
        List<User> users1 = new ArrayList<>();
        users1.add(user1);
        users1.add(user2);
        List<User> users2 = new ArrayList<>();
        users1.add(user3);
        users1.add(user4);
        List<Message> msgs1 = new ArrayList<>();
        msgs1.add(msg1);
        msgs1.add(msg2);
        msgs1.add(msg3);
        List<Message> msgs2 = new ArrayList<>();
        msgs2.add(msg4);
        msgs2.add(msg5);
        Chat chat1 = new Chat((ArrayList<User>) users1, (ArrayList<Message>) msgs1);
        Chat chat2= new Chat((ArrayList<User>) users2, (ArrayList<Message>) msgs2);
        */














        // Prepare data
        //curUser = getIntent().getParcelableExtra("user");

        contactList = new ArrayList<Chat>();

        // Set up the adapter
        adapter = new ContactAdapter(contactList, curUser, this);
        rvContacts.setAdapter(adapter);


        new Thread(() -> {
            contactList = appDB.chatDao().getChatsWithUser(curUser.getUserName());
            runOnUiThread(() -> {
                // Set up the adapter
                adapter = new ContactAdapter(contactList, curUser, this);
                rvContacts.setAdapter(adapter);
            });
        }).start();


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

                AddContactFragment fragment = new AddContactFragment(curUser, contactList);
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
        new Thread(() -> {
            runOnUiThread(() -> adapter.setContactList(chats));
        }).start();

        // Notify the adapter of the data change
        adapter.notifyDataSetChanged();

        // Hide the overlay
        findViewById(R.id.grayOutOverlay).setVisibility(View.GONE);
    }
}

