package com.example.myapplication.Model;

import java.util.Date;

public class Article {
    private Integer id;
    private Integer categoryId;
    private String title;
    private String image;
    private String content;
    private Date createAt;
    private String categoryName;

    public Article(Integer categoryId, Integer id, String title, String image, String content, Date createAt, String categoryName) {
        this.categoryId = categoryId;
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.createAt = createAt;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}