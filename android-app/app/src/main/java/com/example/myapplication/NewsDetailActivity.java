package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.Article;
import com.example.myapplication.Model.Bookmark;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity {
    private ImageView newsImage;
    private TextView tvCategory, tvTitle, tvDate, tvContent;
    private ImageButton backButton;
    private ImageView btnBookmark, btnShare, btnComment;
    private int userId, articleId;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        newsImage = findViewById(R.id.newsImage);
        tvCategory = findViewById(R.id.tvCategory);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvContent = findViewById(R.id.tvContent);
        backButton = findViewById(R.id.backButton);
        btnComment = findViewById(R.id.btnComment);
        backButton.setOnClickListener(v -> finish());

        SharedPreferences sharedPreferences = getSharedPreferences("shared_user", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        userId = sharedPreferences.getInt("id", -1);

        articleId = getIntent().getIntExtra("article_id", -1);
        if (username != null && password != null) {
            if (articleId != -1) {
                loadArticleDetail(articleId, username, password);
            } else {
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
            }
            btnComment.setOnClickListener(view -> {
                Intent intent = new Intent(NewsDetailActivity.this, CommentActivity.class);
                intent.putExtra("articleId", articleId);
                startActivity(intent);
            });
        }

        btnBookmark = findViewById(R.id.btnBookmark);
        btnBookmark.setOnClickListener(v -> {
            if (userId != -1 && articleId != -1) {
                ApiService apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);

                apiService.getBookmarks(userId).enqueue(new Callback<List<Bookmark>>() {
                    @Override
                    public void onResponse(Call<List<Bookmark>> call, Response<List<Bookmark>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Bookmark> bookmarks = response.body();
                            Optional<Bookmark> existingBookmark = bookmarks.stream()
                                    .filter(b -> b.getArticle().getId() == articleId && b.getUser().getId() == userId)
                                    .findFirst();

                            if (existingBookmark.isPresent()) {
                                int bookmarkId = existingBookmark.get().getId();
                                apiService.deleteBookmark(bookmarkId).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(NewsDetailActivity.this, "Bookmark removed", Toast.LENGTH_SHORT).show();
                                            btnBookmark.setColorFilter(null);
                                            SharedPreferences prefs = getSharedPreferences("bookmark_pref", MODE_PRIVATE);
                                            prefs.edit().putBoolean("bookmark_changed", true).apply();
                                        } else {
                                            Toast.makeText(NewsDetailActivity.this, "Failed to remove bookmark", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(NewsDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Bookmark bookmark = new Bookmark();
                                bookmark.setUserId(userId);
                                bookmark.setArticleId(articleId);
                                bookmark.setCreateAt(new Date());

                                apiService.sendBookmark(bookmark).enqueue(new Callback<Bookmark>() {
                                    @Override
                                    public void onResponse(Call<Bookmark> call, Response<Bookmark> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(NewsDetailActivity.this, "Article saved", Toast.LENGTH_SHORT).show();
                                            btnBookmark.setColorFilter(Color.parseColor("#648DDB"));
                                        } else {
                                            Toast.makeText(NewsDetailActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Bookmark> call, Throwable t) {
                                        Toast.makeText(NewsDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Bookmark>> call, Throwable t) {
                        Toast.makeText(NewsDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> shareArticle());

        ApiService apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);
        apiService.getBookmarks(userId).enqueue(new Callback<List<Bookmark>>() {
            @Override
            public void onResponse(Call<List<Bookmark>> call, Response<List<Bookmark>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean isBookmarked = response.body().stream()
                            .anyMatch(b -> b.getArticle().getId() == articleId && b.getUser().getId() == userId);
                    if (isBookmarked) {
                        btnBookmark.setColorFilter(Color.parseColor("#648DDB"));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Bookmark>> call, Throwable t) {
                Toast.makeText(NewsDetailActivity.this, "Error checking bookmark: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadArticleDetail(int articleId, String username, String password) {
        ApiService apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);

        Call<Article> call = apiService.getArticleById(articleId);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Article article = response.body();
                    tvTitle.setText(article.getTitle());
                    tvContent.setText(article.getContent());

                    if (article.getCreateAt() != null) {
                        tvDate.setText(dateFormat.format(article.getCreateAt()));
                    } else {
                        tvDate.setText("Unknown Date");
                    }

                    if (article.getCategoryName() != null) {
                        tvCategory.setText(article.getCategoryName());
                    } else {
                        tvCategory.setText("Unknown Category");
                    }
                    Glide.with(NewsDetailActivity.this)
                            .load("http://192.168.2.13:8080/" + article.getImage())
                            .placeholder(R.drawable.placeholder)
                            .into(newsImage);
                } else {
                    Toast.makeText(NewsDetailActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Toast.makeText(NewsDetailActivity.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void shareArticle() {
        String title = tvTitle.getText().toString();
        String content = tvContent.getText().toString();

        String shareText = title + "\n\n" + content;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
