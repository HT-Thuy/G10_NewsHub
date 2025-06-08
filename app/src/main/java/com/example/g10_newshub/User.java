package com.example.g10_newshub;

public class User {
    private int autoIncrementId; // ID tự động tăng
    private String uid;
    private String fullName;
    private String email;
    private String role;

    public User() {}  // Firebase yêu cầu Constructor mặc định

    public User(int autoIncrementId, String uid, String fullName, String email, String role) {
        this.autoIncrementId = autoIncrementId;
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public int getAutoIncrementId() { return autoIncrementId; }
    public String getUid() { return uid; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}