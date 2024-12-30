package com.example.shopapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ShopApp.db";
    public static final int DATABASE_VERSION = 8;
    public Context context;

    // Bảng TAIKHOAN
    public static final String TABLE_TAIKHOAN = "TAIKHOAN";
    public static final String COLUMN_TAIKHOAN_ID = "MaTaiKhoan";
    public static final String COLUMN_TEN_DANG_NHAP = "TenDangNhap";
    public static final String COLUMN_MAT_KHAU = "MatKhau";
    public static final String COLUMN_HO_TEN = "HoTen";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_QUYEN = "Quyen";

    // Bảng THELOAI
    public static final String TABLE_THELOAI = "THELOAI";
    public static final String COLUMN_MA_THE_LOAI = "MaTheLoai";
    public static final String COLUMN_TEN_THE_LOAI = "TenTheLoai";

    // Bảng NHACUNGCAP
    public static final String TABLE_NHACUNGCAP = "NHACUNGCAP";
    public static final String COLUMN_NHA_CUNG_CAP_ID = "MaNhaCungCap";
    public static final String COLUMN_TEN_NHA_CUNG_CAP = "TenNhaCungCap";
    public static final String COLUMN_DIA_CHI = "DiaChi";
    public static final String COLUMN_SO_DIEN_THOAI = "SoDienThoai";
    public static final String COLUMN_EMAIL_NHA_CUNG_CAP = "Email";
    public static final String COLUMN_WEBSITE = "Website";

    // Bảng SACH
    public static final String TABLE_SACH = "SACH";
    public static final String COLUMN_SACH_ID = "MaSach";
    public static final String COLUMN_TEN_SACH = "TenSach";
    public static final String COLUMN_GIA = "Gia";
    public static final String COLUMN_SO_LUONG_TON = "SoLuongTon";
    public static final String COLUMN_MO_TA = "MoTa";
    public static final String COLUMN_NGAY_XUAT_BAN = "NgayXuatBan";
    public static final String COLUMN_ANH = "Anh";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng TAIKHOAN

        String createTableTaiKhoan = "CREATE TABLE " + TABLE_TAIKHOAN + " (" +
                COLUMN_TAIKHOAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEN_DANG_NHAP + " TEXT NOT NULL UNIQUE, " +
                COLUMN_MAT_KHAU + " TEXT NOT NULL, " +
                COLUMN_HO_TEN + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_QUYEN + " TEXT DEFAULT 'Khách hàng')";
        db.execSQL(createTableTaiKhoan);

        // Tạo bảng THELOAI
        String createTableTheLoai = "CREATE TABLE " + TABLE_THELOAI + " (" +
                COLUMN_MA_THE_LOAI + " TEXT PRIMARY KEY, " +
                COLUMN_TEN_THE_LOAI + " TEXT NOT NULL UNIQUE)";
        db.execSQL(createTableTheLoai);

        // Tạo bảng NHACUNGCAP
        String createTableNhaCungCap = "CREATE TABLE " + TABLE_NHACUNGCAP + " (" +
                COLUMN_NHA_CUNG_CAP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEN_NHA_CUNG_CAP + " TEXT NOT NULL, " +
                COLUMN_DIA_CHI + " TEXT, " +
                COLUMN_SO_DIEN_THOAI + " TEXT, " +
                COLUMN_EMAIL_NHA_CUNG_CAP + " TEXT, " +
                COLUMN_WEBSITE + " TEXT)";
        db.execSQL(createTableNhaCungCap);

        // Tạo bảng SACH
        String createTableSach = "CREATE TABLE " + TABLE_SACH + " (" +
                COLUMN_SACH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEN_SACH + " TEXT NOT NULL, " +
                COLUMN_MA_THE_LOAI + " TEXT, " +
                COLUMN_NHA_CUNG_CAP_ID + " INTEGER, " +
                COLUMN_GIA + " REAL NOT NULL, " +
                COLUMN_SO_LUONG_TON + " INTEGER DEFAULT 0, " +
                COLUMN_MO_TA + " TEXT, " +
                COLUMN_NGAY_XUAT_BAN + " TEXT, " +
                COLUMN_ANH + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_MA_THE_LOAI + ") REFERENCES " + TABLE_THELOAI + "(" + COLUMN_MA_THE_LOAI + "), " +
                "FOREIGN KEY (" + COLUMN_NHA_CUNG_CAP_ID + ") REFERENCES " + TABLE_NHACUNGCAP + "(" + COLUMN_NHA_CUNG_CAP_ID + "))";
        db.execSQL(createTableSach);
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SACH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHACUNGCAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THELOAI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAIKHOAN);
        onCreate(db);

    }

    public void insertSampleData(SQLiteDatabase db) {
        // Thêm dữ liệu mẫu cho bảng TAIKHOAN
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_DANG_NHAP, "user");
        values.put(COLUMN_MAT_KHAU, "user");
        values.put(COLUMN_HO_TEN, "Nguyen Van An");
        values.put(COLUMN_EMAIL, "user@email.com");
        values.put(COLUMN_QUYEN, "Khách hàng");
        db.insert(TABLE_TAIKHOAN, null, values);

        values.clear();
        values.put(COLUMN_TEN_DANG_NHAP, "admin");
        values.put(COLUMN_MAT_KHAU, "admin");
        values.put(COLUMN_HO_TEN, "Tran Thai Bao");
        values.put(COLUMN_EMAIL, "admin@email.com");
        values.put(COLUMN_QUYEN, "Quản lý");
        db.insert(TABLE_TAIKHOAN, null, values);

        // Thêm dữ liệu mẫu cho bảng THELOAI
        insertTheLoai(db, "TT", "Tiểu thuyết");
        insertTheLoai(db, "KH", "Khoa học");
        insertTheLoai(db, "PL", "Phiêu lưu");
        insertTheLoai(db, "HK", "Hài kịch");
        insertTheLoai(db, "LS", "Lịch sử");

        // Thêm dữ liệu mẫu cho bảng NHACUNGCAP
        insertNhaCungCap(db, "NXB Kim Đồng", "123 Đường Kim Đồng, Hà Nội", "0901234567", "kimdong@example.com", "www.kimdong.vn");
        insertNhaCungCap(db, "NXB Trẻ", "456 Đường Trần Hưng Đạo, TP.HCM", "0912345678", "nxbtre@example.com", "www.nxbtre.vn");
        insertNhaCungCap(db, "NXB Giáo Dục", "789 Đường Giải Phóng, Hà Nội", "0923456789", "giaoduc@example.com", "www.giaoduc.vn");
        insertNhaCungCap(db, "Công Ty Sách Alpha", "101 Đường Lý Thường Kiệt, Đà Nẵng", "0934567890", "alpha@example.com", "www.alpha.vn");
        insertNhaCungCap(db, "NXB Văn Hóa", "102 Đường Nguyễn Văn Cừ, TP.HCM", "0945678901", "vanhoa@example.com", "www.vanhoa.vn");

        // Thêm dữ liệu mẫu cho bảng SACH
        insertSach(db, "Tuổi thơ dữ dội", "LS", 1, 150, 10, "Mừng là nhân vật xuất hiện sớm nhất, nhà nghèo, mẹ bị mắc bệnh hen suyễn nặng và bị người cha dượng lợi dụng bóc lột.", "2021-01-01", "drawable/tuoithodudoi.jpg");
        insertSach(db, "Cậu bé có tài mở khóa", "PL", 2, 200, 5, "Chú Bé Có Tài Mở Khóa là một truyện phiêu lưu, kể về chú bé Hùng bị ném ra ngoài xã hội, phải đối mặt với cái xấu, cái ác", "2020-05-10", "drawable/chubecotaimokhoa.jpg");
        insertSach(db, "Đất rừng phương Nam", "TT", 3, 180, 7, "Cậu bé An sống cùng với cha mẹ tại thành phố, sau ngày độc lập 2-9-1945.", "2022-02-20", "drawable/datrungpn.jpg");
        insertSach(db, "Số đỏ", "PL", 2, 200, 5, "Tác phẩm xoay quanh nhân vật chính là Xuân biệt danh là Xuân Tóc đỏ từ một kẻ hạ lưu, bỗng chốc bước lên tầng lớp danh giá", "2020-05-10", "drawable/sodo.jpg");
        insertSach(db, "Tiêu sơn tráng sĩ", "TT", 3, 180, 7, "Tác phẩm lấy bối cảnh thời Lê mạt Nguyễn sơ, là một cuốn tiểu thuyết dã sử xoay quanh hoạt động của đảng Tiêu Sơn phò Lê", "2022-02-20", "drawable/tieuson.png");
    }

    // Phương thức tiện ích để thêm vào bảng THELOAI
    private void insertTheLoai(SQLiteDatabase db, String maTheLoai, String tenTheLoai) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_THE_LOAI, maTheLoai);
        values.put(COLUMN_TEN_THE_LOAI, tenTheLoai);
        db.insert(TABLE_THELOAI, null, values);
    }

    // Phương thức tiện ích để thêm vào bảng NHACUNGCAP
    private void insertNhaCungCap(SQLiteDatabase db, String tenNhaCungCap, String diaChi, String soDienThoai, String email, String website) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_NHA_CUNG_CAP, tenNhaCungCap);
        values.put(COLUMN_DIA_CHI, diaChi);
        values.put(COLUMN_SO_DIEN_THOAI, soDienThoai);
        values.put(COLUMN_EMAIL_NHA_CUNG_CAP, email);
        values.put(COLUMN_WEBSITE, website);
        db.insert(TABLE_NHACUNGCAP, null, values);
    }

    // Phương thức tiện ích để thêm vào bảng SACH
    private void insertSach(SQLiteDatabase db, String tenSach, String maTheLoai, int maNhaCungCap, double gia, int soLuongTon, String moTa, String ngayXuatBan, String anh) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_SACH, tenSach);
        values.put(COLUMN_MA_THE_LOAI, maTheLoai);
        values.put(COLUMN_NHA_CUNG_CAP_ID, maNhaCungCap);
        values.put(COLUMN_GIA, gia);
        values.put(COLUMN_SO_LUONG_TON, soLuongTon);
        values.put(COLUMN_MO_TA, moTa);
        values.put(COLUMN_NGAY_XUAT_BAN, ngayXuatBan);
        values.put(COLUMN_ANH, anh);
        db.insert(TABLE_SACH, null, values);
    }

    // Phương thức lấy tất cả các sản phẩm từ bảng SACH
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SACH, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String imageNameWithExtension = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANH));
                String imageName = imageNameWithExtension.replace(".jpg", "").replace(".png", "");
                int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                if (imageResId == 0) {
                    imageResId = R.drawable.themebg;
                }

                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_SACH));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GIA));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MO_TA));
                int stock = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_LUONG_TON));
                String publishDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_XUAT_BAN)); // Lấy ngày xuất bản

                Product product = new Product(imageResId, name, price, description, stock, publishDate);
                productList.add(product);
            } while (cursor.moveToNext());
        }

        if (cursor != null) cursor.close();
        return productList;
    }
    public List<String> getAllCategoryNames() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TEN_THE_LOAI + " FROM " + TABLE_THELOAI, null);


        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public List<String> getAllCategoryIds() {
        List<String> categoryIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_MA_THE_LOAI + " FROM " + TABLE_THELOAI, null);

        if (cursor.moveToFirst()) {
            do {
                categoryIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoryIds;
    }

    // Phương thức lấy danh sách tên nhà cung cấp từ bảng NHACUNGCAP
    public List<String> getAllSupplierNames() {
        List<String> suppliers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TEN_NHA_CUNG_CAP + " FROM " + TABLE_NHACUNGCAP, null);

        if (cursor.moveToFirst()) {
            do {
                suppliers.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return suppliers;
    }

    // Phương thức lấy danh sách mã nhà cung cấp từ bảng NHACUNGCAP
    public List<String> getAllSupplierIds() {
        List<String> supplierIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NHA_CUNG_CAP_ID + " FROM " + TABLE_NHACUNGCAP, null);

        if (cursor.moveToFirst()) {
            do {
                supplierIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return supplierIds;
    }
    public void insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_san_pham", product.getName());
        values.put("gia", product.getPrice());
        values.put("ma_the_loai", product.getCategoryId());
        values.put("ma_nha_cung_cap", product.getSupplierId());
        values.put("mo_ta", product.getDescription());
        values.put("so_luong_ton", product.getStock());
        values.put("ngay_xuat_ban", product.getPublishDate());
        values.put("anh", product.getImageResId());

        db.insert("SANPHAM", null, values);
        db.close();
    }

}
