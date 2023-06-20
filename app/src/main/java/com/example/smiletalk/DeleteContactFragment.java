package com.example.smiletalk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.List;

public class DeleteContactFragment extends Fragment {

    private List<Chat> contactList;
    private int contactIndex;
    private DeleteContactListener deleteContactListener;

    public DeleteContactFragment(List<Chat> contactList, int contactIndex) {
        this.contactList = contactList;
        this.contactIndex = contactIndex;
    }

    public void setDeleteContactListener(DeleteContactListener listener) {
        deleteContactListener = listener;
    }

    public interface DeleteContactListener {
        void onContactDeleted(int contactIndex);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.delete_contact, container, false);

        Button deleteButton = rootView.findViewById(R.id.rightButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact();
            }
        });

        return rootView;
    }

    private void deleteContact() {
        if (contactIndex >= 0 && contactIndex < contactList.size()) {
            contactList.remove(contactIndex);

            if (deleteContactListener != null) {
                deleteContactListener.onContactDeleted(contactIndex);
            }

            getParentFragmentManager().popBackStack();
        }
    }
}



