package com.example.g10_newshub.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.g10_newshub.models.User;
import com.example.g10_newshub.repositories.AuthRepository;

public class ProfileViewModel extends AndroidViewModel {
    private AuthRepository authRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public LiveData<User> getUserProfile(int userId) {
        return authRepository.getUserProfile(userId);
    }
}
