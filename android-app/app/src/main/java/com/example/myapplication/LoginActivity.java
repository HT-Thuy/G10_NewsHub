package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.LoginRequest;
import com.example.myapplication.Model.User;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private MaterialButton btnSignIn;
    private TextView txtSignUp, txtForgot;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "shared_user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signIn), (v, insets) -> {
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insets.getInsets(systemBars).left,
                    insets.getInsets(systemBars).top,
                    insets.getInsets(systemBars).right,
                    insets.getInsets(systemBars).bottom);
            return insets;
        });

        txtSignUp = findViewById(R.id.tvSignUp);
        txtSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        txtForgot = findViewById(R.id.forgotPassword);
        txtForgot.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
            startActivity(intent);
        });

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty()) {
                etUsername.setError("Username is required");
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Password is required");
                return;
            }

            loginUser(username, password);
        });
    }

    private void loginUser(String username, String password){
        RetrofitService retrofitService = new RetrofitService(username, password);
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);
        LoginRequest loginRequest = new LoginRequest(username, password);
        apiService.login(loginRequest).enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response){
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id", user.getId());
                    editor.putString("username", username);
                    editor.putString("email", user.getEmail());
                    editor.putString("phone", user.getPhone());
                    editor.putString("fullname", user.getFullname());
                    editor.putString("password", password);
                    editor.putString("image", user.getImage());
                    editor.putString("role", user.getRole());
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
