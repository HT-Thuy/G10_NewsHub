package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Adapter.ArticleAdapter;
import com.example.myapplication.Adapter.SliderAdapter;
import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.Article;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private ViewPager2 viewPager;
    private ListView newsListView;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        newsListView = view.findViewById(R.id.newsListView);

        TextView txtDateNow = view.findViewById(R.id.txtDateNow);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMMM, yyyy", Locale.ENGLISH);
        String currentDate = sdf.format(new Date());
        txtDateNow.setText(currentDate);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared_user", getContext().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);

        loadSliderData();
        loadLatestNews();

        return view;
    }

    private void loadSliderData() {
        apiService.getSliderArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SliderAdapter sliderAdapter = new SliderAdapter(response.body(), getContext());
                    viewPager.setAdapter(sliderAdapter);
                    sliderAdapter.setOnItemClickListener(article -> {
                        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                        intent.putExtra("article_id", article.getId());
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadLatestNews() {
        apiService.getLatestArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArticleAdapter articleAdapter = new ArticleAdapter(getContext(), response.body());
                    newsListView.setAdapter(articleAdapter);
                    newsListView.setOnItemClickListener((parent, view, position, id) -> {
                        Article selectedArticle = (Article) parent.getItemAtPosition(position);
                        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                        intent.putExtra("article_id", selectedArticle.getId());
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}