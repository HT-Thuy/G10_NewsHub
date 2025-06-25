package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Article;
import com.example.myapplication.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<Article> articleList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SliderAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.txtText.setText(article.getTitle());
        holder.txtCate.setText(article.getCategoryName());

        // --- SỬA LẠI PHẦN TẢI ẢNH Ở ĐÂY ---
        // 1. Dùng địa chỉ IP đúng cho máy ảo
        // 2. Thêm giao thức "http://" vào đầu
        String baseUrl = "http://10.0.2.2:8080/";
        String fullImageUrl = baseUrl + article.getImage();

        Glide.with(context)
                .load(fullImageUrl) // Sử dụng URL đã được sửa lại
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtCate, txtText;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            txtCate = itemView.findViewById(R.id.txtCate);
            txtText = itemView.findViewById(R.id.txtText);
        }
    }
}
