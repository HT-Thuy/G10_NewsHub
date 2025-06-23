package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Comment;
import com.example.myapplication.R;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> commentList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
            holder = new ViewHolder();
            holder.imgAvatar = convertView.findViewById(R.id.imgAvatar);
            holder.tvUsername = convertView.findViewById(R.id.tvUsername);
            holder.tvContent = convertView.findViewById(R.id.tvContent);
            holder.tvCreateAt = convertView.findViewById(R.id.tvCreateAt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment comment = commentList.get(position);
        holder.tvUsername.setText(comment.getUser().getUsername());
        holder.tvContent.setText(comment.getContent());
        holder.tvCreateAt.setText(dateFormat.format(comment.getCreateAt()));

        Glide.with(context)
                .load("http://192.168.2.13:8080/" + comment.getUser().getImage())
                .placeholder(R.drawable.avatar)
                .circleCrop()
                .into(holder.imgAvatar);

        return convertView;
    }

    static class ViewHolder {
        ImageView imgAvatar;
        TextView tvUsername;
        TextView tvContent;
        TextView tvCreateAt;
    }
}
