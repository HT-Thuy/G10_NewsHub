package com.example.g10_newshub.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.g10_newshub.R;
import com.example.g10_newshub.viewmodels.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private EditText fullnameET, usernameET, emailET, passwordET;
    private Button registerButton;
    private TextView loginInsteadTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // ... Ánh xạ các view ...

        registerButton.setOnClickListener(v -> {
            // ... Lấy dữ liệu và gọi authViewModel.register(...) ...
        });

        loginInsteadTextView.setOnClickListener(v -> finish());

        authViewModel.getAuthenticationResult().observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "Đăng ký thành công! Vui lòng đăng nhập.", Toast.LENGTH_LONG).show();
                finish(); // Quay lại màn hình đăng nhập
            } else {
                Toast.makeText(this, "Đăng ký thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}