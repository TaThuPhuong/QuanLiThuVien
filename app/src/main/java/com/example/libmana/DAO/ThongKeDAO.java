package com.example.libmana.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import com.example.libmana.DTO.Sach;
import com.example.libmana.DTO.ThongKe;
import com.example.libmana.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;
    @SuppressLint("NewApi")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //      Thong ke top10
    @SuppressLint("Range")
    public List<ThongKe> getThongKe() {
        String sqlTop = "SELECT maSach, count(maSach) AS soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";   // ODER BY .... DESC sap xep tu lon->nho trong cot so luong
        List<ThongKe> list = new ArrayList<ThongKe>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor c = db.rawQuery(sqlTop, null);

        while (c.moveToNext()) {
            ThongKe top = new ThongKe();
            Sach sach = sachDAO.getID(c.getString(0));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(c.getInt(c.getColumnIndex("soLuong")));
            list.add(top);
        }
        return list;
    }
    //      Thong ke doanh thu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(tienThue) AS doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});

        while (c.moveToNext()) {
            try {
                list.add(c.getInt(c.getColumnIndex("doanhThu")));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}
