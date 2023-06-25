package com.example.smiletalk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;


public class DeleteContactFragment extends Fragment {

    private int contactIndex;
    private DeleteContactListener deleteContactListener;

    public DeleteContactFragment(int contactIndex) {
        this.contactIndex = contactIndex;
    }

    public void setDeleteContactListener(DeleteContactListener listener) {
        deleteContactListener = listener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.delete_contact, container, false);

        Button deleteButton = rootView.findViewById(R.id.rightButton);

        RelativeLayout overlay = rootView.findViewById(R.id.blockClick);

        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Consume the touch event to prevent it from reaching the underlying views
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteContact();
            }
        });

        Button cancelButton = rootView.findViewById(R.id.leftButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDeleteContact();
            }
        });

        return rootView;
    }

    private void confirmDeleteContact() {
        if (deleteContactListener != null) {
            deleteContactListener.onContactDeleted(contactIndex);
        }

        getParentFragmentManager().popBackStack();
    }

    private void cancelDeleteContact() {
        if (deleteContactListener != null) {
            deleteContactListener.onContactDeleteCanceled();
        }

        getParentFragmentManager().popBackStack();
    }

}





