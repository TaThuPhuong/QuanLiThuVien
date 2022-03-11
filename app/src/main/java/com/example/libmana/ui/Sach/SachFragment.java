package com.example.libmana.ui.Sach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.libmana.Adapter.LoaiSachAdapter;
import com.example.libmana.Adapter.SachAdapter;
import com.example.libmana.DAO.LoaiSachDAO;
import com.example.libmana.DAO.SachDAO;
import com.example.libmana.DTO.LoaiSach;
import com.example.libmana.DTO.Sach;
import com.example.libmana.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class SachFragment extends Fragment {
    ListView lv;
    ArrayList<Sach> list;
    FloatingActionButton them;
    EditText timKiem;
    ImageButton btnSearch;

    SachDAO dao;
    SachAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sach, container, false);

        lv = v.findViewById(R.id.lvSach);
        them = v.findViewById(R.id.btnThemSach);
        timKiem = v.findViewById(R.id.edSearchSach);
        btnSearch = v.findViewById(R.id.btnSearchSach);

        dao = new SachDAO(getActivity());
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
                list = (ArrayList<Sach>) dao.getSearch(timKiem.getText().toString());
                adapter = new SachAdapter(list,dao);
                lv.setAdapter(adapter);
            }
        });
        return v;
    }

    public void capNhatLv(){
        list = (ArrayList<Sach>) dao.getAll();
        adapter = new SachAdapter(list,dao);
        lv.setAdapter(adapter);
    }
}