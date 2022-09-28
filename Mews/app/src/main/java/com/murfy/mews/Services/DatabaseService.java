package com.murfy.mews.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.murfy.mews.Models.Post;
import com.murfy.mews.Models.User;
import com.murfy.mews.Utils.DateHelper;
import com.murfy.mews.Utils.ImageEncoding;

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
                int post_image_idx = cursor.getColumnIndex("image_base_64");
                do {
                    int post_id = cursor.getInt(post_id_idx);
                    String post_title = cursor.getString(post_title_idx);
                    String post_content = cursor.getString(post_content_idx);
                    String post_created_at = cursor.getString(post_created_at_idx);
                    String post_location = cursor.getString(post_location_idx);
                    int author_id = cursor.getInt(post_author_id_idx);
                    String image_base_64 = cursor.getString(post_image_idx);
                    Bitmap imageBitmap = ImageEncoding.convertToBitmap(image_base_64);

                    list.add(new Post(post_id, post_title, post_content, post_created_at, post_location, author_id, imageBitmap));
                } while(cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public Post getPostById(int id){
        Cursor cursor = db_manager.db.rawQuery("SELECT * FROM POSTS WHERE post_id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        if(cursor.getCount() == 0) return null;
        Post post =   new Post(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), ImageEncoding.convertToBitmap(cursor.getString(6)));
        cursor.close();
        return  post;
    }
    public void addPostToDb(User user, String title, String content, String location, Bitmap imageBitMap){

        String image_base_64 = null;
        if(imageBitMap != null){
            image_base_64 = ImageEncoding.convertToBase64(imageBitMap);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("created_at", DateHelper.getTodayDateFormatted());
        contentValues.put("location", location);
        contentValues.put("author_id", user.getUserId());
        contentValues.put("image_base_64", image_base_64);
        db_manager.db.insertWithOnConflict("posts", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public boolean userFavouritePost(User user, Post post){
        Cursor cursor = db_manager.db.rawQuery("SELECT * FROM favorites WHERE user_id = ? AND post_id = ?", new String[]{String.valueOf(user.getUserId()), String.valueOf(post.getPostId())});
        boolean didFavorite = cursor.getCount() != 0;
        cursor.close();
        return didFavorite;
    }

    public void unfavoritePost(User user, Post post) {
        db_manager.db.delete("favorites", "user_id = ? AND post_id = ?", new String[] {String.valueOf(user.getUserId()), String.valueOf(post.getPostId())});
    }

    public void favoritePost(User user, Post post) {
        if(userFavouritePost(user,post)) return;
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", user.getUserId());
        contentValues.put("post_id", post.getPostId());
        contentValues.put("favorite_at", DateHelper.getTodayDateFormatted());
        db_manager.db.insertWithOnConflict("favorites", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<Post> getFavouritePosts(User user) {
        ArrayList<Post> list= new ArrayList<Post>();
        Cursor cursor = db_manager.db.rawQuery("SELECT * FROM posts JOIN favorites ON favorites.post_id = posts.post_id WHERE favorites.user_id = ?", new String[]{String.valueOf(user.getUserId())});
        if (cursor.moveToFirst()){
            int post_id_idx = cursor.getColumnIndex("post_id");
            int post_title_idx = cursor.getColumnIndex("title");
            int post_content_idx = cursor.getColumnIndex("content");
            int post_created_at_idx = cursor.getColumnIndex("created_at");
            int post_location_idx = cursor.getColumnIndex("location");
            int post_author_id_idx = cursor.getColumnIndex("author_id");
            int post_image_idx = cursor.getColumnIndex("image_base_64");
            do {
                int post_id = cursor.getInt(post_id_idx);
                String post_title = cursor.getString(post_title_idx);
                String post_content = cursor.getString(post_content_idx);
                String post_created_at = cursor.getString(post_created_at_idx);
                String post_location = cursor.getString(post_location_idx);
                int author_id = cursor.getInt(post_author_id_idx);
                String image_base_64 = cursor.getString(post_image_idx);
                Bitmap imageBitmap = ImageEncoding.convertToBitmap(image_base_64);
                list.add(new Post(post_id, post_title, post_content, post_created_at, post_location, author_id, imageBitmap));
                Log.i("DEBUG: ", String.valueOf(list.get(0).getPostId()));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
