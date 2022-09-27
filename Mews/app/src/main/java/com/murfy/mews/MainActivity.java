package com.murfy.mews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.murfy.mews.Models.User;
import com.murfy.mews.Utils.DateHelper;
import com.murfy.mews.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private User current_user;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        current_user = (User) getIntent().getSerializableExtra("user");

        String todayDate = DateHelper.getTodayDateFormatted();
        binding.todayDate.setText(todayDate == null ? "Today" : todayDate);

        binding.userName.setText(String.format("%s%s", current_user.getUserName().substring(0, 1).toUpperCase(), current_user.getUserName().substring(1)));

        binding.homeButton.setOnClickListener(view -> {
            // Don't do anything already in homePage
        });
        binding.addButton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AddPostActivity.class);
            i.putExtra("user", current_user);
            startActivity(i);
        });
        binding.favoriteButton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), FavoritesActivity.class);
            i.putExtra("user", current_user);
            startActivity(i);
        });

    }
}