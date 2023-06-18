package com.example.smiletalk;

import android.content.Context;
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

import com.example.smiletalk.User;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Chat> contactList;

    private User curUser;

    private String LastMessage;
    private Context context;

    public ContactAdapter(List<Chat> contactList, User curUser) {
        this.contactList = contactList;
        this.curUser = curUser;
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
        Chat chat = contactList.get(position);
        User otherUser = null;
        for (User user : chat.getUsers()) {
            if (!user.getUsername().equals(getCurUser().getUsername())) {
                otherUser = user;
                break;
            }
        }
        holder.nameTextView.setText(otherUser.getUsername());
        Bitmap bitmap = decodeBase64(otherUser.getProfilePic());
        if (bitmap != null) {
            holder.avatarImageView.setImageBitmap(bitmap);
        } else {
            holder.avatarImageView.setImageResource(R.mipmap.ic_default_avatar);
        }
        holder.messageTextView.setText("Hello");
        holder.timestampTextView.setText("16.06.23 : 08:16:00");
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public User getCurUser() { return curUser; }

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
        if (base64String != null && isValidBase64(base64String)) {
            try {
                byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            } catch (IllegalArgumentException e) {
                // Handle invalid Base64 string
                e.printStackTrace();
            }
        }
        // Return null or a default Bitmap in case of invalid input or null string
        return null;
    }

    private boolean isValidBase64(String base64String) {
        // Check if the length is a multiple of 4 (a requirement for Base64)
        if (base64String.length() % 4 != 0) {
            return false;
        }
        // Check if the string contains valid Base64 characters
        for (char c : base64String.toCharArray()) {
            if (!(Character.isLetterOrDigit(c) || c == '+' || c == '/' || c == '=')) {
                return false;
            }
        }
        return true;
    }
}
