package com.example.smiletalk;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
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
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `users` (`username`,`password`,`displayName`,`profilePic`,`token`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getUsername() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUsername());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPassword());
        }
        if (value.getDisplayName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDisplayName());
        }
        if (value.getProfilePic() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getProfilePic());
        }
        if (value.getToken() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getToken());
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `users` WHERE `username` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getUsername() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUsername());
        }
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `users` SET `username` = ?,`password` = ?,`displayName` = ?,`profilePic` = ?,`token` = ? WHERE `username` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getUsername() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUsername());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPassword());
        }
        if (value.getDisplayName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDisplayName());
        }
        if (value.getProfilePic() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getProfilePic());
        }
        if (value.getToken() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getToken());
        }
        if (value.getUsername() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getUsername());
        }
      }
    };
  }

  @Override
  public void insert(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handleMultiple(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUser.handleMultiple(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<User> index() {
    final String _sql = "SELECT * FROM users";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfProfilePic = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePic");
      final int _cursorIndexOfToken = CursorUtil.getColumnIndexOrThrow(_cursor, "token");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpDisplayName;
        if (_cursor.isNull(_cursorIndexOfDisplayName)) {
          _tmpDisplayName = null;
        } else {
          _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        }
        final String _tmpProfilePic;
        if (_cursor.isNull(_cursorIndexOfProfilePic)) {
          _tmpProfilePic = null;
        } else {
          _tmpProfilePic = _cursor.getString(_cursorIndexOfProfilePic);
        }
        _item = new User(_tmpUsername,_tmpPassword,_tmpDisplayName,_tmpProfilePic);
        final String _tmpToken;
        if (_cursor.isNull(_cursorIndexOfToken)) {
          _tmpToken = null;
        } else {
          _tmpToken = _cursor.getString(_cursorIndexOfToken);
        }
        _item.setToken(_tmpToken);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public User get(final String username) {
    final String _sql = "SELECT * FROM users WHERE userName = ?";
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
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfProfilePic = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePic");
      final int _cursorIndexOfToken = CursorUtil.getColumnIndexOrThrow(_cursor, "token");
      final User _result;
      if(_cursor.moveToFirst()) {
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpDisplayName;
        if (_cursor.isNull(_cursorIndexOfDisplayName)) {
          _tmpDisplayName = null;
        } else {
          _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        }
        final String _tmpProfilePic;
        if (_cursor.isNull(_cursorIndexOfProfilePic)) {
          _tmpProfilePic = null;
        } else {
          _tmpProfilePic = _cursor.getString(_cursorIndexOfProfilePic);
        }
        _result = new User(_tmpUsername,_tmpPassword,_tmpDisplayName,_tmpProfilePic);
        final String _tmpToken;
        if (_cursor.isNull(_cursorIndexOfToken)) {
          _tmpToken = null;
        } else {
          _tmpToken = _cursor.getString(_cursorIndexOfToken);
        }
        _result.setToken(_tmpToken);
      } else {
        _result = null;
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
