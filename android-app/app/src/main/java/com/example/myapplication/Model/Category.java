package com.example.myapplication.Model;

import java.util.Date;

public class Category {
    private Integer id;
    private String categoryName;
    private String image;
    private Date createAt;

    public Category(Integer id, String categoryName, String image, Date createAt) {
        this.id = id;
        this.categoryName = categoryName;
        this.image = image;
        this.createAt = createAt;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
