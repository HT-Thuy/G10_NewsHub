package com.example.g10_newshub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.g10_newshub.R;
import com.example.g10_newshub.utils.SessionManager;
import com.example.g10_newshub.viewmodels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private TextInputEditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo SessionManager và ViewModel
        sessionManager = new SessionManager(getApplicationContext());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Ánh xạ View
        usernameEditText = findViewById(R.id.edit_text_username);
        passwordEditText = findViewById(R.id.edit_text_password);
        loginButton = findViewById(R.id.button_login);
        registerTextView = findViewById(R.id.text_view_register);

        // Xử lý sự kiện nhấn nút Đăng nhập
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            authViewModel.login(username, password);
        });

        // Xử lý sự kiện chuyển sang trang Đăng ký
        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Lắng nghe kết quả từ ViewModel
        authViewModel.getAuthenticationResult().observe(this, user -> {
            if (user != null) {
                // Lưu thông tin session khi đăng nhập thành công
                sessionManager.createLoginSession(user.getId(), user.getUsername(), user.getFullname());

                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                // Chuyển sang MainActivity và xóa các activity trước đó khỏi stack
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                // Hiển thị thông báo lỗi khi đăng nhập thất bại
                Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}