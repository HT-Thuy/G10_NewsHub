package com.example.g10_newshub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.g10_newshub.R;
import com.example.g10_newshub.adapters.ArticleAdapter;
import com.example.g10_newshub.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progress_bar_home);
        recyclerView = view.findViewById(R.id.recycler_view_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ArticleAdapter(getContext());
        recyclerView.setAdapter(adapter);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getAllArticles().observe(getViewLifecycleOwner(), articles -> {
            progressBar.setVisibility(View.GONE);
            if (articles != null) {
                adapter.setArticles(articles);
            } else {
                Toast.makeText(getContext(), "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemClickListener(article -> {
            // Xử lý sự kiện khi một bài viết được nhấn
            // Ví dụ: mở ArticleDetailActivity
            // Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            // intent.putExtra("ARTICLE_ID", article.getId());
            // startActivity(intent);
        });

        return view;
    }
}
