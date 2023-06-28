package com.example.smiletalk;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatsRepository {
    private ChatDao dao;
    private ChatListData chatListData;
    private MessagesDao mesDao;

    private ServerChat api;
    public ChatsRepository() {
        AppDB db = Room.databaseBuilder(ContactsActivity.context, AppDB.class, "AppDB")
                .fallbackToDestructiveMigration()
                .build();
        dao = db.chatDao();
        mesDao = db.mesDao();
        chatListData = new ChatListData();
        api = new ServerChat(chatListData,dao,mesDao);
    }


    public boolean sendMassge(String token,String username, Message msg, String chatID) {
        AtomicBoolean res = new AtomicBoolean(false);
        api.sendMessage(token,username,chatID,msg,ChatActivity.context).thenAccept(result->{
            if(result) {
                api.getMessages(token, chatID, ChatActivity.context).thenAccept(r -> {
                    if (r)
                        res.set(true);
                });
            };
            });
        return res.get();
    }
    public CompletableFuture<Boolean> add(String token, User other) {
        return api.createChat(token,other);
    }

    public boolean delete(String token,Chat rem) {
        AtomicBoolean res = new AtomicBoolean(false);
        this.chatListData.deleteChat(rem);
        api.deleteChat(token,rem.getId()).thenAccept(result->{
            if(result)
                res.set(true);
        });
        return res.get();
    }

    public void reload(String token,String username) {
        api.get(token,username);
    }

    public LiveData<List<Chat>> getAll() {
        return chatListData;
    }



    public void getUserChats(String username) {
        chatListData.postValue(dao.getChatsWithUser(username));
    }

    class ChatListData extends MutableLiveData<List<Chat>> {
        public ChatListData() {
            super();
            setValue(new LinkedList<>());
        }

        public void addChat(Chat chat) {
            List<Chat> currentChats = getValue();
            currentChats.add(chat);
            setValue(currentChats);
        }

        public void deleteChat(Chat chat) {
            List<Chat> currentChats = getValue();
            currentChats.remove(chat);
            setValue(currentChats);
        }

    }
}
