package com.example.smiletalk;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.smiletalk.AppDB;
import com.example.smiletalk.Chat;
import com.example.smiletalk.ChatDao;
import com.example.smiletalk.User;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatsRepository {
    private ChatDao dao;
    private ChatListData chatListData;

    private ServerDao api;
    public ChatsRepository() {
        AppDB db = Room.databaseBuilder(ContactsActivity.context, AppDB.class, "AppDB")
                .build();
        dao = db.chatDao();
        chatListData = new ChatListData();
        api = new ServerDao(chatListData,dao);
    }


    public boolean sendMassge(String token, Message msg, String chatID) {
        AtomicBoolean res = new AtomicBoolean(false);
        api.sendMessage(token,chatID,msg).thenAccept(result->{
            if(result)
                res.set(true);
        });
        return res.get();
    }
    public boolean add(String token, User other) {
        AtomicBoolean res = new AtomicBoolean(false);
        api.createChat(token,other).thenAccept(result->{
            if(result)
                res.set(true);
        });
        return res.get();
    }

    public boolean delete(String chatId) {
        AtomicBoolean res = new AtomicBoolean(false);
        api.deleteChat(chatId).thenAccept(result->{
            if(result)
                res.set(true);
        });
        return res.get();
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

