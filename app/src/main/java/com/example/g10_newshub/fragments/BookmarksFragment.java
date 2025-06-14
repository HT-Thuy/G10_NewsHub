package com.example.g10_newshub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.g10_newshub.R;
import com.example.g10_newshub.activities.ArticleDetailActivity;
import com.example.g10_newshub.adapters.SavedArticleAdapter;
import com.example.g10_newshub.viewmodels.BookmarksViewModel;

public class BookmarksFragment extends Fragment {

    private BookmarksViewModel bookmarksViewModel;
    private RecyclerView recyclerView;
    private SavedArticleAdapter adapter;
    private TextView noBookmarksTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        noBookmarksTextView = view.findViewById(R.id.text_view_no_bookmarks);
        recyclerView = view.findViewById(R.id.recycler_view_bookmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new SavedArticleAdapter(getContext());
        recyclerView.setAdapter(adapter);

        bookmarksViewModel = new ViewModelProvider(this).get(BookmarksViewModel.class);
        bookmarksViewModel.getSavedArticles().observe(getViewLifecycleOwner(), savedArticles -> {
            if (savedArticles == null || savedArticles.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                noBookmarksTextView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                noBookmarksTextView.setVisibility(View.GONE);
                adapter.setArticles(savedArticles);
            }
        });

        adapter.setOnItemClickListener(articleEntity -> {
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            // Vì API chi tiết cần id, chúng ta truyền id của bài viết đã lưu
            intent.putExtra("ARTICLE_ID", articleEntity.id);
            startActivity(intent);
        });

        return view;
    }
}
