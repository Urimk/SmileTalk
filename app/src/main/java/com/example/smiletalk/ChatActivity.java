package com.example.smiletalk;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends DarkAppCompact implements DeleteContactListener {

    private ImageView avatarImageView;
    private TextView nameTextView;
    private Button deleteChatButton;
    private EditText messageEditText;
    private ImageButton submitButton;
    private RecyclerView messageRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    //may not be needed
    private User curUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // Retrieve data from the intent
        // !!! Should change that only the name is passed and the rest
        // is loaded from the db/server maybe! !!!
        String contactName = getIntent().getStringExtra("contactName");
        String pic = getIntent().getStringExtra("contactPic");
        String curUsername = getIntent().getStringExtra("curUser");
        //find real later...
        curUser = new User(curUsername, "111", "Me", null);


        // Initialize views
        avatarImageView = findViewById(R.id.avatarImageView);
        nameTextView = findViewById(R.id.nameTextView);
        deleteChatButton = findViewById(R.id.actionButton);
        messageEditText = findViewById(R.id.messageEditText);
        submitButton = findViewById(R.id.submitButton);
        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, curUsername);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messageRecyclerView.setLayoutManager(layoutManager);
        messageRecyclerView.setAdapter(messageAdapter);

        // Set contact name
        nameTextView.setText(contactName);


        Bitmap bitmap = decodeBase64(pic);
        if (bitmap != null) {
            avatarImageView.setImageBitmap(bitmap);
        } else {
            avatarImageView.setImageResource(R.mipmap.ic_default_avatar);
        }

        // Set delete chat button click listener
        deleteChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getIntent().getIntExtra("position", -1);
                showDeleteContactDialog(position);
            }
        });

        // Set submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message message = new Message(curUser, getCurrentTimestamp(), messageEditText.getText().toString().trim());
                if (message != null) {
                    addMessageToScrollView(message);
                    messageEditText.getText().clear();
                }
            }
        });

    }

    private String getCurrentTimestamp() {
        // Get the current system time and format it as desired
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void addMessageToScrollView(Message message) {
        if (messageList.isEmpty()) {
            Message introMessage = new Message(null, null, "-----     The beginning of the chat     -----");
            messageList.add(introMessage);
        }

        messageList.add(message);
        messageAdapter.addMessage(message);
        messageRecyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
    }


    private boolean isValidBase64(String base64String) {
        if (base64String.length() % 4 != 0) {
            return false;
        }
        for (char c : base64String.toCharArray()) {
            if (!(Character.isLetterOrDigit(c) || c == '+' || c == '/' || c == '=')) {
                return false;
            }
        }
        return true;
    }
    private Bitmap decodeBase64(String base64String) {
        if (base64String != null && isValidBase64(base64String)) {
            try {
                byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void showDeleteContactDialog(int position) {
        // Check if the AddContactFragment is already added
        Fragment deleteContactFragment = getSupportFragmentManager().findFragmentByTag("DeleteContactFragment");

        if (deleteContactFragment == null) {
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


    // The server will do it, will only need to go back.
    @Override
    public void onContactDeleted(int position) {

    }

    public void onContactDeleteCanceled() {
        findViewById(R.id.grayOutOverlay).setVisibility(View.GONE);
    }
}

