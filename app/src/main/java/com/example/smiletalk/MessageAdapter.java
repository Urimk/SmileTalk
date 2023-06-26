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
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Return 0 if the message is from the current user, 1 otherwise
        Message message = messageList.get(position);
        if (message.getSender().getUserName().equals(curUser)) {
            return 0;
        } else {
            return 1;
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




