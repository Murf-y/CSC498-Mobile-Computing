package com.murfy.mews.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.murfy.mews.Adapters.PostAdapter;
import com.murfy.mews.Models.Post;
import com.murfy.mews.Models.User;
import com.murfy.mews.R;
import com.murfy.mews.Services.DatabaseService;
import com.murfy.mews.Utils.DateHelper;
import com.murfy.mews.Utils.ListViewHelper;
import com.murfy.mews.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Post> post_list;
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

        post_list = new ArrayList<Post>();
        post_list = DatabaseService.getInstance(getApplicationContext()).getAllPosts();
        PostAdapter adapter=  new PostAdapter(getApplicationContext(), R.layout.post_list_item, post_list);
        binding.postsList.setAdapter(adapter);
        binding.postsList.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), PostDetails.class);
            intent.putExtra("post", post_list.get(i));
            startActivity(intent);
        });

        ListViewHelper.getListViewSize(binding.postsList);
    }
}