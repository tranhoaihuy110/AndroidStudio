package com.example.shopapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etUsername, etPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (!isValidName(name)) {
                    Toast.makeText(RegisterActivity.this, "Tên không được chứa số hoặc ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                } else if (!isValidUsername(username)) {
                    Toast.makeText(RegisterActivity.this, "Tên tài khoản không được chứa ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                } else {
                    // Lưu thông tin đăng ký vào cơ sở dữ liệu
                    long result = registerUser(name, email, username, password);
                    if (result != -1) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại. Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isValidName(String name) {
        // Kiểm tra tên chỉ chứa chữ cái và khoảng trắng
        return name.matches("^[\\p{L}\\s]+$");
    }

    private boolean isValidEmail(String email) {
        // Kiểm tra định dạng email
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidUsername(String username) {

        return username.matches("^[a-zA-Z0-9]+$");
    }

    private boolean isValidPassword(String password) {

        return password.length() >= 8;
    }

    private long registerUser(String name, String email, String username, String password) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_HO_TEN, name);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_TEN_DANG_NHAP, username);
        values.put(DatabaseHelper.COLUMN_MAT_KHAU, password);
        values.put(DatabaseHelper.COLUMN_QUYEN, "Khách hàng");


        long result = db.insert(DatabaseHelper.TABLE_TAIKHOAN, null, values);
        db.close();
        return result;
    }
}
