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
/**
 * Màn hình CommentActivity:
 * - Hiển thị danh sách bình luận cho một bài viết cụ thể (articleId)
 * - Cho phép người dùng đăng bình luận mới
 */


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
        setContentView(R.layout.comment);  // Gắn layout từ file XML comment.xml


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.comment), (v, insets) -> {
            WindowInsetsCompat insetsCompat = insets;
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insetsCompat.getInsets(systemBars).left,
                    insetsCompat.getInsets(systemBars).top,
                    insetsCompat.getInsets(systemBars).right,
                    insetsCompat.getInsets(systemBars).bottom);
            return insets;
        });

        //  Nút quay lại
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        //khởi tạo các view và lấy thong tin đăng nhập
        listComment = findViewById(R.id.listComment);
        noResultsLayout = findViewById(R.id.noResultsLayout);

        //Lấy thông tin đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_user", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);

        //Lấy thông tin đăng nhập (username & password) được lưu trữ từ lần trước.
        apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);

        articleId = getIntent().getIntExtra("articleId", 0);
        if (articleId > 0) {
            loadComments(articleId);
        }
        //Nhận articleId từ Intent gửi sang từ activity trước.
        //Nếu có ID hợp lệ, gọi hàm loadComments() để hiển thị bình luận.

        //Lấy ô nhập nội dung bình luận và nút gửi.
        editComment = findViewById(R.id.addComment).findViewById(R.id.etComment);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {
            String content = editComment.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(CommentActivity.this, "Please enter a comment", Toast.LENGTH_SHORT).show();
                return;
            }//Kiểm tra xem người dùng đã nhập bình luận hay chưa.

            int userId = sharedPreferences.getInt("id", 0);
            if (userId == 0) {
                Toast.makeText(CommentActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
                return;
            }//Lấy userId từ SharedPreferences, nếu bằng 0 nghĩa là chưa đăng nhập.

            //Tạo một Comment mới với nội dung vừa nhập.
            Comment newComment = new Comment();
            newComment.setArticleId(articleId);
            newComment.setUserId(userId);
            newComment.setContent(content);
            newComment.setCreateAt(new Date());

            apiService.addComment(articleId, newComment).enqueue(new Callback<Comment>() { //Gửi comment thông qua apiService.addComment(...) (Retrofit).
                @Override                                                                  //Khi thành công: hiển thị thông báo, xóa nội dung input, và tải lại danh sách bình luận.
                public void onResponse(Call<Comment> call, Response<Comment> response) {   //Khi lỗi: hiển thị lỗi.
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

    private void loadComments(int articleId) {   //hàm này dùng để lấy và hiển thị danh sách bình luận cho một bài viết:
        apiService.getComments(articleId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    commentAdapter = new CommentAdapter(CommentActivity.this, response.body());  //Tạo adapter mới với danh sách bình luận nhận được.
                    listComment.setAdapter(commentAdapter);   //Gán adapter vào listComment để hiển thị danh sách bình luận trên giao diện.
                    noResultsLayout.setVisibility(View.GONE);
                    listComment.setVisibility(View.VISIBLE);  //Hiện ListView chứa bình luận.
                } else {
                    noResultsLayout.setVisibility(View.VISIBLE);     //Nếu không có bình luận hoặc phản hồi không hợp lệ → hiển thị layout "Không có bình luận".
                    listComment.setVisibility(View.GONE);            //Ẩn danh sách bình luận.
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                noResultsLayout.setVisibility(View.VISIBLE);
                listComment.setVisibility(View.GONE);
            }
        });     //Nếu có lỗi như: mất kết nối, timeout, lỗi JSON,...
    }           //Ẩn danh sách bình luận, hiện layout "Không có bình luận".
}
