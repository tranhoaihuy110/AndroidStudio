package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Khởi tạo CartManager
        cartManager = new CartManager();

        // Lấy thông tin từ Intent
        int imageResId = getIntent().getIntExtra("imageResId", 0);
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0);
        String description = getIntent().getStringExtra("description");
        int stock = getIntent().getIntExtra("stock", 0);
        String publishDate = getIntent().getStringExtra("publishDate");

        // Ánh xạ các View
        ImageView productImage = findViewById(R.id.productImageDetail);
        TextView productName = findViewById(R.id.productNameDetail);
        TextView productPrice = findViewById(R.id.productPriceDetail);
        TextView productDescription = findViewById(R.id.productDescriptionDetail);
        TextView productStock = findViewById(R.id.productStockDetail);
        TextView productPublishDate = findViewById(R.id.PublishDateDetail);

        // Thiết lập dữ liệu cho các View
        productImage.setImageResource(imageResId);
        productName.setText(name);
        productPrice.setText(String.format("$%.2f", price));
        productDescription.setText(description);
        productStock.setText("Số lượng tồn: " + stock);
        productPublishDate.setText("Ngày xuất bản: " + publishDate);

        findViewById(R.id.addToCartButton).setOnClickListener(v -> {
            Product product = new Product(imageResId, name, price, description, stock, publishDate,1);

            if (product.getStock() >= product.getAvailableStock()) {
                cartManager.addToCart(product, ProductDetailActivity.this);
            } else {
                Toast.makeText(ProductDetailActivity.this, "Sản phẩm không đủ số lượng tồn kho!", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            intent.putParcelableArrayListExtra("cartItems", new ArrayList<>(cartManager.getCartItems()));
            startActivity(intent);
        });

        findViewById(R.id.backIcon).setOnClickListener(
                v -> {
                    Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
        );

    }
}

