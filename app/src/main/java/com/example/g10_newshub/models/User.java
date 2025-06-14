package com.example.g10_newshub.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id") private int id;
    @SerializedName("username") private String username;
    @SerializedName("fullname") private String fullname;
    @SerializedName("email") private String email;
    @SerializedName("phone") private String phone;
    @SerializedName("image") private String image;
    @SerializedName("role") private String role;

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getFullname() { return fullname; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getImage() { return image; }
    public String getRole() { return role; }
}
