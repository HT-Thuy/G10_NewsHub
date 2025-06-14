package com.example.g10_newshub.repositories;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.g10_newshub.data.local.AppDatabase;
import com.example.g10_newshub.data.local.ArticleDao;
import com.example.g10_newshub.data.local.ArticleEntity;
import com.example.g10_newshub.models.Article;
import com.example.g10_newshub.network.ApiClient;
import com.example.g10_newshub.network.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    private ApiService apiService;
    private ArticleDao articleDao;
    private LiveData<List<ArticleEntity>> allSavedArticles;

    public ArticleRepository(Application application) {
        apiService = ApiClient.getClient().create(ApiService.class);
        AppDatabase db = AppDatabase.getDatabase(application);
        articleDao = db.articleDao();
        allSavedArticles = articleDao.getSavedArticles();
    }

    public LiveData<List<Article>> getArticlesFromApi() {
        MutableLiveData<List<Article>> data = new MutableLiveData<>();
        apiService.getAllArticles().enqueue(new Callback<List<Article>>() {
            @Override public void onResponse(Call<List<Article>> call, Response<List<Article>> response) { data.setValue(response.body()); }
            @Override public void onFailure(Call<List<Article>> call, Throwable t) { data.setValue(null); }
        });
        return data;
    }

    public LiveData<List<ArticleEntity>> getSavedArticles() { return allSavedArticles; }
    public void saveArticleOffline(Article article) {
        ArticleEntity entity = new ArticleEntity();
        entity.id = article.getId();
        entity.title = article.getTitle();
        entity.image = article.getImage();
        entity.content = article.getContent();
        entity.categoryName = article.getCategory() != null ? article.getCategory().getCategoryName() : "Chung";
        entity.savedAt = System.currentTimeMillis();
        new SaveArticleAsyncTask(articleDao).execute(entity);
    }
    public void deleteArticleOffline(int articleId) { new DeleteArticleAsyncTask(articleDao).execute(articleId); }
    public LiveData<Boolean> isArticleSaved(int articleId) {
        MutableLiveData<Boolean> isSaved = new MutableLiveData<>();
        new IsSavedAsyncTask(articleDao, isSaved).execute(articleId);
        return isSaved;
    }

    private static class SaveArticleAsyncTask extends AsyncTask<ArticleEntity, Void, Void> {
        private ArticleDao dao;
        private SaveArticleAsyncTask(ArticleDao dao) { this.dao = dao; }
        @Override protected Void doInBackground(ArticleEntity... articles) { dao.saveArticle(articles[0]); return null; }
    }
    private static class DeleteArticleAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ArticleDao dao;
        private DeleteArticleAsyncTask(ArticleDao dao) { this.dao = dao; }
        @Override protected Void doInBackground(Integer... integers) { dao.deleteArticle(integers[0]); return null; }
    }
    private static class IsSavedAsyncTask extends AsyncTask<Integer, Void, Boolean> {
        private ArticleDao dao; private MutableLiveData<Boolean> isSaved;
        private IsSavedAsyncTask(ArticleDao dao, MutableLiveData<Boolean> liveData) { this.dao = dao; this.isSaved = liveData; }
        @Override protected Boolean doInBackground(Integer... integers) { return dao.isArticleSaved(integers[0]) > 0; }
        @Override protected void onPostExecute(Boolean result) { isSaved.setValue(result); }
    }
}