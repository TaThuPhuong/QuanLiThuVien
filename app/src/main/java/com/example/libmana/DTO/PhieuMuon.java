package com.example.libmana.DTO;

public class PhieuMuon {
    private int maPM,maTV,maSach,traSach,tienThue;
    private String maTT,Ngay;

    public static final String TB_NAME = "PhieuMuon";
    public static final String COL_MA_PM = "MaPM";
    public static final String COL_MA_TT = "MaTT";
    public static final String COL_MA_TV = "MaTV";
    public static final String COL_MA_SACH = "MaSach";
    public static final String COL_NGAY = "Ngay";
    public static final String COL_TRA_SACH = "traSach";
    public static final String COL_TIEN = "tienThue";

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, int maTV, int maSach, int traSach, int tienThue, String maTT, String ngay) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maSach = maSach;
        this.traSach = traSach;
        this.tienThue = tienThue;
        this.maTT = maTT;
        Ngay = ngay;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }
}
