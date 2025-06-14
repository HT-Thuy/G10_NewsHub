package com.example.g10_newshub.network;

import com.example.g10_newshub.models.Article;
import com.example.g10_newshub.models.Category;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface ApiService {
    @GET("api/articles") Call<List<Article>> getAllArticles();
    @GET("api/articles/{id}") Call<Article> getArticleDetails(@Path("id") int articleId);
    @GET("api/categories") Call<List<Category>> getAllCategories();
}
