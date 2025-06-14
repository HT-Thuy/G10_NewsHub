package com.example.g10_newshub.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Bookmark {
    @SerializedName("id") private int id;
    @SerializedName("createdAt") private Date createdAt;
    @SerializedName("user") private User user;
    @SerializedName("article") private Article article;

    // Getters
    public int getId() { return id; }
    public Date getCreatedAt() { return createdAt; }
    public User getUser() { return user; }
    public Article getArticle() { return article; }
}