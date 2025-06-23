package com.example.myapplication.Model;

import java.util.Date;

public class Comment {
    private int id;
    private int articleId;
    private int userId;
    private User user;
    private String content;
    private Date createAt;

    public Comment() {
    }

    public Comment(int articleId, int userId, User user, String content, Date createAt) {
        this.articleId = articleId;
        this.userId = userId;
        this.user = user;
        this.content = content;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
