package com.example.smiletalk;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.LinkedList;
import java.util.List;

public class ChatsRepository {
    private ChatDao dao;
    private ChatListData chatListData;

    private ServerDao api;
    public ChatsRepository() {
        AppDB db = Room.databaseBuilder(ContactsActivity.context, AppDB.class, "AppDB").build();
        dao = db.chatDao();
        chatListData = new ChatListData();
        api = new ServerDao(chatListData,dao);
    }

    public void add(String token, User other) {
        api.createChat(token,other);
    }

    public void delete(int chatId) {
        api.deleteChat(chatId);
    }

    public void reload(String token) {
        api.get(token);
    }

    class ChatListData extends MutableLiveData<List<Chat>> {
        public ChatListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                chatListData.postValue(dao.index());
            }).start();
        }
    }

    public LiveData<List<Chat>> getAll() {
        return chatListData;
    }

}
