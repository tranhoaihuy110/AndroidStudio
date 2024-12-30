package com.example.shopapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister,tvForget;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new DatabaseHelper(this);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForget = findViewById(R.id.tvForget);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (authenticateUser(email, password)) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });


        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
        tvForget.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Quên mật khẩu");

            final EditText inputUsername = new EditText(MainActivity.this);
            inputUsername.setHint("Nhập tên đăng nhập");
            builder.setView(inputUsername);

            builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                String username = inputUsername.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                } else if (checkUsernameExists(username)) {
                    promptNewPassword(username); // Cho phép đổi mật khẩu
                } else {
                    Toast.makeText(MainActivity.this, "Tên đăng nhập không tồn tại.", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
            builder.show();
        });

    }
    private void updatePassword(String username, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_MAT_KHAU, newPassword);

        db.update(DatabaseHelper.TABLE_TAIKHOAN, values, DatabaseHelper.COLUMN_TEN_DANG_NHAP + "=?", new String[]{username});
        db.close();
    }

    private boolean checkUsernameExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TAIKHOAN,
                new String[]{DatabaseHelper.COLUMN_TEN_DANG_NHAP},
                DatabaseHelper.COLUMN_TEN_DANG_NHAP + "=?",
                new String[]{username},
                null, null, null
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    private void promptNewPassword(String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Nhập mật khẩu mới");

        final EditText inputNewPassword = new EditText(MainActivity.this);
        inputNewPassword.setHint("Mật khẩu mới (ít nhất 8 ký tự)");
        builder.setView(inputNewPassword);

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String newPassword = inputNewPassword.getText().toString();

            if (newPassword.length() >= 8) {
                updatePassword(username, newPassword);
                Toast.makeText(MainActivity.this, "Đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Mật khẩu phải có ít nhất 8 ký tự.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private boolean authenticateUser(String tenDangNhap, String matKhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TAIKHOAN,
                new String[]{DatabaseHelper.COLUMN_TEN_DANG_NHAP},
                DatabaseHelper.COLUMN_TEN_DANG_NHAP + "=? AND " + DatabaseHelper.COLUMN_MAT_KHAU + "=?",
                new String[]{tenDangNhap, matKhau},
                null, null, null
        );
        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isAuthenticated;
    }
}
