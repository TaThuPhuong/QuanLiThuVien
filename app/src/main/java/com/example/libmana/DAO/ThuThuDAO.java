package com.example.libmana.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libmana.DTO.ThuThu;
import com.example.libmana.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;

    public ThuThuDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put(ThuThu.COL_MA, thuThu.getMaTT());
        values.put(ThuThu.COL_TEN, thuThu.getHoTen());
        values.put(ThuThu.COL_MK, thuThu.getMatKhau());
        return db.insert(ThuThu.TB_NAME, null, values);
    }

    public int updatePass(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put(ThuThu.COL_TEN, thuThu.getHoTen());
        values.put(ThuThu.COL_MK, thuThu.getMatKhau());
        return db.update(ThuThu.TB_NAME, values, "maTT = ?", new String[]{String.valueOf(thuThu.getMaTT())});
    }

    public int delete(String id){
        return db.delete(ThuThu.TB_NAME, "maTT = ?", new String[]{id});
    }

    //    get tat ca data
    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }

    //    get data theo id
    public ThuThu getID(String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list = getData(sql, id);
        return list.get(0);
    }

    //    get data nhieu tham so
    private List<ThuThu> getData(String sql, String...selectionArgs){
        List<ThuThu> list = new ArrayList<ThuThu>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThuThu thuThu = new ThuThu();
            thuThu.setMaTT(c.getString(0));
            thuThu.setHoTen(c.getString(1));
            thuThu.setMatKhau(c.getString(2));

            list.add(thuThu);
        }
        return list;
    }

    //    check login
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?";
        List<ThuThu> list = getData(sql, id, password);
        if (list.size() == 0){
            return -1;
        }
        return 1;
    }

}
