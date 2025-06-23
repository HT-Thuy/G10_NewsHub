package com.example.myapplication.Model;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String fullname;
    private String image;
    private String role;
    private Date createAt;

    public User() {
    }

    public User(String username, String email, String phone, String password, String fullname, String image, String role, Date createAt) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.fullname = fullname;
        this.image = image;
        this.role = role;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
