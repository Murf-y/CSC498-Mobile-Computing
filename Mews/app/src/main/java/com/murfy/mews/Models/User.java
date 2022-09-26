package com.murfy.mews.Models;

import java.io.Serializable;

public class User implements Serializable {
    private final String user_name;
    private final int user_id;

    public User(int id, String name){
        user_name = name;
        user_id = id;
    }
    public int getUserId() {
        return user_id;
    }

    public String getUserName() {
        return user_name;
    }
}
