package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Adapter.ArticleAdapter;
import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsCategoryActivity extends AppCompatActivity {
    private TextView categoriesTitle;
    private ListView newsListView;
    private EditText searchBox;
    private ImageView filterIcon;
    private List<Article> articleList = new ArrayList<>();
    private boolean isSortedAscending = true;
    private ArticleAdapter articleAdapter;
    private ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_category);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.newsCategory), (v, insets) -> {
            WindowInsetsCompat insetsCompat = insets;
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insetsCompat.getInsets(systemBars).left,
                    insetsCompat.getInsets(systemBars).top,
                    insetsCompat.getInsets(systemBars).right,
                    insetsCompat.getInsets(systemBars).bottom);
            return insets;
        });

        categoriesTitle = findViewById(R.id.categoriesTitle);
        newsListView = findViewById(R.id.newsListView);
        backButton = findViewById(R.id.backButton);

        int categoryId = getIntent().getIntExtra("category_id", -1);
        String categoryName = getIntent().getStringExtra("category_name");

        categoriesTitle.setText(categoryName);

        backButton.setOnClickListener(v -> finish());

        SharedPreferences sharedPreferences = getSharedPreferences("shared_user", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);

        if (username != null && password != null) {
            if (categoryId != -1) {
                loadArticlesByCategory(categoryId, username, password);
            } else {
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
            }
        }

        searchBox = findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNewsCateBySearch(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        filterIcon = findViewById(R.id.filterIcon);
        filterIcon.setOnClickListener(v -> {
            sortNewsCateByDate();
        });

    }

    private void loadArticlesByCategory(int categoryId, String username, String password) {
        ApiService apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);
        Call<List<Article>> call = apiService.getArticlesByCategory(categoryId);

        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articleList = response.body();
                    articleAdapter = new ArticleAdapter(NewsCategoryActivity.this, articleList);
                    newsListView.setAdapter(articleAdapter);

                    newsListView.setOnItemClickListener((parent, view, position, id) -> {
                        Article selectedArticle = articleList.get(position);
                        Intent intent = new Intent(NewsCategoryActivity.this, NewsDetailActivity.class);
                        intent.putExtra("article_id", selectedArticle.getId());
                        startActivity(intent);
                    });
                } else {
                    Toast.makeText(NewsCategoryActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(NewsCategoryActivity.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filterNewsCateBySearch(String query) {
        List<Article> filteredList = new ArrayList<>();
        for (Article article : articleList) {
            if (article.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(article);
            }
        }
        articleAdapter = new ArticleAdapter(this, filteredList);
        newsListView.setAdapter(articleAdapter);

        View noResultsLayout = findViewById(R.id.noResultsLayout);
        if (noResultsLayout != null) {
            noResultsLayout.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }
    private void sortNewsCateByDate() {
        if (articleList != null) {
            if (isSortedAscending) {
                Collections.sort(articleList, (a1, a2) -> a2.getCreateAt().compareTo(a1.getCreateAt()));
            } else {
                Collections.sort(articleList, (a1, a2) -> a1.getCreateAt().compareTo(a2.getCreateAt()));
            }
            isSortedAscending = !isSortedAscending;
            articleAdapter = new ArticleAdapter(this, articleList);
            newsListView.setAdapter(articleAdapter);
        }
    }
}
