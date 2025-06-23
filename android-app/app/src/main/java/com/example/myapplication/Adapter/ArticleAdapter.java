package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Article;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ArticleAdapter extends BaseAdapter {
    private Context context;
    private List<Article> articleList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public ArticleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void updateData(List<Article> newList) {
        this.articleList = newList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
            holder = new ViewHolder();
            holder.newsImage = convertView.findViewById(R.id.newsImage);
            holder.newsTitle = convertView.findViewById(R.id.newsTitle);
            holder.newsCategory = convertView.findViewById(R.id.newsCategory);
            holder.newsDate = convertView.findViewById(R.id.newsDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Article article = articleList.get(position);
        holder.newsTitle.setText(article.getTitle());
        if (article.getCategoryName() != null) {
            holder.newsCategory.setText(article.getCategoryName());
        } else {
            holder.newsCategory.setText("Unknown Category");
        }
        holder.newsDate.setText(dateFormat.format(article.getCreateAt()));

        Glide.with(context)
                .load("http://192.168.2.13:8080/" + article.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.newsImage);

        return convertView;
    }

    static class ViewHolder {
        ImageView newsImage;
        TextView newsTitle, newsCategory, newsDate;
    }
}
