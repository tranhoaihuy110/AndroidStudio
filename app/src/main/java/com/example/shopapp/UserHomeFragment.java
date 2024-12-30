package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class UserHomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private ViewPager viewPager;
    private SliderAdapter sliderAdapter;
    private DatabaseHelper dbHelper;
    private EditText etSearch;
    private ImageView ivSearch;
    private Handler sliderHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        viewPager = view.findViewById(R.id.viewPager);
        etSearch = view.findViewById(R.id.etSearch);
        ivSearch = view.findViewById(R.id.ivSearch);

        // Thiết lập ViewPager cho slider
        int[] sliderImages = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,R.drawable.banner4,R.drawable.banner5};
        sliderAdapter = new SliderAdapter(getContext(), sliderImages);
        viewPager.setAdapter(sliderAdapter);

        sliderHandler = new Handler(Looper.getMainLooper());
        Runnable sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % sliderImages.length;
                viewPager.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 3000);
            }
        };

        sliderHandler.postDelayed(sliderRunnable, 3000);

        // Thiết lập RecyclerView theo chiều ngang
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPixels));


        dbHelper = new DatabaseHelper(getContext());
        productList = dbHelper.getAllProducts();


        productAdapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(productAdapter);


        ivSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                productAdapter.filter(query);
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nhấn vào ivCart
//        ImageView ivCart = view.findViewById(R.id.ivCart);
//        ivCart.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CartActivity.class);
//            startActivity(intent);
//        });

        return view;
    }
}
