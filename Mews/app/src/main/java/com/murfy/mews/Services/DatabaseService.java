package com.murfy.mews.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.murfy.mews.Models.User;

public class DatabaseService {

    private static DatabaseService instance;
    private final DatabaseManager db_manager;

    public DatabaseService(Context applicationContext){
        db_manager = new DatabaseManager(applicationContext);
    }

    public static DatabaseService getInstance(Context applicationContext){
        if(instance == null){
            instance = new DatabaseService(applicationContext);
        }
        return instance;
    }

    public User createOrGetUser(String user_name){
        try {
            Cursor cursor = db_manager.db.rawQuery("SELECT * FROM users WHERE user_name = ?", new String[]{user_name});
            if (cursor.getCount() == 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("user_name", user_name);
                db_manager.db.insertWithOnConflict("users", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            }
            Cursor new_cursor = db_manager.db.rawQuery("SELECT * FROM users WHERE user_name = ?", new String[]{user_name});
            new_cursor.moveToFirst();

            int id = new_cursor.getInt(0);
            String user_name_in_db = new_cursor.getString(1);

            cursor.close();
            new_cursor.close();
            return new User(id, user_name_in_db);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
