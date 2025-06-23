package com.example.spring_api.dto;

public class ArticleDTO {

    private Integer id;
    private String title;
    private String image;
    private String content;
    private String createAt;
    private String categoryName;

    public ArticleDTO() {
    }

    public ArticleDTO(Integer id, String title, String image, String content, String createAt, String categoryName) {
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
