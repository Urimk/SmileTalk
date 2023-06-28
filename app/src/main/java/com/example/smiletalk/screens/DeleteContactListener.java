package com.example.smiletalk.screens;

public interface DeleteContactListener {
    void showDeleteContactDialog(int position);
    void onContactDeleted(int position);
    void onContactDeleteCanceled();
}

