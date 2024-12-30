package com.example.shopapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManagementFragment extends Fragment {


    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper dbHelper;
    private EditText etSearch;
    private ImageView ivSearch,ivAdd;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_management, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        etSearch = view.findViewById(R.id.etSearch);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivAdd = view.findViewById(R.id.ivAdd);

        // Thiết lập RecyclerView theo chiều dọc với 1 cột
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPixels));

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        dbHelper = new DatabaseHelper(getContext());
        productList = dbHelper.getAllProducts();

        // Khởi tạo adapter và gán vào RecyclerView
        productAdapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(productAdapter);

        // Xử lý sự kiện tìm kiếm khi nhấn vào ivSearch
        ivSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                productAdapter.filter(query);
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });
//        ivAdd.setOnClickListener(v -> {
//            getParentFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, new AddProductFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
        return view;
    }
}
