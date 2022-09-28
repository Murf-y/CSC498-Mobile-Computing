package com.murfy.mews.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.murfy.mews.Models.Post;
import com.murfy.mews.R;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {
    private final ArrayList<Post> post_list;

    public PostAdapter(@NonNull Context context, int resource, ArrayList<Post> post_list) {
        super(context, resource, post_list);
        this.post_list = post_list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, parent, false);
        }

        ImageView postImage = convertView.findViewById(R.id.postImageView);
        int drawableID = getContext().getResources().getIdentifier("placeholder", "drawable", getContext().getPackageName());
        postImage.setImageResource(drawableID);

        TextView postTitle = convertView.findViewById(R.id.postTitle);
        postTitle.setText(post_list.get(position).getTitle());

        TextView postedAt = convertView.findViewById(R.id.postedAt);
        postedAt.setText(post_list.get(position).getCreatedAt());
        return convertView;
    }
}
