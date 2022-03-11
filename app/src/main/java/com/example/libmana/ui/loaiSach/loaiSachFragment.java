package com.example.libmana.ui.loaiSach;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.libmana.Adapter.LoaiSachAdapter;
import com.example.libmana.DAO.LoaiSachDAO;
import com.example.libmana.DTO.LoaiSach;
import com.example.libmana.Main;
import com.example.libmana.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class loaiSachFragment extends Fragment {
    ListView lv;
    ArrayList<LoaiSach> list;
    FloatingActionButton them;
    EditText timKiem;
    ImageButton btnSearch;

    LoaiSachDAO dao;
    LoaiSachAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loai_sach, container, false);

        lv = v.findViewById(R.id.lvLoaiSach);
        them = v.findViewById(R.id.btnThemLoaiSach);
        timKiem = v.findViewById(R.id.edSearchLoaiSach);
        btnSearch = v.findViewById(R.id.btnSearchLoaiSach);

        dao = new LoaiSachDAO(getActivity());
        capNhatLv();

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.showDialogAdd(getActivity());
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                list = (ArrayList<LoaiSach>) dao.getSearch(timKiem.getText().toString());
                adapter = new LoaiSachAdapter(list,dao);
                lv.setAdapter(adapter);
            }
        });
        return v;
    }

    public void capNhatLv(){
        list = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new LoaiSachAdapter(list,dao);
        lv.setAdapter(adapter);
    }

}