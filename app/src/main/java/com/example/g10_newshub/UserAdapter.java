package com.example.g10_newshub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private OnUserClickListener listener;

    public UserAdapter(List<User> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.listener = listener;

        // 📌 Sắp xếp danh sách theo ID tự động
        Collections.sort(userList, Comparator.comparingInt(User::getAutoIncrementId));
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);

        // 📌 Hiển thị ID tự động trước Tên
        holder.tvUserId.setText(String.valueOf(user.getAutoIncrementId()));
        holder.tvUserName.setText(user.getFullName());
        holder.tvUserEmail.setText(user.getEmail());

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteUser(user);
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditUser(user);
        });

        holder.btnView.setOnClickListener(v -> {
            if (listener != null) listener.onViewUser(user);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserId, tvUserName, tvUserEmail;
        ImageButton btnDelete, btnEdit, btnView;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnView = itemView.findViewById(R.id.btnView);
        }
    }
}