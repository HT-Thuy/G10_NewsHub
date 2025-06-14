package com.example.g10_newshub.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.g10_newshub.data.local.ArticleEntity;
import com.example.g10_newshub.repositories.ArticleRepository;
import java.util.List;

public class BookmarksViewModel extends AndroidViewModel {
    private ArticleRepository repository;
    private LiveData<List<ArticleEntity>> savedArticles;

    public BookmarksViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleRepository(application);
        savedArticles = repository.getSavedArticles();
    }

    public LiveData<List<ArticleEntity>> getSavedArticles() {
        return savedArticles;
    }
}
