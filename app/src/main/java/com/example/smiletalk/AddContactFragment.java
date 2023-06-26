package com.example.smiletalk;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddContactFragment extends DarkFragment {

    private EditText usernameEditText;
    private AddContactListener addContactListener;
    private AppDB appDB;
    private User curUser;

    private List<Chat> contactList;

    public AddContactFragment(User curUser, List<Chat> contactList) {
        this.curUser = curUser;
        this.contactList = contactList;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_contact, container, false);

        usernameEditText = rootView.findViewById(R.id.usernameEditText);

        Button addButton = rootView.findViewById(R.id.addButton);

        appDB = AppDB.getInstance(requireContext());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User contactUser = appDB.userDao().get(username);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (contactUser != null) {
                                    if (contactUser.getUserName().equals(curUser.getUserName())) {
                                        // Users can't chat themselves
                                        Toast.makeText(getContext(), "Users can't chat themselves", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Thread thread = new Thread(new Runnable() {
                                            boolean chatExists = false;
                                            @Override
                                            public void run() {
                                                for (Chat chat : contactList) {
                                                    List<User> users = chat.getUsers();

                                                    for (User user : users) {
                                                        if (user.getUserName().equals(contactUser.getUserName())) {
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
                                        thread.start();
                                    }
                                } else {
                                    // User does not exist
                                    Toast.makeText(getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        return rootView;
    }

    private void createNewChat(User contactUser) {
        List<User> users = new ArrayList<>();
        users.add(curUser);
        users.add(contactUser);

        List<Message> msgs = new ArrayList<>();

        // Add first message which looks different!!!

        Chat chat = new Chat((ArrayList<User>) users, (ArrayList<Message>) msgs);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                appDB.chatDao().insert(chat);
                contactList.add(chat);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (addContactListener != null) {
                            addContactListener.onChatsAdded(contactList);
                        }

                        getParentFragmentManager().popBackStack();
                    }
                });
            }
        });
        thread.start();
    }

    public void setAddContactListener(AddContactListener listener) {
        addContactListener = listener;
    }
}
