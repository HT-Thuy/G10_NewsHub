package com.example.myapplication.Model;

import java.util.Date;

public class Bookmark {
    private int id;
    private User user;
    private Article article;
    private Date createAt;
    private Integer userId;
    private Integer articleId;

    public Bookmark(){

    }
    public Bookmark(int id, Integer userId, Integer articleId, User user, Article article, Date createAt) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.user = user;
        this.article = article;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
}