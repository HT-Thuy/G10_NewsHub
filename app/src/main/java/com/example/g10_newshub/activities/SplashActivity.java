package com.example.g10_newshub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.g10_newshub.R;
// Giả sử có một lớp SessionManager để quản lý trạng thái đăng nhập
// import com.example.g10_newshub.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            // SessionManager sessionManager = new SessionManager(this);
            // if (sessionManager.isLoggedIn()) {
            //     startActivity(new Intent(SplashActivity.this, MainActivity.class));
            // } else {
            //     startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            // }
            // Tạm thời luôn vào Login để test
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 2000);
    }
}
