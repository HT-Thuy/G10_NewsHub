package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.myapplication.Adapter.BookmarkAdapter;
import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.Article;
import com.example.myapplication.Model.Bookmark;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkFragment extends Fragment {

    private ListView newsListView;
    private RelativeLayout noBookmarkLayout;
    private BookmarkAdapter bookmarkAdapter;
    private List<Bookmark> originalBookmarkList = new ArrayList<>();
    private EditText searchBox;
    private ImageView filterIcon;
    private boolean isSortedAscending = true;
    private SharedPreferences sharedPreferences;
    private int userId;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        newsListView = view.findViewById(R.id.newsListView);
        noBookmarkLayout = view.findViewById(R.id.noBookmarkLayout);

        sharedPreferences = requireContext().getSharedPreferences("shared_user", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        userId = sharedPreferences.getInt("id", -1);

        if (username != null && password != null && userId != -1) {
            apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);
            loadBookmarks(userId);
        }

        searchBox = view.findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBookmarksBySearch(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        filterIcon = view.findViewById(R.id.filterIcon);
        filterIcon.setOnClickListener(v -> sortBookmarksByDate());

        return view;
    }

    private void loadBookmarks(int userId) {
        Call<List<Bookmark>> call = apiService.getBookmarks(userId);
        call.enqueue(new Callback<List<Bookmark>>() {
            @Override
            public void onResponse(Call<List<Bookmark>> call, Response<List<Bookmark>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    originalBookmarkList.clear();
                    originalBookmarkList.addAll(response.body());

                    if (originalBookmarkList.isEmpty()) {
                        noBookmarkLayout.setVisibility(View.VISIBLE);
                        newsListView.setVisibility(View.GONE);
                    } else {
                        noBookmarkLayout.setVisibility(View.GONE);
                        newsListView.setVisibility(View.VISIBLE);
                        bookmarkAdapter = new BookmarkAdapter(getContext(), originalBookmarkList);
                        newsListView.setAdapter(bookmarkAdapter);
                    }
                } else {
                    Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                }
                newsListView.setOnItemClickListener((parent, view, position, id) -> {
                    Bookmark bookmark = (Bookmark) parent.getItemAtPosition(position);
                    Article article = bookmark.getArticle();
                    Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                    intent.putExtra("article_id", article.getId());
                    startActivity(intent);
                });

            }

            @Override
            public void onFailure(Call<List<Bookmark>> call, Throwable t) {
                Toast.makeText(getContext(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterBookmarksBySearch(String query) {
        List<Bookmark> filteredList = new ArrayList<>();
        for (Bookmark bookmark : originalBookmarkList) {
            if (bookmark.getArticle().getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(bookmark);
            }
        }
        if (bookmarkAdapter != null) {
            bookmarkAdapter.updateData(filteredList);
        }
        RelativeLayout noResultsLayout = getView().findViewById(R.id.noResultsLayout);
        if (noResultsLayout != null) {
            noResultsLayout.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    private void sortBookmarksByDate() {
        if (originalBookmarkList != null) {
            if (isSortedAscending) {
                Collections.sort(originalBookmarkList, (a1, a2) -> a2.getCreateAt().compareTo(a1.getCreateAt()));
            } else {
                Collections.sort(originalBookmarkList, (a1, a2) -> a1.getCreateAt().compareTo(a2.getCreateAt()));
            }
            isSortedAscending = !isSortedAscending;
            bookmarkAdapter.updateData(originalBookmarkList);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = requireContext().getSharedPreferences("bookmark_pref", Context.MODE_PRIVATE);
        boolean changed = prefs.getBoolean("bookmark_changed", false);
        if (changed) {
            loadBookmarks(userId);
            prefs.edit().putBoolean("bookmark_changed", false).apply();
        }
    }
}