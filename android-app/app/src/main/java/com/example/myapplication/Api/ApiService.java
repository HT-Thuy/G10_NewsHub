package com.example.myapplication.Api;

import com.example.myapplication.Model.Article;
import com.example.myapplication.Model.Bookmark;
import com.example.myapplication.Model.Category;
import com.example.myapplication.Model.Comment;
import com.example.myapplication.Model.LoginRequest;
import com.example.myapplication.Model.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @GET("get-all-categories")
    Call<List<Category>> getAllCategories();
    @GET("{id}/article-count")
    Call<Integer> getArticleCount(@Path("id") int categoryId);
    @GET("get-all-articles")
    Call<List<Article>> getAllArticles();
    @GET("get-articles/{id}")
    Call<Article> getArticleById(@Path("id") int id);
    @GET("article-slider")
    Call<List<Article>> getSliderArticles();
    @GET("article-latest")
    Call<List<Article>> getLatestArticles();
    @GET("get-articles-by-category/{id}")
    Call<List<Article>> getArticlesByCategory(@Path("id") int categoryId);
    @GET("get-comments/{articleId}")
    Call<List<Comment>> getComments(@Path("articleId") int newsId);
    @GET("get-bookmark/{userId}")
    Call<List<Bookmark>> getBookmarks(@Path("userId") int userId);
    @GET("get-user/{id}")
    Call<User> getUserById(@Path("id") int userId);
    @POST("sendBookmark")
    Call<Bookmark> sendBookmark(@Body Bookmark bookmark);
    @DELETE("deleteBookmark/{id}")
    Call<Void> deleteBookmark(@Path("id") int bookmarkId);
    @POST("{articleId}/comments")
    Call<Comment> addComment(@Path("articleId") int articleId, @Body Comment comment);
    @POST("login")
    Call<User> login(@Body LoginRequest loginRequest);
    @POST("logout")
    Call<Void> logout();
    @POST("register")
    Call<ResponseBody> registerUser(@Body User user);
    @POST("send-otp")
    Call<ResponseBody> sendOtp(@Body Map<String, String> emailMap);
    @POST("verify-otp")
    Call<ResponseBody> verifyOtp(@Body Map<String, String> request);
    @POST("reset-password")
    Call<ResponseBody> resetPassword(@Body Map<String, String> body);
    @Multipart
    @PUT("update-profile/{id}")
    Call<Void> updateUser(
            @Path("id") int id,
            @Part("user") RequestBody userJson,
            @Part MultipartBody.Part image
    );

}
