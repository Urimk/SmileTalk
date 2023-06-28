package com.example.smiletalk.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smiletalk.R;
import com.example.smiletalk.entities.Message;
import com.example.smiletalk.entities.User;
import com.example.smiletalk.entities.Chat;
import com.example.smiletalk.screens.ChatActivity;
import com.example.smiletalk.screens.DeleteContactListener;
import com.example.smiletalk.screens.Login;

import java.util.ArrayList;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    public static Chat curChat;
    public List<Chat> getContactList() {
        return contactList;
    }

    public void setContactList(List<Chat> contactList) {
        this.contactList = contactList;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DeleteContactListener getDeleteContactListener() {
        return deleteContactListener;
    }

    public void setDeleteContactListener(DeleteContactListener deleteContactListener) {
        this.deleteContactListener = deleteContactListener;
    }

    private List<Chat> contactList;
    private User curUser;
    private Context context;


    private DeleteContactListener deleteContactListener;

    public ContactAdapter(List<Chat> contactList, User curUser, DeleteContactListener deleteContactListener) {
        this.contactList = contactList;
        this.curUser = curUser;
        this.deleteContactListener = deleteContactListener;
    }



    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Chat chat = contactList.get(holder.getAdapterPosition());
        User otherUser = null;
        for (User user : chat.getUsers()) {
            if (!user.getUsername().equals(getCurUser().getUsername())) {
                otherUser = user;
                break;
            }
        }


        holder.itemView.setOnClickListener(view -> {
            // Get the clicked contact
            int clickedPosition = holder.getAdapterPosition();
            Chat clickedContact = contactList.get(clickedPosition);

            // Find the other user's userName
            User otherUser1 = null;
            for (User user : clickedContact.getUsers()) {
                if (!user.getUsername().equals(curUser.getUsername())) {
                    otherUser1 = user;
                    break;
                }
            }

            // Create an intent to start the ChatActivity
            Intent intent = new Intent(context, ChatActivity.class);
            Login.curUser = curUser;
            curChat = contactList.get(clickedPosition);
            context.startActivity(intent);
        });



        holder.itemView.setOnLongClickListener(view -> {
            if (deleteContactListener != null) {
                int clickedPosition = holder.getAdapterPosition();
                deleteContactListener.showDeleteContactDialog(clickedPosition);
                return true; // Consume the long click event
            }
            return false; // Continue with regular click event handling
        });

        holder.nameTextView.setText(otherUser.getUsername());
        Bitmap bitmap = decodeBase64(otherUser.getProfilePic());
        if (bitmap != null) {
            if (isImageSizeValid(bitmap,holder)) {
                holder.avatarImageView.setImageBitmap(bitmap);
            } else {
                holder.avatarImageView.setImageResource(R.mipmap.ic_default_avatar);
            }
        } else {
            holder.avatarImageView.setImageResource(R.mipmap.ic_default_avatar);
        }
        holder.messageTextView.setText("Hello");
        holder.timestampTextView.setText("16.06.23 : 08:16:00");
    }

    private boolean isImageSizeValid(Bitmap bitmap,ContactViewHolder holder) {
        int maxWidth = holder.avatarImageView.getMaxWidth();
        int maxHeight = holder.avatarImageView.getMaxHeight();

        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();

        return (imageWidth <= maxWidth && imageHeight <= maxHeight);
    }



    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public User getCurUser() {
        return curUser;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView nameTextView;
        TextView messageTextView;
        TextView timestampTextView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }


    private Bitmap decodeBase64(String base64String) {
        if (base64String != null ) { //&& isValidBase64(base64String)
            try {
                byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
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
}
