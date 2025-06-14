package com.example.g10_newshub;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUserActivity extends AppCompatActivity {
    private EditText edtName, edtUsername, edtEmail, edtPassword, edtConfirmPassword, edtDateCreated;
    private Button btnSave;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // Khởi tạo Firebase
        dbRef = FirebaseDatabase.getInstance().getReference("users");

        // Ánh xạ các trường nhập liệu
        edtName = findViewById(R.id.edtName);
        //edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtDateCreated = findViewById(R.id.edtDateCreated);
        btnSave = findViewById(R.id.btnSave); // Chỉ có nút "Save", không có nút "Cancel"

        // Bắt sự kiện khi nhấn nút "Lưu"
        btnSave.setOnClickListener(v -> saveUser());
    }

    private void saveUser() {
        String name = edtName.getText().toString().trim();
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        String dateCreated = edtDateCreated.getText().toString().trim();
        String role = "Employee"; // Mặc định là nhân viên

        // Kiểm tra dữ liệu đầu vào
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(dateCreated)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 📌 Tạo UID tự động
        String uid = dbRef.push().getKey();

        // 📌 Lấy ID tự động tăng từ Firebase
        dbRef.orderByChild("autoIncrementId").limitToLast(1).get().addOnCompleteListener(task -> {
            int newId = 1; // 📌 Nếu chưa có dữ liệu, mặc định ID bắt đầu từ 1

            if (task.isSuccessful() && task.getResult().exists()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    User lastUser = snapshot.getValue(User.class);
                    if (lastUser != null) {
                        newId = lastUser.getAutoIncrementId() + 1; // 📌 Tăng lên 1 đơn vị
                    }
                }
            }

            // 📌 Tạo người dùng mới và lưu vào Firebase
            User newUser = new User(newId, uid, name, email, role);
            dbRef.child(uid).setValue(newUser)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // 📌 Quay lại AdminActivity
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi thêm!", Toast.LENGTH_SHORT).show());
        });
    }
}