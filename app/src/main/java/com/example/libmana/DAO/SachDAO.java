package com.example.libmana.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libmana.DTO.LoaiSach;
import com.example.libmana.DTO.Sach;
import com.example.libmana.DTO.ThanhVien;
import com.example.libmana.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase db;

    public SachDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(Sach sach){
        ContentValues values = new ContentValues();
        values.put(Sach.COL_TEN, sach.getTenSach());
        values.put(Sach.COL_GIA, sach.getGiaThue());
        values.put(Sach.COL_LOAI, sach.getLoaiSach());
        return db.insert("Sach", null, values);
    }

    public int update(Sach sach){
        ContentValues values = new ContentValues();

        values.put(Sach.COL_TEN, sach.getTenSach());
        values.put(Sach.COL_GIA, sach.getGiaThue());
        values.put(Sach.COL_LOAI, sach.getLoaiSach());

        String[] arr = new String[]{sach.getMaSach()+""};
        return db.update("Sach" , values, "MaSach = ?", arr);
    }

    public int delete(Sach sach){
        String[] arr = new String[]{sach.getMaSach()+""};

        return db.delete(Sach.TB_NAME,"MaSach = ?",arr);
    }

    //    get tat ca data
    public List<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }

    //    get data theo id
    public Sach getID(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
    }

    //    get data nhieu tham so
    private List<Sach> getData(String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<Sach>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            Sach sach = new Sach();

            sach.setMaSach(c.getInt(0));
            sach.setTenSach(c.getString(1));
            sach.setGiaThue(c.getInt(2));
            sach.setLoaiSach(c.getInt(3));

            list.add(sach);
        }
        return list;
    }

    public List<Sach> getSearch(String tenSach){
        String sql = "SELECT * FROM Sach WHERE tenSach LIKE '%"+tenSach+"%' ";
        return getData(sql);
    }

    public List<String> getTenSach() {
        String sql="SELECT tenSach FROM Sach";
        List<String> list = new ArrayList<String>();

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            String tenSach = c.getString(0);
            list.add(tenSach);
        }
        return list;
    }

    public Sach getMaGia(String ten){
        Sach s = new Sach();
        String[] args = new String[]{ten};
        Cursor c = db.rawQuery("SELECT MaSach, tenSach, giaThue FROM Sach WHERE tenSach = ?", args);
        if (c.moveToFirst()){
            s.setMaSach(c.getInt(0)); ;
            s.setTenSach(c.getString(1));
            s.setGiaThue(c.getInt(2));
        }
        return s;
    }


}
