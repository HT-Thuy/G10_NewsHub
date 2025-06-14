package com.example.g10_newshub.models;

public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private String fullname;

    public SignUpRequest(String username, String email, String password, String fullname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }
}