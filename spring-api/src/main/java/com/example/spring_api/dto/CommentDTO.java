package com.example.spring_api.dto;

public class CommentDTO {

    private Integer id;
    private Integer articleId;
    private UserDTO user;
    private String content;
    private String createAt;

    public CommentDTO(Integer id, Integer articleId, UserDTO user, String content, String createAt) {
        this.id = id;
        this.articleId = articleId;
        this.user = user;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
