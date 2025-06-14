package com.example.g10_newshub.models;

import com.google.gson.annotations.SerializedName;
public class Category {
    @SerializedName("id") private int id;
    @SerializedName("categoryName") private String categoryName;
    public int getId() { return id; }
    public String getCategoryName() { return categoryName; }
}