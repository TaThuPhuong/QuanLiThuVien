package com.example.libmana.ui.thanhVien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libmana.Adapter.thanhVienAdapter;
import com.example.libmana.DAO.thanhVienDAO;
import com.example.libmana.DTO.ThanhVien;
import com.example.libmana.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class thanhVienFragment extends Fragment {
    ListView lv;
    ArrayList<ThanhVien> list;
    FloatingActionButton them;
    EditText timKiem;
    ImageButton btnSearch;

    thanhVienDAO dao;
    thanhVienAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.thanh_vien_fragment, container, false);

        lv = v.findViewById(R.id.lvThanhVien);
        timKiem = v.findViewById(R.id.edSearchTV);
        them = v.findViewById(R.id.btnThemTV);
        btnSearch = v.findViewById(R.id.btnSearchTV);
        dao = new thanhVienDAO(getActivity());
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
                list = (ArrayList<ThanhVien>) dao.getSearch(timKiem.getText().toString());
                adapter = new thanhVienAdapter(list,dao);
                lv.setAdapter(adapter);
            }
        });
        return v;

    }

    public void capNhatLv(){
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new thanhVienAdapter(list,dao);
        lv.setAdapter(adapter);
    }
}
