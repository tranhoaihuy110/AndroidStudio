package com.example.shopapp;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private List<Product> cartItems;

    public CartManager() {
        cartItems = new ArrayList<>();
    }


    public void addToCart(Product product, Context context) {

        for (Product p : cartItems) {
            if (p.getName().equals(product.getName())) {

                int newStock = p.getAvailableStock() + product.getAvailableStock();
                if (newStock <= p.getStock()) {
                    p.setAvailableStock(newStock);
                    Toast.makeText(context, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    Toast.makeText(context, "Không thể thêm sản phẩm, số lượng tồn kho đã hết!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }


        cartItems.add(product);
        Toast.makeText(context, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }

    // Lấy tất cả sản phẩm trong giỏ hàng
    public List<Product> getCartItems() {
        return new ArrayList<>(cartItems);
    }

}
