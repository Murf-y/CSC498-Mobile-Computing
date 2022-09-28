package com.murfy.mews.Services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    private final String db_name = "COM.MURFY.MEWS.DB";

    public final SQLiteDatabase db;

    public DatabaseManager(Context applicationContext){
        db = applicationContext.openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {

        // Create Users Table
        db.execSQL("CREATE Table IF NOT EXISTS users " +
                "(user_id INTEGER PRIMARY KEY," +
                "user_name VARCHAR NOT NULL," +
                "UNIQUE(user_name)); \n");

//         Create Posts Table
        db.execSQL("CREATE Table IF NOT EXISTS posts" +
                "(post_id INTEGER PRIMARY KEY," +
                "title VARCHAR NOT NULL," +
                "content VARCHAR NOT NULL," +
                "created_at DATE NOT NULL," +
                "location VARCHAR," +
                "author_id INT NOT NULL," +
                "image_base_64 TEXT,"+
                "FOREIGN KEY(author_id)" +
                " REFERENCES users(user_id));");


        // Create Favorites Table
//        db.execSQL("CREATE Table IF NOT EXISTS Favorites" +
//                "(favorite_id SERIAL," +
//                " favorite_at DATE NOT NULL," +
//                " user_id INT NOT NULL," +
//                " post_id INT NOT NULL," +
//                " PRIMARY KEY(favorite_id)," +
//                " FOREIGN KEY(user_id)" +
//                " REFERENCES users(user_id)" +
//                " );");
    }
}
