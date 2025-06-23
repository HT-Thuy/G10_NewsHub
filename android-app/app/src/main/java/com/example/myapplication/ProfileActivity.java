package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.User;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {

    private EditText etFullName, etPhone, etEmail, etUsername;
    private ImageView imgAvatar;
    private ImageButton backButton;
    private MaterialButton btnSave;
    private SharedPreferences sharedPreferences;
    private int userId;
    private ApiService apiService;
    private static final int REQUEST_CODE_PICK_IMAGE = 1001;
    private ImageView btnChangeAvatar;
    private Uri selectedImageUri;
    private String uploadedImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_information);

        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        imgAvatar = findViewById(R.id.imgAvatar);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        sharedPreferences = getSharedPreferences("shared_user", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        userId = sharedPreferences.getInt("id", -1);

        if (username != null && password != null && userId != -1) {
            apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);
            loadUserProfile(userId);
        }

        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);
        btnChangeAvatar.setOnClickListener(v -> {
            openGalleryToPickImage();
        });

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> updateUserProfile());
    }

    private void loadUserProfile(int id) {
        Call<User> call = apiService.getUserById(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    etUsername.setText(user.getUsername());
                    etFullName.setText(user.getFullname());
                    etPhone.setText(user.getPhone());
                    etEmail.setText(user.getEmail());

                    if (user.getImage() != null && !user.getImage().isEmpty()) {
                        Glide.with(ProfileActivity.this)
                                .load("http://192.168.2.13:8080/" + user.getImage())
                                .placeholder(R.drawable.avatar)
                                .circleCrop()
                                .into(imgAvatar);
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void openGalleryToPickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                Glide.with(this)
                        .load(selectedImageUri)
                        .placeholder(R.drawable.placeholder)
                        .circleCrop()
                        .into(imgAvatar);
            }
        }
    }


    private File createTempFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File tempFile = File.createTempFile("upload", ".jpg", getCacheDir());
        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        }
        return tempFile;
    }

    private void updateUserProfile() {
        String username = etUsername.getText().toString().trim();
        String fullname = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        User user = new User();
        user.setUsername(username);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setEmail(email);

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        RequestBody userBody = RequestBody.create(
                MediaType.parse("application/json"), userJson
        );

        MultipartBody.Part imagePart = null;
        if (selectedImageUri != null) {
            try {
                File imageFile = createTempFileFromUri(selectedImageUri);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), fileBody);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Image processing error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Call<Void> call = apiService.updateUser(userId, userBody, imagePart);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("fullname", fullname);
                    editor.putString("phone", phone);
                    editor.putString("email", email);

                    if (selectedImageUri != null) {
                        uploadedImagePath = selectedImageUri.toString();
                        editor.putString("image", uploadedImagePath);
                    }

                    editor.apply();
                    Toast.makeText(ProfileActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
