package com.example.spring_api.repository;

import com.example.spring_api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByArticleId(Integer articleId);
    int countByArticleId(Integer articleId);
}
