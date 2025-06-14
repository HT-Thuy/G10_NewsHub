package com.example.g10_newshub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.g10_newshub.R;
import com.example.g10_newshub.models.Article;
import com.example.g10_newshub.viewmodels.ArticleDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ArticleDetailActivity extends AppCompatActivity {

    public static final String ARTICLE_ID_KEY = "ARTICLE_ID";
    private ArticleDetailViewModel viewModel;
    private TextView titleTextView, contentTextView;
    private ImageView detailImageView;
    private FloatingActionButton bookmarkFab;
    private boolean isCurrentlySaved = false;
    private Article currentArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        titleTextView = findViewById(R.id.text_view_detail_title);
        contentTextView = findViewById(R.id.text_view_detail_content);
        detailImageView = findViewById(R.id.image_view_detail);
        bookmarkFab = findViewById(R.id.fab_bookmark);

        viewModel = new ViewModelProvider(this).get(ArticleDetailViewModel.class);

        int articleId = getIntent().getIntExtra(ARTICLE_ID_KEY, -1);
        if (articleId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy bài viết.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Lấy chi tiết bài viết từ API
        viewModel.getArticleDetails(articleId).observe(this, article -> {
            if (article != null) {
                currentArticle = article;
                titleTextView.setText(article.getTitle());
                contentTextView.setText(article.getContent());
                Glide.with(this).load(article.getImage()).into(detailImageView);
            } else {
                Toast.makeText(this, "Không thể tải chi tiết bài viết.", Toast.LENGTH_SHORT).show();
            }
        });

        // Kiểm tra trạng thái lưu
        viewModel.isArticleSaved(articleId).observe(this, isSaved -> {
            isCurrentlySaved = isSaved;
            updateBookmarkIcon(isSaved);
        });

        // Xử lý sự kiện nút lưu
        bookmarkFab.setOnClickListener(v -> {
            if (currentArticle == null) return;
            if (isCurrentlySaved) {
                viewModel.unsaveArticle(currentArticle.getId());
            } else {
                viewModel.saveArticle(currentArticle);
            }
        });

        // Thêm chức năng chia sẻ
        // ...
    }

    private void updateBookmarkIcon(boolean isSaved) {
        if (isSaved) {
            bookmarkFab.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            bookmarkFab.setImageResource(R.drawable.ic_bookmark_border);
        }
    }
}
