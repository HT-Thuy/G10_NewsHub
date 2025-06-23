package com.example.spring_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.spring_api.model.Category;
import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Integer>{
    List<Category> findAll();
    Category findByCategoryName(String categoryName);
}
