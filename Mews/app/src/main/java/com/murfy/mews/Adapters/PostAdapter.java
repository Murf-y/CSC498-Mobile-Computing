package com.murfy.mews.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.murfy.mews.Models.Post;
import com.murfy.mews.Models.User;
import com.murfy.mews.R;
import com.murfy.mews.Services.DatabaseService;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {
    private final ArrayList<Post> post_list;
    private final User current_user;
    public PostAdapter(@NonNull Context context, int resource, ArrayList<Post> post_list, User current_user) {
        super(context, resource, post_list);
        this.post_list = post_list;
        this.current_user = current_user;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, parent, false);
        }

        Post post = post_list.get(position);

        ImageView postImage = convertView.findViewById(R.id.postImageView);
        if(post.getImage() == null){
            int drawableID = getContext().getResources().getIdentifier("placeholder", "drawable", getContext().getPackageName());
            postImage.setImageResource(drawableID);
        }else{
            postImage.setImageBitmap(post.getImage());
        }

        TextView postTitle = convertView.findViewById(R.id.postTitle);
        postTitle.setText(post.getTitle());

        TextView postedAt = convertView.findViewById(R.id.postedAt);
        postedAt.setText(post.getCreatedAt());

        ImageButton favoriteButton = convertView.findViewById(R.id.favoritePostButton);
        boolean userFavouritePost = DatabaseService.getInstance(getContext()).userFavouritePost(current_user, post);
        if(userFavouritePost){
            int drawableID = getContext().getResources().getIdentifier("ic_fav_clicked", "drawable", getContext().getPackageName());
            favoriteButton.setImageResource(drawableID);
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            boolean flag = userFavouritePost;
            @Override
            public void onClick(View view) {
                if(flag){
                    DatabaseService.getInstance(getContext()).unfavoritePost(current_user, post);
                    int drawableID = getContext().getResources().getIdentifier("ic_fav", "drawable", getContext().getPackageName());
                    favoriteButton.setImageResource(drawableID);
                }
                else{
                    DatabaseService.getInstance(getContext()).favoritePost(current_user, post);
                    int drawableID = getContext().getResources().getIdentifier("ic_fav_clicked", "drawable", getContext().getPackageName());
                    favoriteButton.setImageResource(drawableID);
                }
                flag = !flag;
            }
        });

        return convertView;
    }
}
