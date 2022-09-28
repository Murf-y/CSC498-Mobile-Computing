package com.murfy.mews.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.murfy.mews.Models.Post;
import com.murfy.mews.Models.User;
import com.murfy.mews.R;
import com.murfy.mews.Services.DatabaseService;
import com.murfy.mews.databinding.ActivityPostDetailsBinding;

public class PostDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostDetailsBinding binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);


        Post current_post = DatabaseService.getInstance(getApplicationContext()).getPostById((Integer) getIntent().getExtras().get("post_id"));
        User current_user = (User) getIntent().getSerializableExtra("user");

        binding.backButton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("user", current_user);
            startActivity(i);
        });

        boolean userFavouritePost = DatabaseService.getInstance(getApplicationContext()).userFavouritePost(current_user, current_post);
        if(userFavouritePost){
            int drawableID = getResources().getIdentifier("ic_fav_clicked", "drawable", getPackageName());
            binding.favoriteButton.setImageResource(drawableID);
        }
        binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
            boolean flag = userFavouritePost;
            @Override
            public void onClick(View view) {
                if(flag){
                    DatabaseService.getInstance(getApplicationContext()).unfavoritePost(current_user, current_post);
                    int drawableID = getResources().getIdentifier("ic_fav", "drawable", getPackageName());
                    binding.favoriteButton.setImageResource(drawableID);
                }
                else{
                    DatabaseService.getInstance(getApplicationContext()).favoritePost(current_user, current_post);
                    int drawableID = getResources().getIdentifier("ic_fav_clicked", "drawable", getPackageName());
                    binding.favoriteButton.setImageResource(drawableID);
                }
                flag = !flag;
            }
        });

        binding.postDetailsTitle.setText(current_post.getTitle());
        binding.postDetailsContent.setText(current_post.getContent());
        binding.postDetailsDate.setText(current_post.getCreatedAt());
        binding.postDetailsLocation.setText(current_post.getLocation() == null || current_post.getLocation().length() == 0 ? "Unknown Location" : current_post.getLocation());
        if(current_post.getImage() != null){
            binding.imageDetailsView.setImageBitmap(current_post.getImage());
        }
    }
}