package com.example.libmana.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libmana.DTO.PhieuMuon;
import com.example.libmana.DTO.Sach;
import com.example.libmana.Database.DbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonDAO {

    private SQLiteDatabase db;
    Date date = new Date();
    DateFormat dateFormat = null;

    public PhieuMuonDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon obj){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        values.put(PhieuMuon.COL_MA_TT, obj.getMaTT());
        values.put(PhieuMuon.COL_MA_TV, obj.getMaTV());
        values.put(PhieuMuon.COL_MA_SACH, obj.getMaSach());
        values.put(PhieuMuon.COL_TIEN, obj.getTienThue());
        values.put(PhieuMuon.COL_TRA_SACH, obj.getTraSach());
        values.put(PhieuMuon.COL_NGAY, obj.getNgay());

        return db.insert(PhieuMuon.TB_NAME, null, values);
    }

    public int update(PhieuMuon obj){

        ContentValues values = new ContentValues();
        values.put(PhieuMuon.COL_MA_TT, obj.getMaTT());
        values.put(PhieuMuon.COL_MA_TV, obj.getMaTV());
        values.put(PhieuMuon.COL_MA_SACH, obj.getMaSach());
        values.put(PhieuMuon.COL_TIEN, obj.getTienThue());
        values.put(PhieuMuon.COL_TRA_SACH, obj.getTraSach());

        return db.update(PhieuMuon.TB_NAME, values, "maPM = ?", new String[]{String.valueOf(obj.getMaPM())});
    }

    public int delete(PhieuMuon pm){
        String[] arr = new String[]{pm.getMaPM()+""};

        return db.delete(PhieuMuon.TB_NAME, "MaPM = ?", arr);
    }

    //    get tat ca data
    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }

    //    get data theo id
    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list = getData(sql, id);
        return list.get(0);
    }

    //    get data nhieu tham so
    private List<PhieuMuon> getData(String sql, String...selectionArgs) throws NullPointerException {

        List<PhieuMuon> list = new ArrayList<PhieuMuon>();
        Cursor c = db.rawQuery(sql, selectionArgs);

        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                PhieuMuon obj = new PhieuMuon();
                obj.setMaPM(c.getInt(0));
                obj.setMaTT(c.getString(1));
                obj.setMaTV(c.getInt(2));
                obj.setMaSach(c.getInt(3));
                obj.setTienThue(c.getInt(6));
                obj.setTraSach(c.getInt(5));

                obj.setNgay(c.getString(4));

                list.add(obj);
                c.moveToNext();
            }
        }
        return list;
    }


}
