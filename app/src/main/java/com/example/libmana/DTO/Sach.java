package com.example.libmana.DTO;

public class Sach {
    private int maSach,giaThue,loaiSach;
    private String tenSach;


    public static final String TB_NAME = "Sach";
    public static final String COL_MA = "MaSach";
    public static final String COL_GIA = "giaThue";
    public static final String COL_TEN = "tenSach";
    public static final String COL_LOAI = "MaLoai";

    public Sach() {
    }

    public Sach(int maSach, int giaThue, int loaiSach, String tenSach) {
        this.maSach = maSach;
        this.giaThue = giaThue;
        this.loaiSach = loaiSach;
        this.tenSach = tenSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getLoaiSach() {
        return loaiSach;
    }

    public void setLoaiSach(int loaiSach) {
        this.loaiSach = loaiSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
