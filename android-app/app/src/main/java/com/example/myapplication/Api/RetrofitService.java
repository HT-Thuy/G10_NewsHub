package com.example.myapplication.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    // Constructor cho các request không cần xác thực
    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        retrofit = new Retrofit.Builder()
                // Giữ lại baseUrl đúng cho máy ảo
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    // Constructor cho các request cần xác thực (ví dụ: login)
    public RetrofitService(String username, String password) {
        initializeRetrofit(username, password);
    }

    private void initializeRetrofit(String username, String password) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(username, password))
                .build();
        retrofit = new Retrofit.Builder()
                // SỬA LẠI Ở ĐÂY: Thống nhất baseUrl về địa chỉ đúng của máy ảo
                .baseUrl("http://10.0.2.2:8080/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
