package com.example.g10_newshub.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.g10_newshub.models.Article;
import com.example.g10_newshub.repositories.ArticleRepository;
public class ArticleDetailViewModel extends AndroidViewModel {
    private ArticleRepository repository;
    public ArticleDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleRepository(application);
    }
    public LiveData<Boolean> isArticleSaved(int articleId) { return repository.isArticleSaved(articleId); }
    public void saveArticle(Article article) { repository.saveArticleOffline(article); }
    public void unsaveArticle(int articleId) { repository.deleteArticleOffline(articleId); }
}
