package com.example.libmana.DTO;

public class LoaiSach {
    private String tenLoai;
    private int maLoai;

    public static final String TB_NAME = "LoaiSach";
    public static final String COL_MA = "MaLoai";
    public static final String COL_TEN = "tenLoai";

    public LoaiSach() {
    }

    public LoaiSach(String tenLoai, int maLoai) {
        this.tenLoai = tenLoai;
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }
}
