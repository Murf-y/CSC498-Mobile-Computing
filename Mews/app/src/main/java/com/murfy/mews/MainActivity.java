package com.murfy.mews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.murfy.mews.Models.User;

public class MainActivity extends AppCompatActivity {

    private User current_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current_user = (User) getIntent().getSerializableExtra("user");

        Log.i("DEBUG: ", current_user.getUserName());
    }
}