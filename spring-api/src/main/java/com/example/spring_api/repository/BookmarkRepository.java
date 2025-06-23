package com.example.spring_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.spring_api.model.Bookmark;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    List<Bookmark> findByUserId(Integer userId);    
}