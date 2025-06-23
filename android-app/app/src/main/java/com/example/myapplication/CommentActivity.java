package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapplication.Adapter.CommentAdapter;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.Comment;
import com.google.android.material.button.MaterialButton;
import com.example.myapplication.Api.ApiService;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    private ListView listComment;
    private RelativeLayout noResultsLayout;
    private CommentAdapter commentAdapter;
    private EditText editComment;
    private MaterialButton btnSend;
    private int articleId;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.comment), (v, insets) -> {
            WindowInsetsCompat insetsCompat = insets;
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insetsCompat.getInsets(systemBars).left,
                    insetsCompat.getInsets(systemBars).top,
                    insetsCompat.getInsets(systemBars).right,
                    insetsCompat.getInsets(systemBars).bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        listComment = findViewById(R.id.listComment);
        noResultsLayout = findViewById(R.id.noResultsLayout);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_user", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);

        articleId = getIntent().getIntExtra("articleId", 0);
        if (articleId > 0) {
            loadComments(articleId);
        }

        editComment = findViewById(R.id.addComment).findViewById(R.id.etComment);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {
            String content = editComment.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(CommentActivity.this, "Please enter a comment", Toast.LENGTH_SHORT).show();
                return;
            }
            int userId = sharedPreferences.getInt("id", 0);
            if (userId == 0) {
                Toast.makeText(CommentActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
                return;
            }
            Comment newComment = new Comment();
            newComment.setArticleId(articleId);
            newComment.setUserId(userId);
            newComment.setContent(content);
            newComment.setCreateAt(new Date());

            apiService.addComment(articleId, newComment).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(CommentActivity.this, "Comment posted successfully", Toast.LENGTH_SHORT).show();
                        editComment.setText("");
                        loadComments(articleId);
                    } else {
                        Toast.makeText(CommentActivity.this, "Post comment failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Toast.makeText(CommentActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadComments(int articleId) {
        apiService.getComments(articleId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    commentAdapter = new CommentAdapter(CommentActivity.this, response.body());
                    listComment.setAdapter(commentAdapter);
                    noResultsLayout.setVisibility(View.GONE);
                    listComment.setVisibility(View.VISIBLE);
                } else {
                    noResultsLayout.setVisibility(View.VISIBLE);
                    listComment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                noResultsLayout.setVisibility(View.VISIBLE);
                listComment.setVisibility(View.GONE);
            }
        });
    }
}
