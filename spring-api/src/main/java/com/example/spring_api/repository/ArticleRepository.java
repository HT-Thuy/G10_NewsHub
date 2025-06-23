package com.example.spring_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.spring_api.model.Article;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer>{
    int countByCategoryId(Integer categoryId);
    List<Article> findByCategoryId(Integer categoryId);
    List<Article> findTop5ByOrderByIdDesc();
}
