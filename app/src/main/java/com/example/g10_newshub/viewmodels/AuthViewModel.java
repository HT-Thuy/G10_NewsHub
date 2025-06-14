package com.example.g10_newshub.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.g10_newshub.models.User;
import com.example.g10_newshub.repositories.AuthRepository;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private LiveData<User> userLiveData;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public void login(String username, String password) {
        userLiveData = authRepository.loginUser(username, password);
    }

    public void register(String username, String email, String password, String fullname) {
        userLiveData = authRepository.registerUser(username, email, password, fullname);
    }

    public LiveData<User> getAuthenticationResult() {
        return userLiveData;
    }
}
