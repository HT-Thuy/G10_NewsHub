package com.example.g10_newshub.models;

import com.google.gson.annotations.SerializedName;
public class Article {
    @SerializedName("id") private int id;
    @SerializedName("title") private String title;
    @SerializedName("image") private String image;
    @SerializedName("content") private String content;
    @SerializedName("category") private Category category;
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getImage() { return image; }
    public String getContent() { return content; }
    public Category getCategory() { return category; }
}
