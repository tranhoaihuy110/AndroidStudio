package com.example.shopapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int imageResId;  // ID của ảnh trong drawable
    private String name;
    private double price;
    private String description;
    private int stock;
    private String publishDate;
    private String categoryId;
    private String supplierId;
    private int availableStock;

    public Product(int imageResId, String name, double price, String description, int stock,String publishDate) {
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.publishDate = publishDate;

    }
    public Product(int imageResId, String name, double price, String description, int stock,String publishDate,int availableStock) {
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.availableStock =1;
        this.publishDate = publishDate;

    }
    public Product(String imageResId, String name, double price, String description, int stock, String publishDate, String categoryId, String supplierId) {
        this.imageResId = Integer.parseInt(imageResId);
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.publishDate = publishDate;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    // Getters
    public int getImageResId() { return imageResId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getStock() { return stock; }

    public String getPublishDate() {
        return publishDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    protected Product(Parcel in) {
        imageResId = in.readInt();
        name = in.readString();
        price = in.readDouble();
        description = in.readString();
        stock = in.readInt();
        publishDate = in.readString();
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResId);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeInt(stock);
        dest.writeString(publishDate);
    }


    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}

