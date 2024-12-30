package com.example.shopapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartItems;
    private OnCartItemClickListener listener;

    // Constructor nhận dữ liệu từ Activity
    public CartAdapter(List<Product> cartItems, OnCartItemClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout cart_item_layout.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText("Giá: " + product.getPrice());
        int displayStock = product.getAvailableStock() > 0 ? product.getAvailableStock() : product.getAvailableStock();
        if (displayStock == 0) {
            displayStock = 1;
        }
        holder.tvQuantity.setText(String.valueOf(displayStock));
        holder.imgProduct.setImageResource(product.getImageResId());


        Context context = holder.itemView.getContext();


        holder.btnIncrease.setOnClickListener(v -> {

            int currentStockInCart = product.getStock();
            int availableStock = product.getAvailableStock();
            if (availableStock < currentStockInCart) {
                product.setAvailableStock(availableStock + 1);
                holder.tvQuantity.setText(String.valueOf(product.getAvailableStock()));
                listener.onQuantityChanged();
            } else {

                Toast.makeText(context, "Không thể thêm sản phẩm, số lượng tồn kho đã hết!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (product.getStock() > 1) {

                int currentStock = product.getStock();
                product.setStock(currentStock - 1);
                holder.tvQuantity.setText(String.valueOf(product.getStock()));
                listener.onQuantityChanged();
            }
        });


        holder.btnRemove.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            listener.onItemRemoved();
        });
    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView tvProductName, tvProductPrice, tvQuantity;
        Button btnIncrease, btnDecrease, btnRemove;

        public CartViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }

    // Interface để xử lý sự kiện khi thay đổi số lượng hoặc xóa sản phẩm
    public interface OnCartItemClickListener {
        void onQuantityChanged();
        void onItemRemoved();
    }
}
