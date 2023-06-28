package com.example.smiletalk.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smiletalk.entities.Chat;
import com.example.smiletalk.R;
import com.example.smiletalk.entities.User;
import com.example.smiletalk.viewModel.ViewModelChat;

import java.util.List;

public class AddContactFragment extends DarkFragment {

    private EditText usernameEditText;
    private AddContactListener addContactListener;
    private User curUser;

    private List<Chat> contactList;
    private ViewModelChat viewModelChat;
    public AddContactFragment(User curUser, List<Chat> contactList, ViewModelChat viewModelChat) {
        this.curUser = curUser;
        this.contactList = contactList;
        this.viewModelChat = viewModelChat;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_contact, container, false);

        usernameEditText = rootView.findViewById(R.id.usernameEditText);

        Button addButton = rootView.findViewById(R.id.addButton);



        addButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();

            Thread thread = new Thread(() -> {
                User contactUser = new User(username);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (contactUser != null) {
                            if (contactUser.getUsername().equals(curUser.getUsername())) {
                                // Users can't chat themselves
                                Toast.makeText(getContext(), "Users can't chat themselves", Toast.LENGTH_SHORT).show();
                            } else {
                                Thread thread1 = new Thread(new Runnable() {
                                    boolean chatExists = false;
                                    @Override
                                    public void run() {
                                        for (Chat chat : contactList) {
                                            List<User> users = chat.getUsers();

                                            for (User user : users) {
                                                if (user.getUsername().equals(contactUser.getUsername())) {
                                                    chatExists = true;
                                                    break;
                                                }
                                            }
                                            if (chatExists) {
                                                break;
                                            }
                                        }
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (chatExists) {
                                                    // Chat already exists
                                                    Toast.makeText(getContext(), "Chat already exists", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    createNewChat(contactUser);
                                                }
                                            }
                                        });
                                    }
                                });
                                thread1.start();
                            }
                        } else {
                            // User does not exist
                            Toast.makeText(getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
            thread.start();
        });

        return rootView;
    }

    private void createNewChat(User contactUser) {
       viewModelChat.add(curUser.getToken(),contactUser).thenAccept(result->{
           if (addContactListener != null && result) {
               addContactListener.onChatsAdded(viewModelChat.get().getValue());
           }else if (!result){
               Toast.makeText(getContext(), "User does not exist or have already chat", Toast.LENGTH_SHORT).show();
           }

       });
    }

    public void setAddContactListener(AddContactListener listener) {
        addContactListener = listener;
    }
}
