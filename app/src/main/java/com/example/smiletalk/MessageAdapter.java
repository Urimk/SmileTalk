package com.example.smiletalk;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;
    private String curUser;

    public MessageAdapter(List<Message> messageList, String curUser) {
        this.messageList = messageList;
        this.curUser = curUser;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 0) {
            // Use the new_layout for the first message
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.intro_msg, parent, false);
        } else if (viewType == 1) {
            // Inflate the message_sent layout for messages from the current user
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent, parent, false);
        } else {
            // Inflate the message_rec layout for other messages
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_rec, parent, false);
        }
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);

        holder.messageTextView.setText(message.getContent());
        holder.timestampTextView.setText(message.getTime());

        // You can customize the gravity of the messageTextView based on the sender
        if (message.getSender().getUserName().equals(curUser)) {
            holder.messageTextView.setGravity(Gravity.END);
        } else {
            holder.messageTextView.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // Return 0 for the first message
            return 0;
        } else {
            Message message = messageList.get(position - 1); // Adjusted position to exclude the first message
            if (message.getSender().getUserName().equals(curUser)) {
                return 1; // Sent message
            } else {
                return 2; // Received message
            }
        }
    }

    public void addMessage(Message message) {
        messageList.add(message);
        notifyDataSetChanged();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timestampTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
}





