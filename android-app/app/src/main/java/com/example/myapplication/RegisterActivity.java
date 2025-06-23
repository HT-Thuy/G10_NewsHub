package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.User;
import com.google.android.material.button.MaterialButton;

import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private MaterialButton btnSignUp;
    private RetrofitService retrofitService;
    private ApiService apiService;
    private String username, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signUp), (v, insets) -> {
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insets.getInsets(systemBars).left,
                    insets.getInsets(systemBars).top,
                    insets.getInsets(systemBars).right,
                    insets.getInsets(systemBars).bottom);
            return insets;
        });

        TextView txtSignIn = findViewById(R.id.tvSignIn);
        txtSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        apiService = new RetrofitService().getRetrofit().create(ApiService.class);

        btnSignUp.setOnClickListener(view -> {
            username = etUsername.getText().toString().trim();
            email = etEmail.getText().toString().trim();
            password = etPassword.getText().toString().trim();
            confirmPassword = etConfirmPassword.getText().toString().trim();

            if (isValidInput()) {
                registerUser();
            }
        });
    }
    private boolean isValidInput() {
        if (username.isEmpty()) {
            etUsername.setError("Username is required");
            return false;
        }
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid email format");
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }
    private void registerUser() {
        User newUser = new User(
                username,
                email,
                null,
                password,
                null,
                null,
                "user",
                new Date()
        );
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole("user");

        apiService.registerUser(newUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String message = response.body().string();
                        Toast.makeText(RegisterActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
