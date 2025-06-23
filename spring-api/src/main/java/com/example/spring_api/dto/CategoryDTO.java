package com.example.spring_api.dto;

import java.util.List;

public class CategoryDTO {
    private Integer id;
    private String categoryName;
    private String image;
    private String createAt;

    private List<ArticleDTO> articles;

    public CategoryDTO() {

    }

    public CategoryDTO(Integer id, String categoryName, String image, String createAt) {
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public List<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }
}