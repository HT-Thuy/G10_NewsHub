package com.example.g10_newshub.repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.g10_newshub.models.LoginRequest;
import com.example.g10_newshub.models.SignUpRequest;
import com.example.g10_newshub.models.User;
import com.example.g10_newshub.network.ApiClient;
import com.example.g10_newshub.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private ApiService apiService;

    public AuthRepository(Application application) {
        apiService = ApiClient.getClient().create(ApiService.class);
    }
    public LiveData<User> loginUser(String username, String password) {
        MutableLiveData<User> data = new MutableLiveData<>();
        apiService.loginUser(new LoginRequest(username, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<User> registerUser(String username, String email, String password, String fullname) {
        MutableLiveData<User> data = new MutableLiveData<>();
        apiService.registerUser(new SignUpRequest(username, email, password, fullname)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<User> getUserProfile(int userId) {
        MutableLiveData<User> data = new MutableLiveData<>();
        apiService.getUserProfile(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
