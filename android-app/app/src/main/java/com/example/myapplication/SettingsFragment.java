package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {
    private LinearLayout changeProfileLayout, helpCenterLayout, logoutLayout;
    private SharedPreferences sharedPreferences;
    private RetrofitService retrofitService;
    private ApiService apiService;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        changeProfileLayout = view.findViewById(R.id.changeProfileLayout);
        helpCenterLayout = view.findViewById(R.id.helpCenterLayout);
        logoutLayout = view.findViewById(R.id.logoutLayout);

        changeProfileLayout.setOnClickListener(v -> goToChangeProfile());
        helpCenterLayout.setOnClickListener(v -> goToHelpCenter());
        logoutLayout.setOnClickListener(v -> performLogout());

        return view;
    }

    private void goToChangeProfile() {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        startActivity(intent);
    }

    private void goToHelpCenter() {
        Intent intent = new Intent(getActivity(), HelpActivity.class);
        startActivity(intent);
    }
    private void performLogout() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared_user", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);

        RetrofitService retrofitService = new RetrofitService(username, password);
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        Call<Void> call = apiService.logout();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    Toast.makeText(requireContext(), "Logout success", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(requireContext(), "Logout fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}