package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailEditText;
    private MaterialButton resetPasswordButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgotPassword), (v, insets) -> {
            WindowInsetsCompat insetsCompat = insets;
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insetsCompat.getInsets(systemBars).left,
                    insetsCompat.getInsets(systemBars).top,
                    insetsCompat.getInsets(systemBars).right,
                    insetsCompat.getInsets(systemBars).bottom);
            return insets;
        });

        emailEditText = findViewById(R.id.emailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        resetPasswordButton.setOnClickListener(v -> {
            String emailInput = emailEditText.getText().toString().trim();

            if (emailInput.isEmpty()) {
                emailEditText.setError("Email is required");
                return;
            }

            sendOtpRequest(emailInput);
        });
    }

    private void sendOtpRequest(String email) {
        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        Map<String, String> body = new HashMap<>();
        body.put("email", email);

        Call<ResponseBody> call = apiService.sendOtp(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseText = response.body().string();
                        Toast.makeText(ForgotPassword.this, "OTP sent successfully", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ForgotPassword.this, CheckOtp.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ForgotPassword.this, "OTP sending failed" + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ForgotPassword.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
