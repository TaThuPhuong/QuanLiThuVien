package com.example.libmana.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libmana.DTO.LoaiSach;
import com.example.libmana.DTO.ThanhVien;
import com.example.libmana.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class thanhVienDAO {

    private SQLiteDatabase db;

    public thanhVienDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put(ThanhVien.COL_TEN, thanhVien.getHoTen());
        values.put(ThanhVien.COL_YEAR, thanhVien.getNamSinh());

        return db.insert(ThanhVien.TB_NAME, null, values);
    }

    public int update(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put(ThanhVien.COL_TEN, thanhVien.getHoTen());
        values.put(ThanhVien.COL_YEAR, thanhVien.getNamSinh());
        return db.update(ThanhVien.TB_NAME, values, "maTV = ?", new String[]{String.valueOf(thanhVien.getMaTV())});
    }

    public int delete(ThanhVien tv){
        String[] arr = new String[]{tv.getMaTV()+""};

        return db.delete(ThanhVien.TB_NAME, "maTV = ?", arr);
    }

    //    get tat ca data
    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }

    //    get data theo id
    public ThanhVien getID(String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> list = getData(sql, id);
        return list.get(0);
    }

    //    get data nhieu tham so
    private List<ThanhVien> getData(String sql, String...selectionArgs){
        List<ThanhVien> list = new ArrayList<ThanhVien>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setMaTV(c.getInt(0));
            thanhVien.setHoTen(c.getString(1));
            thanhVien.setNamSinh(c.getString(2));

            list.add(thanhVien);
        }
        return list;
    }

    public List<ThanhVien> getSearch(String hoTen){
        String sql = "SELECT * FROM ThanhVien WHERE hoTen LIKE '%"+hoTen+"%' ";
        return getData(sql);
    }

    public List<String> getTenTV() {
        String sql="SELECT hoTen FROM ThanhVien";
        List<String> list = new ArrayList<String>();

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            String tenTV = c.getString(0);
            list.add(tenTV);
        }
        return list;
    }

    public ThanhVien getMa(String ten){
        ThanhVien tv = new ThanhVien();
        String[] args = new String[]{ten};
        Cursor c = db.rawQuery("SELECT MaTV, hoTen FROM ThanhVien WHERE hoTen = ?", args);
        if (c.moveToFirst()){
            tv.setMaTV(c.getInt(0)); ;
            tv.setHoTen(c.getString(1));
        }
        return tv;
    }
}
