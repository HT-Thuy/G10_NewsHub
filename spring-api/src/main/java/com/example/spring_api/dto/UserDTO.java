package com.example.spring_api.dto;

public class UserDTO {
    private Integer id;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private String role;
    private String image;

    public UserDTO() {

    }

    public UserDTO(Integer id, String username, String fullname, String email, String phone, String role, String image) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    
}
