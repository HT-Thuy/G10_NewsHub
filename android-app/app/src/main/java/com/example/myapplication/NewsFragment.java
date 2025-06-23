package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class NewsFragment extends Fragment {
    private ListView newsListView;
    private ArticleAdapter articleAdapter;
    private ApiService apiService;
    private EditText searchBox;
    private ImageView filterIcon;
    private List<Article> originalArticleList;
    private boolean isSortedAscending = false;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsListView = view.findViewById(R.id.newsListView);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared_user", getContext().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);

        fetchArticles();

        searchBox = view.findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterArticles(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        filterIcon = view.findViewById(R.id.filterIcon);
        filterIcon.setOnClickListener(v -> sortArticlesByDate());

        return view;
    }

    private void fetchArticles() {
        apiService.getAllArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    originalArticleList = response.body();
                    articleAdapter = new ArticleAdapter(getContext(), originalArticleList);
                    newsListView.setAdapter(articleAdapter);
                } else {
                    Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                }
                newsListView.setOnItemClickListener((parent, view, position, id) -> {
                    Article selectedArticle = (Article) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                    intent.putExtra("article_id", selectedArticle.getId());
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void filterArticles(String keyword) {
        List<Article> filteredList = new ArrayList<>();
        for (Article article : originalArticleList) {
            if (article.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(article);
            }
        }
        if (articleAdapter != null) {
            articleAdapter.updateData(filteredList);
        }

        RelativeLayout noResultsLayout = getView().findViewById(R.id.noResultsLayout);
        if (noResultsLayout != null) {
            noResultsLayout.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    private void sortArticlesByDate() {
        if (originalArticleList != null) {
            if (isSortedAscending) {
                Collections.sort(originalArticleList, (a1, a2) -> a2.getCreateAt().compareTo(a1.getCreateAt()));
            } else {
                Collections.sort(originalArticleList, (a1, a2) -> a1.getCreateAt().compareTo(a2.getCreateAt()));
            }
            isSortedAscending = !isSortedAscending;
            articleAdapter.updateData(originalArticleList);
        }
    }
}