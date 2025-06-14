package com.example.g10_newshub.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;
@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) void saveArticle(ArticleEntity article);
    @Query("SELECT * FROM saved_articles ORDER BY savedAt DESC") LiveData<List<ArticleEntity>> getSavedArticles();
    @Query("DELETE FROM saved_articles WHERE id = :articleId") void deleteArticle(int articleId);
    @Query("SELECT COUNT(*) FROM saved_articles WHERE id = :articleId") int isArticleSaved(int articleId);
}