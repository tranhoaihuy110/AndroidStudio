package com.example.shopapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemClickListener {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartItems;
    private TextView tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerViewCart);
        tvTotalPrice = findViewById(R.id.tvTotalAmount);


        cartItems = getIntent().getParcelableArrayListExtra("cartItems");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartItems, this);
        recyclerView.setAdapter(cartAdapter);

        updateTotalPrice();

        Button btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            if (cartItems == null || cartItems.isEmpty()) {
                Toast.makeText(CartActivity.this, "Giỏ hàng của bạn đang trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            double totalPrice = 0;
            int totalQuantity = 0;
            for (Product product : cartItems) {
                totalPrice += product.getPrice() * product.getStock();
                totalQuantity += product.getStock();
            }
            new AlertDialog.Builder(CartActivity.this)
                    .setTitle("Xác nhận thanh toán")
                    .setMessage("Số lượng sản phẩm: " + totalQuantity + "\nTổng cộng: " + totalPrice + "$")
                    .setPositiveButton("Xác nhận", (dialog, which) -> {
                        // Thanh toán và làm trống giỏ hàng
                        Toast.makeText(CartActivity.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                        cartItems.clear();
                        cartAdapter.notifyDataSetChanged();
                        updateTotalPrice();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    public void onQuantityChanged() {
        // Cập nhật tổng giá sau khi thay đổi số lượng
        updateTotalPrice();
    }

    @Override
    public void onItemRemoved() {
        // Cập nhật tổng giá sau khi xóa sản phẩm
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        for (Product product : cartItems) {
            totalPrice += product.getPrice() * product.getStock();
        }
        tvTotalPrice.setText("Tổng cộng: " + (totalPrice == 0 ? "0" : totalPrice) + "$");
    }
}
