package com.example.g10_newshub.network;

import com.example.g10_newshub.models.Article;
import com.example.g10_newshub.models.Bookmark;
import com.example.g10_newshub.models.Category;
import com.example.g10_newshub.models.Comment;
import com.example.g10_newshub.models.LoginRequest;
import com.example.g10_newshub.models.SignUpRequest;
import com.example.g10_newshub.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // Auth
    @POST("api/auth/login")
    Call<User> loginUser(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<User> registerUser(@Body SignUpRequest signUpRequest);

    // Articles
    @GET("api/articles")
    Call<List<Article>> getAllArticles();

    @GET("api/articles/{id}")
    Call<Article> getArticleDetails(@Path("id") int articleId);

    @GET("api/articles/search")
    Call<List<Article>> searchArticles(@Query("keyword") String keyword);

    // Categories
    @GET("api/categories")
    Call<List<Category>> getAllCategories();

    // Comments
    @GET("api/articles/{articleId}/comments")
    Call<List<Comment>> getCommentsForArticle(@Path("articleId") int articleId);

    @POST("api/comments")
    Call<Comment> postComment(@Body Comment comment);

    // Bookmarks
    @GET("api/users/{userId}/bookmarks")
    Call<List<Bookmark>> getBookmarksForUser(@Path("userId") int userId);

    @POST("api/bookmarks")
    Call<Bookmark> createBookmark(@Body Bookmark bookmark);

    @DELETE("api/bookmarks/{bookmarkId}")
    Call<Void> deleteBookmark(@Path("bookmarkId") int bookmarkId);

    // User Profile
    @GET("api/users/{id}")
    Call<User> getUserProfile(@Path("id") int userId);

    @PUT("api/users/{id}")
    Call<User> updateUserProfile(@Path("id") int userId, @Body User user);
}
