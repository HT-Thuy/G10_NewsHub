package com.example.g10_newshub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.g10_newshub.R;
import com.example.g10_newshub.activities.LoginActivity;
import com.example.g10_newshub.utils.SessionManager;
import com.example.g10_newshub.viewmodels.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private TextView fullNameTextView, usernameTextView;
    private Button logoutButton;
    private SessionManager sessionManager;
    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(requireContext());
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        fullNameTextView = view.findViewById(R.id.profile_fullname);
        usernameTextView = view.findViewById(R.id.profile_username);
        logoutButton = view.findViewById(R.id.btn_logout);

        // Lấy thông tin từ session để hiển thị tạm thời
        usernameTextView.setText("@" + sessionManager.getUsername());
        fullNameTextView.setText(sessionManager.getFullname());

        // Gọi API để lấy thông tin mới nhất (nếu cần)
        int userId = sessionManager.getUserId();
        if (userId != -1) {
            profileViewModel.getUserProfile(userId).observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    fullNameTextView.setText(user.getFullname());
                    usernameTextView.setText("@" + user.getUsername());
                    // Cập nhật session nếu có thông tin mới
                    sessionManager.createLoginSession(user.getId(), user.getUsername(), user.getFullname());
                }
            });
        }

        logoutButton.setOnClickListener(v -> {
            sessionManager.logoutUser();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return view;
    }
}
