package com.example.smiletalk;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity implements DeleteContactListener {

    private ImageView avatarImageView;
    private TextView nameTextView;
    private Button deleteChatButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // Retrieve data from the intent
        // !!! Should change that only the name is passed and the rest
        // is loaded from the db/server maybe! !!!
        String contactName = getIntent().getStringExtra("contactName");
        String pic = getIntent().getStringExtra("contactPic");

        // Initialize views
        avatarImageView = findViewById(R.id.avatarImageView);
        nameTextView = findViewById(R.id.nameTextView);
        deleteChatButton = findViewById(R.id.actionButton);

        // Set contact name
        nameTextView.setText(contactName);
        addMessageToScrollView("Hey", "14:53");




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
    }

    private void addMessageToScrollView(String message, String timestamp) {
        LinearLayout scrollViewLayout = findViewById(R.id.scrollViewLayout);

        // Inflate the message layout
        View messageView = getLayoutInflater().inflate(R.layout.message_rec, null);

        // Set the message content
        TextView messageTextView = messageView.findViewById(R.id.messageTextView);
        messageTextView.setText(message);

        // Set the message timestamp
        TextView timestampTextView = messageView.findViewById(R.id.timestampTextView);
        timestampTextView.setText(timestamp);

        // Add the message view to the ScrollView
        scrollViewLayout.addView(messageView);
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

