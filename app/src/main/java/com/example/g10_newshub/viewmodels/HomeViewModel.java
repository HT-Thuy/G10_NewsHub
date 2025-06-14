package com.example.g10_newshub.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.g10_newshub.models.Article;
import com.example.g10_newshub.repositories.ArticleRepository;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private ArticleRepository repository;
    private LiveData<List<Article>> allArticles;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleRepository(application);
        allArticles = repository.getArticlesFromApi();
    }

    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }
}
