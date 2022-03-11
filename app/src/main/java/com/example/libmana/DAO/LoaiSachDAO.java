package com.example.libmana.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libmana.DTO.LoaiSach;
import com.example.libmana.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    SQLiteDatabase db;

    public LoaiSachDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put(LoaiSach.COL_TEN, loaiSach.getTenLoai());
        return db.insert(LoaiSach.TB_NAME, null, values);
    }

    public int update(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put(LoaiSach.COL_TEN, loaiSach.getTenLoai());
        return db.update(LoaiSach.TB_NAME, values, "MaLoai = ?", new String[]{String.valueOf(loaiSach.getMaLoai())});
    }

    public int delete(LoaiSach loaiSach){
        String[] arr = new String[]{loaiSach.getMaLoai()+""};

        return db.delete(LoaiSach.TB_NAME,"MaLoai = ?",arr);
    }


    //    get tat ca data
    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }





    //    get data nhieu tham so
    private List<LoaiSach> getData(String sql, String...selectionArgs){
        List<LoaiSach> list = new ArrayList<LoaiSach>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setMaLoai(c.getInt(0));
            loaiSach.setTenLoai(c.getString(1));

            list.add(loaiSach);
        }
        return list;
    }

    public List<LoaiSach> getSearch(String tenLoai){
        String sql = "SELECT * FROM LoaiSach WHERE tenLoai LIKE '%"+tenLoai+"%' ";
        return getData(sql);
    }

    public List<String> getTenLoai() {
        String sql="SELECT tenLoai FROM LoaiSach";
        List<String> list = new ArrayList<String>();

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            String loaiSach = c.getString(0);
            list.add(loaiSach);
        }
        return list;
    }

    public LoaiSach getID(String ten){
        LoaiSach loaiSach = new LoaiSach();
        String[] args = new String[]{ten};
        Cursor c = db.rawQuery("SELECT maLoai, tenLoai FROM LoaiSach WHERE tenLoai = ?", args);
        if (c.moveToFirst()){
            loaiSach.setMaLoai(c.getInt(0)) ;
            loaiSach.setTenLoai(c.getString(1));
        }
        return loaiSach;
    }

    //    get data theo ten
    public LoaiSach getTen(String id){
        LoaiSach loaiSach = new LoaiSach();
        String[] args = new String[]{id+""};
        Cursor c = db.rawQuery("SELECT maLoai, tenLoai FROM LoaiSach WHERE maLoai = ?", args);
        if (c.moveToFirst()){
            loaiSach.setMaLoai(c.getInt(0)) ;
            loaiSach.setTenLoai(c.getString(1));
        }
        return loaiSach;
    }


}
