package com.example.g10_newshub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.g10_newshub.R;
import com.example.g10_newshub.data.local.ArticleEntity;
import java.util.ArrayList;
import java.util.List;

public class SavedArticleAdapter extends RecyclerView.Adapter<SavedArticleAdapter.SavedArticleViewHolder> {

    private List<ArticleEntity> articles = new ArrayList<>();
    private Context context;
    private OnItemClickListener listener;

    public SavedArticleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SavedArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new SavedArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedArticleViewHolder holder, int position) {
        ArticleEntity currentArticle = articles.get(position);
        holder.title.setText(currentArticle.title);

        Glide.with(context)
                .load(currentArticle.image)
                .placeholder(R.color.design_default_color_secondary)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<ArticleEntity> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    class SavedArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;

        public SavedArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_article);
            title = itemView.findViewById(R.id.text_view_article_title);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(articles.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ArticleEntity article);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
