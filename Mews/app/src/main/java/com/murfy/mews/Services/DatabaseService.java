package com.murfy.mews.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.murfy.mews.Models.Post;
import com.murfy.mews.Models.User;

import java.util.ArrayList;

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

    public ArrayList<Post> getAllPosts(){
        ArrayList<Post> list = new ArrayList<Post>();

        try{
            Cursor cursor = db_manager.db.rawQuery("SELECT * FROM POSTS ORDER BY POSTS.created_at DESC", null);
            if (cursor.moveToFirst()){
                int post_id_idx = cursor.getColumnIndex("post_id");
                int post_title_idx = cursor.getColumnIndex("title");
                int post_content_idx = cursor.getColumnIndex("content");
                int post_created_at_idx = cursor.getColumnIndex("created_at");
                int post_location_idx = cursor.getColumnIndex("location");
                int post_author_id_idx = cursor.getColumnIndex("author_id");
                do {
                    int post_id = cursor.getInt(post_id_idx);
                    String post_title = cursor.getString(post_title_idx);
                    String post_content = cursor.getString(post_content_idx);
                    String post_created_at = cursor.getString(post_created_at_idx);
                    String post_location = cursor.getString(post_location_idx);
                    int author_id = cursor.getInt(post_author_id_idx);
                    list.add(new Post(post_id, post_title, post_content, post_created_at, post_location, author_id));
                    Log.i("Helllo", "hello");
                } while(cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
