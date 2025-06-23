package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOtp extends AppCompatActivity {
    EditText digit1, digit2, digit3, digit4;
    Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_otp);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.checkOtp), (v, insets) -> {
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insets.getInsets(systemBars).left,
                    insets.getInsets(systemBars).top,
                    insets.getInsets(systemBars).right,
                    insets.getInsets(systemBars).bottom);
            return insets;
        });
        digit1 = findViewById(R.id.otp1);
        digit2 = findViewById(R.id.otp2);
        digit3 = findViewById(R.id.otp3);
        digit4 = findViewById(R.id.otp4);
        btnVerify = findViewById(R.id.btnSignIn);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        btnVerify.setOnClickListener(v -> verifyOtp());
    }

    private void verifyOtp() {
        String otp = digit1.getText().toString().trim() +
                digit2.getText().toString().trim() +
                digit3.getText().toString().trim() +
                digit4.getText().toString().trim();

        if (otp.length() != 4) {
            Toast.makeText(this, "Please enter a valid 4-digit OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("otp", otp);

        Call<ResponseBody> call = apiService.verifyOtp(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.isSuccessful() ? response.body().string() : response.errorBody().string();
                    if (response.isSuccessful() && message.equals("OTP verified successfully")) {
                        Intent intent = new Intent(CheckOtp.this, SetNewPassword.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CheckOtp.this, "OTP verification failed: " + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CheckOtp.this, "Error reading response", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CheckOtp.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
