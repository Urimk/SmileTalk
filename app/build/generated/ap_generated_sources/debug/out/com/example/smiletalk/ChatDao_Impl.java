package com.example.smiletalk;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatDao_Impl implements ChatDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Chat> __insertionAdapterOfChat;

  private final EntityDeletionOrUpdateAdapter<Chat> __deletionAdapterOfChat;

  private final EntityDeletionOrUpdateAdapter<Chat> __updateAdapterOfChat;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  public ChatDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChat = new EntityInsertionAdapter<Chat>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `chats` (`id`,`users`,`messages`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Chat value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        final String _tmp = UserListConverter.fromArrayList(value.getUsers());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, _tmp);
        }
        final String _tmp_1 = MessageListConverter.fromArrayList(value.getMessages());
        if (_tmp_1 == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfChat = new EntityDeletionOrUpdateAdapter<Chat>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `chats` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Chat value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfChat = new EntityDeletionOrUpdateAdapter<Chat>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `chats` SET `id` = ?,`users` = ?,`messages` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Chat value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        final String _tmp = UserListConverter.fromArrayList(value.getUsers());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, _tmp);
        }
        final String _tmp_1 = MessageListConverter.fromArrayList(value.getMessages());
        if (_tmp_1 == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp_1);
        }
        if (value.getId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getId());
        }
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM chats";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Chat... chats) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfChat.insert(chats);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Chat... chats) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfChat.handleMultiple(chats);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Chat... chats) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfChat.handleMultiple(chats);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clear() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClear.release(_stmt);
    }
  }

  @Override
  public List<Chat> index() {
    final String _sql = "SELECT * FROM chats LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUsers = CursorUtil.getColumnIndexOrThrow(_cursor, "users");
      final int _cursorIndexOfMessages = CursorUtil.getColumnIndexOrThrow(_cursor, "messages");
      final List<Chat> _result = new ArrayList<Chat>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Chat _item;
        final ArrayList<User> _tmpUsers;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfUsers)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfUsers);
        }
        _tmpUsers = UserListConverter.fromString(_tmp);
        final ArrayList<Message> _tmpMessages;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMessages)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfMessages);
        }
        _tmpMessages = MessageListConverter.fromString(_tmp_1);
        _item = new Chat(_tmpUsers,_tmpMessages);
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Chat get(final int id) {
    final String _sql = "SELECT * FROM chats WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUsers = CursorUtil.getColumnIndexOrThrow(_cursor, "users");
      final int _cursorIndexOfMessages = CursorUtil.getColumnIndexOrThrow(_cursor, "messages");
      final Chat _result;
      if(_cursor.moveToFirst()) {
        final ArrayList<User> _tmpUsers;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfUsers)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfUsers);
        }
        _tmpUsers = UserListConverter.fromString(_tmp);
        final ArrayList<Message> _tmpMessages;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMessages)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfMessages);
        }
        _tmpMessages = MessageListConverter.fromString(_tmp_1);
        _result = new Chat(_tmpUsers,_tmpMessages);
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Chat> getChatsWithUser(final String username) {
    final String _sql = "SELECT * FROM chats WHERE ? IN (SELECT username FROM users)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUsers = CursorUtil.getColumnIndexOrThrow(_cursor, "users");
      final int _cursorIndexOfMessages = CursorUtil.getColumnIndexOrThrow(_cursor, "messages");
      final List<Chat> _result = new ArrayList<Chat>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Chat _item;
        final ArrayList<User> _tmpUsers;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfUsers)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfUsers);
        }
        _tmpUsers = UserListConverter.fromString(_tmp);
        final ArrayList<Message> _tmpMessages;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMessages)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfMessages);
        }
        _tmpMessages = MessageListConverter.fromString(_tmp_1);
        _item = new Chat(_tmpUsers,_tmpMessages);
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
