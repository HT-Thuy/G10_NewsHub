package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetNewPassword extends AppCompatActivity {
    private EditText resetPasswordEditText, confirmPasswordEditText;
    private LinearLayout resetPasswordLayout, successLayout;
    private MaterialButton btnUpdatePassword, btnContinue;
    private String userEmail;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_new_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.set_new_password), (v, insets) -> {
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insets.getInsets(systemBars).left,
                    insets.getInsets(systemBars).top,
                    insets.getInsets(systemBars).right,
                    insets.getInsets(systemBars).bottom);
            return insets;
        });

        resetPasswordEditText = findViewById(R.id.resetPassword);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);
        btnContinue = findViewById(R.id.btnContinue);
        resetPasswordLayout = findViewById(R.id.resetPasswordLayout);
        successLayout = findViewById(R.id.successLayout);
        backButton = findViewById(R.id.backButton);
        userEmail = getIntent().getStringExtra("email");

        backButton.setOnClickListener(v -> finish());
        btnUpdatePassword.setOnClickListener(v -> {
            String newPassword = resetPasswordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                resetPasswordEditText.setError("Password is required");
                confirmPasswordEditText.setError("Confirm Password is required");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match");
                return;
            }
            sendResetPasswordRequest(userEmail, newPassword);
        });

        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(SetNewPassword.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void sendResetPasswordRequest(String email, String newPassword) {
        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        request.put("newPassword", newPassword);

        Call<ResponseBody> call = apiService.resetPassword(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        resetPasswordLayout.setVisibility(View.GONE);
                        successLayout.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Toast.makeText(SetNewPassword.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(SetNewPassword.this, errorBody, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(SetNewPassword.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SetNewPassword.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
