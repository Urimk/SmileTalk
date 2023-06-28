package com.example.smiletalk.screens;



import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smiletalk.adapters.ContactAdapter;
import com.example.smiletalk.entities.Message;
import com.example.smiletalk.adapters.MessageAdapter;
import com.example.smiletalk.R;
import com.example.smiletalk.entities.User;
import com.example.smiletalk.viewModel.ViewModelChat;
import com.example.smiletalk.entities.Chat;

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
    private static RecyclerView messageRecyclerView;
    private static MessageAdapter messageAdapter;
    private static List<Message> messageList;
    private ViewModelChat viewModelChat;

    //may not be needed
    private User curUser;
    public static Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        context = getApplicationContext();
        User curUser = Login.curUser;
        Chat curChat = ContactAdapter.curChat;
        ArrayList<User> users = curChat.getUsers();
        User otherUser = users.get(0);
        if(otherUser.getUsername().equals(curUser.getUsername()))
            otherUser = users.get(1);
        String pic = otherUser.getProfilePic();
        Bitmap picProf = decodeBase64(pic);
        String contactName = otherUser.getUsername();


        // Initialize views
        viewModelChat =  new ViewModelProvider(this).get(ViewModelChat.class);
        if (picProf != null) {
            int maxWidth = avatarImageView.getMaxWidth();
            int maxHeight = avatarImageView.getMaxHeight();

            int imageWidth = picProf.getWidth();
            int imageHeight = picProf.getHeight();

            if (imageWidth <= maxWidth && imageHeight <= maxHeight) {
                avatarImageView.setImageBitmap(picProf);
            } else {
                // Image is too large, handle the case accordingly
                avatarImageView.setImageResource(R.mipmap.ic_default_avatar);
            }
        } else {
            avatarImageView.setImageResource(R.mipmap.ic_default_avatar);
        }

        nameTextView = findViewById(R.id.nameTextView);
        deleteChatButton = findViewById(R.id.actionButton);
        messageEditText = findViewById(R.id.messageEditText);
        submitButton = findViewById(R.id.submitButton);
        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        messageList = curChat.getMessages();
        messageAdapter = new MessageAdapter(messageList, curUser.getUsername());
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
        submitButton.setOnClickListener(view -> {

            Message message = new Message(curUser, getCurrentTimestamp(), messageEditText.getText().toString().trim());
            if (message != null) {
                viewModelChat.sendMessage(curUser.getToken(),curUser.getUsername(),message,curChat.getId());
                addMessageToScrollView(message);
                messageEditText.getText().clear();
            }
        });

        ImageView backBtn = findViewById(R.id.backIcon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        if (base64String != null) { // && isValidBase64(base64String)
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

    public static void refresh(){
      List<Chat> chats =  ContactsActivity.viewModel.get().getValue();
      Chat chat = null;
      for(Chat c : chats){
          if(c.getId() == ContactAdapter.curChat.getId()){
              chat = c;
              break;
          }
      }
      if(chat == null)
          return;
        messageList = chat.getMessages();
        messageAdapter.setMessageList(messageList);
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set the updated adapter to the RecyclerView
        messageRecyclerView.setAdapter(messageAdapter);
    }
}

