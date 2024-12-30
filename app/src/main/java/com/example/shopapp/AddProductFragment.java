//package com.example.shopapp;
//
//import android.content.ContentValues;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AddProductFragment extends Fragment {
//
//    private Button buttonAdd;
//    private List<String> categoryIds = new ArrayList<>();
//    private List<String> supplierIds = new ArrayList<>();
//    private Spinner spinnerCategory, spinnerSupplier;
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private EditText etProductName, etProductPrice, etProductDescription, etProductStock, etPublishDate;
//    private ImageView ivProductImage;
//    private Uri imageUri;
//    private DatabaseHelper dbHelper;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
//        spinnerCategory = view.findViewById(R.id.spinnerCategory);
//        spinnerSupplier = view.findViewById(R.id.spinnerSupplier);
//        buttonAdd = view.findViewById(R.id.btnAddProduct);
//        etProductName = view.findViewById(R.id.etProductName);
//        etProductPrice = view.findViewById(R.id.etProductPrice);
//        etProductDescription = view.findViewById(R.id.etProductDescription);
//        etProductStock = view.findViewById(R.id.etProductStock);
//        etPublishDate = view.findViewById(R.id.etPublishDate);
//        ivProductImage = view.findViewById(R.id.ivProductImage);
//        Button btnSelectImage = view.findViewById(R.id.btnSelectImage);
//        Button btnAddProduct = view.findViewById(R.id.btnAddProduct);
//
//        dbHelper = new DatabaseHelper(getContext());
//
//        // Image selection
//        btnSelectImage.setOnClickListener(v -> openImageChooser());
//
//        loadCategoryData();
//        loadSupplierData();
//
//        // Add product
//        btnAddProduct.setOnClickListener(v -> addProduct());
//
//        return view;
//    }
//
//    private void openImageChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }
//    private void loadCategoryData() {
//        List<String> categories = dbHelper.getAllCategoryNames();
//        categoryIds = dbHelper.getAllCategoryIds();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategory.setAdapter(adapter);
//    }
//    private void loadSupplierData() {
//        List<String> suppliers = dbHelper.getAllSupplierNames();
//        supplierIds = dbHelper.getAllSupplierIds();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, suppliers);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerSupplier.setAdapter(adapter);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
//            imageUri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
//                ivProductImage.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void addProduct() {
//        String name = etProductName.getText().toString().trim();
//        String priceStr = etProductPrice.getText().toString().trim();
//        String description = etProductDescription.getText().toString().trim();
//        String stockStr = etProductStock.getText().toString().trim();
//        String publishDate = etPublishDate.getText().toString().trim();
//
//        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(description)
//                || TextUtils.isEmpty(stockStr) || TextUtils.isEmpty(publishDate) || imageUri == null) {
//            Toast.makeText(getContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        double price = Double.parseDouble(priceStr);
//        int stock = Integer.parseInt(stockStr);
//
//
//        // Get selected category and supplier IDs
//        String selectedCategoryId = categoryIds.get(spinnerCategory.getSelectedItemPosition());
//        String selectedSupplierId = supplierIds.get(spinnerSupplier.getSelectedItemPosition());
//
//        // Convert the image URI to a string
//        String imageResId = imageUri.toString();
//
//        // Create a new product object and save it in the database
//        Product product = new Product(imageResId, name, price, description, stock, publishDate, selectedCategoryId, selectedSupplierId);
//        dbHelper.insertProduct(product);
//
//        if (imageUri != null) {
//            ivProductImage.setImageURI(imageUri);
//        } else {
//            ivProductImage.setImageResource(R.drawable.themebg);
//        }
//
//        Toast.makeText(getContext(), "Product added successfully!", Toast.LENGTH_SHORT).show();
//
//        // Clear the fields after adding the product
//        etProductName.setText("");
//        etProductPrice.setText("");
//        etProductDescription.setText("");
//        etProductStock.setText("");
//        etPublishDate.setText("");
//        ivProductImage.setImageResource(0);
//        spinnerCategory.setSelection(0);
//        spinnerSupplier.setSelection(0);
//    }
//
//}
