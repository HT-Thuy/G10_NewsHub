package com.example.g10_newshub;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList;
    private DatabaseReference dbRef;
    private ImageButton btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        adapter = new UserAdapter(userList, new OnUserClickListener() {
            @Override
            public void onDeleteUser(User user) {
                deleteUser(user);
            }

            @Override
            public void onEditUser(User user) {
                Toast.makeText(AdminActivity.this, "Chỉnh sửa: " + user.getFullName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onViewUser(User user) {
                Toast.makeText(AdminActivity.this, "Xem: " + user.getFullName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        dbRef = FirebaseDatabase.getInstance().getReference("users");

        // Lấy danh sách người dùng từ Firebase
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminActivity.this, "Lỗi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        });

        // Bắt sự kiện khi nhấn vào nút "Thêm Người Dùng"
        //btnAddUser = findViewById(R.id.btnAddUser);
        //btnAddUser.setOnClickListener(v -> {
        //    Intent intent = new Intent(AdminActivity.this, AddUserActivity.class);
        //    startActivity(intent);
        //});
    }

    private void deleteUser(User user) {
        dbRef.child(user.getUid()).removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi xóa!", Toast.LENGTH_SHORT).show());
    }
}