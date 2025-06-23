package com.example.spring_api.model;

import jakarta.persistence.*;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    

    public Comment() {}

    public Comment(Integer articleId, Integer userId, String content, Date createAt) {
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
        this.createAt = createAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
