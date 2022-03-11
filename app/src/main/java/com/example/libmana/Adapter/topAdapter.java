package com.example.libmana.Adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.libmana.DAO.ThongKeDAO;
import com.example.libmana.DTO.ThongKe;
import com.example.libmana.R;

import java.util.ArrayList;


public class topAdapter extends BaseAdapter{
    TextView tvSach, tvSL;
    ArrayList<ThongKe> lst;

    ThongKeDAO dao;

    public topAdapter(ArrayList<ThongKe> lst, ThongKeDAO dao) {
        super();
        this.lst = lst;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if(view == null){
            v = View.inflate(viewGroup.getContext(), R.layout.item_top,null);
        }else {
            v = view;
        }

        final ThongKe item = lst.get(i);
        if (item != null) {
            tvSach = v.findViewById(R.id.tvSach);
            tvSach.setText(item.getTenSach());
            tvSL = v.findViewById(R.id.tvSL);
            tvSL.setText("" + item.getSoLuong());
        }
        return v;
    }
}
