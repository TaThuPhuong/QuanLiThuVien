package com.example.libmana.ui.phieuMuon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.libmana.Adapter.LoaiSachAdapter;
import com.example.libmana.Adapter.phieuMuonAdapter;
import com.example.libmana.DAO.PhieuMuonDAO;
import com.example.libmana.DTO.LoaiSach;
import com.example.libmana.DTO.PhieuMuon;
import com.example.libmana.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class phieuMuonFragment extends Fragment {
    ListView lv;
    List<PhieuMuon> list;
    FloatingActionButton them;


    phieuMuonAdapter adapter;
    PhieuMuonDAO dao;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phieu_muon, container, false);

        lv = v.findViewById(R.id.lvPhieuMuon);
        them = v.findViewById(R.id.btnPhieuMuon);

        dao = new PhieuMuonDAO(getActivity());
        capNhatLv();


        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.showDialogAdd(getActivity());
            }
        });

        return v;
    }

    public void capNhatLv(){
        list =  dao.getAll();
        dao = new PhieuMuonDAO(getActivity());
        adapter = new phieuMuonAdapter(list,dao);
        lv.setAdapter(adapter);
    }
}