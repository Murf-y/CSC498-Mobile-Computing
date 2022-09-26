package com.murfy.mews.Models;

import java.io.Serializable;

public class Post implements Serializable {
    private final int post_id;
    private final String title;
    private final String content;
    private final String created_at;
    private final String location;
    private final int author_id;

    public Post(int post_id, String title, String content, String created_at, String location, int author_id) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.location = location;
        this.author_id = author_id;
    }

    public int getPostId() {
        return post_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getLocation() {
        return location;
    }

    public int getAuthorId() {
        return author_id;
    }
}
