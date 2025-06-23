package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;
import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.Category;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {
    private GridView categoriesGridView;
    private ApiService apiService;
    private List<Category> categoryList;

    public CategoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        categoriesGridView = view.findViewById(R.id.categoriesGridView);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared_user", getContext().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);

        if (username != null && password != null) {
            apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);

            loadCategories(username, password);
        }

        return view;
    }

    private void loadCategories(String username, String password) {
        apiService.getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();
                    CategoryAdapter adapter = new CategoryAdapter(getContext(), categoryList, username, password);
                    categoriesGridView.setAdapter(adapter);

                    categoriesGridView.setOnItemClickListener((parent, view1, position, id) -> {
                        Category selectedCategory = categoryList.get(position);
                        Intent intent = new Intent(getActivity(), NewsCategoryActivity.class);
                        intent.putExtra("category_id", selectedCategory.getId());
                        intent.putExtra("category_name", selectedCategory.getCategoryName());
                        startActivity(intent);
                    });
                } else {
                    Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}