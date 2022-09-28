package com.murfy.mews.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.murfy.mews.Adapters.PostAdapter;
import com.murfy.mews.Models.Post;
import com.murfy.mews.Models.User;
import com.murfy.mews.R;
import com.murfy.mews.Services.DatabaseService;
import com.murfy.mews.Utils.DateHelper;
import com.murfy.mews.Utils.ListViewHelper;
import com.murfy.mews.databinding.ActivityFavoritesBinding;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ArrayList<Post> post_list;
    private User current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFavoritesBinding binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        current_user = (User) getIntent().getSerializableExtra("user");

        String todayDate = DateHelper.getTodayDateFormatted();
        binding.todayDate.setText(todayDate == null ? "Today" : todayDate);
        binding.userName.setText(String.format("%s%s", current_user.getUserName().substring(0, 1).toUpperCase(), current_user.getUserName().substring(1)));

        binding.homeButton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("user", current_user);
            startActivity(i);
        });
        binding.addButton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AddPostActivity.class);
            i.putExtra("user", current_user);
            startActivity(i);
        });
        binding.favoriteButton.setOnClickListener(view -> {
            // Don't do anything already in favoritesPage
        });

        post_list = new ArrayList<Post>();
        post_list = DatabaseService.getInstance(getApplicationContext()).getFavouritePosts(current_user);
        PostAdapter adapter=  new PostAdapter(getApplicationContext(), R.layout.post_list_item, post_list, current_user);
        binding.postsList.setAdapter(adapter);
        binding.postsList.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), PostDetails.class);
            intent.putExtra("post_id", post_list.get(i).getPostId());
            intent.putExtra("user", current_user);
            startActivity(intent);
        });

        ListViewHelper.getListViewSize(binding.postsList);
    }
}