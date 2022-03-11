package com.example.libmana.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "QLTV";
    static final int DB_VERSION =1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String taoLoaiSach = "CREATE TABLE LoaiSach (MaLoai INTEGER ,tenLoai TEXT NOT NULL,PRIMARY KEY(MaLoai AUTOINCREMENT) )";
        db.execSQL(taoLoaiSach);

        String taoSach = "CREATE TABLE Sach (MaSach INTEGER,tenSach TEXT NOT NULL,giaThue INTEGER NOT NULL,MaLoai INTEGER NOT NULL,FOREIGN KEY(MaLoai) REFERENCES LoaiSach(MaLoai),PRIMARY KEY(MaSach AUTOINCREMENT))";
        db.execSQL(taoSach);

        String taoThuThu = "CREATE TABLE ThuThu (MaTT TEXT NOT NULL,hoTen TEXT NOT NULL,matKhau TEXT NOT NULL,PRIMARY KEY(MaTT))";
        db.execSQL(taoThuThu);

        String taoThanhVien = "CREATE TABLE ThanhVien (MaTV INTEGER,hoTen TEXT NOT NULL,namSinh TEXT NOT NULL,PRIMARY KEY(MaTV AUTOINCREMENT))";
        db.execSQL(taoThanhVien);

        String taoPhieuMuon = "CREATE TABLE PhieuMuon (MaPM INTEGER,MaTT TEXT NOT NULL,MaTV INTEGER NOT NULL, MaSach INTEGER NOT NULL, Ngay DATE NOT NULL, traSach INTEGER NOT NULL, tienThue INTEGER NOT NULL,PRIMARY KEY(MaPM AUTOINCREMENT), FOREIGN KEY(MaSach) REFERENCES Sach(MaSach), FOREIGN KEY(MaTV) REFERENCES ThanhVien(MaTV), FOREIGN KEY(MaTT) REFERENCES ThuThu(MaTT))";
        db.execSQL(taoPhieuMuon);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
