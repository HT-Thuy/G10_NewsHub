package com.example.g10_newshub.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Comment {
    @SerializedName("id") private int id;
    @SerializedName("content") private String content;
    @SerializedName("createdAt") private Date createdAt;
    @SerializedName("user") private User user;

    // Getters
    public int getId() { return id; }
    public String getContent() { return content; }
    public Date getCreatedAt() { return createdAt; }
    public User getUser() { return user; }
}
