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
import com.example.myapplication.Model.Bookmark;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookmarkAdapter extends BaseAdapter {
    private Context context;
    private List<Bookmark> bookmarkList;
    private LayoutInflater inflater;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public BookmarkAdapter(Context context, List<Bookmark> bookmarkList) {
        this.context = context;
        this.bookmarkList = bookmarkList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookmarkList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookmarkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bookmarkList.get(position).getId();
    }
    public void updateData(List<Bookmark> newList) {
        this.bookmarkList = newList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_news, parent, false);
            holder = new ViewHolder();
            holder.newsImage = convertView.findViewById(R.id.newsImage);
            holder.newsCategory = convertView.findViewById(R.id.newsCategory);
            holder.newsTitle = convertView.findViewById(R.id.newsTitle);
            holder.newsDate = convertView.findViewById(R.id.newsDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bookmark bookmark = bookmarkList.get(position);
        Article article = bookmark.getArticle();

        holder.newsTitle.setText(article.getTitle());
        holder.newsCategory.setText(article.getCategoryName());
        holder.newsDate.setText(dateFormat.format(article.getCreateAt()));

        Glide.with(context).load( "http://192.168.2.13:8080/" + article.getImage()).placeholder(R.drawable.placeholder).into(holder.newsImage);

        return convertView;
    }
    static class ViewHolder {
        ImageView newsImage;
        TextView newsCategory, newsTitle, newsDate;
    }
}
