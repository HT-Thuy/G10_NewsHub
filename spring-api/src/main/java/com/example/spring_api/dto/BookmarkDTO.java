package com.example.spring_api.dto;

public class BookmarkDTO {

    private Integer id;
    private UserDTO user;
    private ArticleDTO article;
    private String createAt;

    public BookmarkDTO(Integer id, UserDTO user, ArticleDTO article, String createAt) {
        this.id = id;
        this.user = user;
        this.article = article;
        this.createAt = createAt;
    }

    public Integer getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public ArticleDTO getArticle() {
        return article;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
