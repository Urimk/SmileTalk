package com.example.smiletalk;

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

    private ServerChat api;
    public ChatsRepository() {
        AppDB db = Room.databaseBuilder(ContactsActivity.context, AppDB.class, "AppDB")
                .fallbackToDestructiveMigration()
                .build();
        dao = db.chatDao();
        chatListData = new ChatListData();
        api = new ServerChat(chatListData,dao);
    }


    public boolean sendMassge(String token, Message msg, String chatID) {
        AtomicBoolean res = new AtomicBoolean(false);
        api.sendMessage(token,chatID,msg,ChatActivity.context).thenAccept(result->{
            if(result)
                res.set(true);
        });
        return res.get();
    }
    public CompletableFuture<Boolean> add(String token, User other) {
        return api.createChat(token,other);
    }

    public boolean delete(String token,String chatId) {
        AtomicBoolean res = new AtomicBoolean(false);
        api.deleteChat(token,chatId).thenAccept(result->{
            if(result)
                res.set(true);
        });
        return res.get();
    }

    public void reload(String token) {
        api.get(token);
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
