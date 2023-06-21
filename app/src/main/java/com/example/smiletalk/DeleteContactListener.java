package com.example.smiletalk;

public interface DeleteContactListener {
    void showDeleteContactDialog(int position);
    void onContactDeleted(int position);
    void onContactDeleteCanceled();
}

