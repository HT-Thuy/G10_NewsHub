package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Api.RetrofitService;
import com.example.myapplication.Model.Category;
import com.example.myapplication.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<Category> categoryList;
    private LayoutInflater inflater;
    private ApiService apiService;

    public CategoryAdapter(Context context, List<Category> categoryList, String username, String password) {
        this.context = context;
        this.categoryList = categoryList;
        this.inflater = LayoutInflater.from(context);
        this.apiService = new RetrofitService(username, password).getRetrofit().create(ApiService.class);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_category, parent, false);
            holder = new ViewHolder();
            holder.categoryImage = convertView.findViewById(R.id.categoryImage);
            holder.categoryName = convertView.findViewById(R.id.categoryName);
            holder.articleCount = convertView.findViewById(R.id.articleCount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Category category = categoryList.get(position);
        holder.categoryName.setText(category.getCategoryName());
        Glide.with(context).load("http://192.168.2.13:8080/" + category.getImage()).placeholder(R.drawable.placeholder).into(holder.categoryImage);

        apiService.getArticleCount(category.getId()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    holder.articleCount.setText(response.body() + " Articles");
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                holder.articleCount.setText("0 Articles");
            }
        });

        return convertView;
    }
    private static class ViewHolder {
        ImageView categoryImage;
        TextView categoryName, articleCount;
    }
}
