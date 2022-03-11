package com.example.libmana.ui.top10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libmana.Adapter.topAdapter;
import com.example.libmana.DAO.ThongKeDAO;
import com.example.libmana.DTO.ThongKe;
import com.example.libmana.R;

import java.util.ArrayList;

    public class top10_Fragment extends Fragment {
    ListView lv;
    ArrayList<ThongKe> list;
    topAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.top_fragment, container, false);
        lv = v.findViewById(R.id.lvTop);

        ThongKeDAO dao = new ThongKeDAO(getActivity());

        list = (ArrayList<ThongKe>) dao.getThongKe();
//        Toast.makeText(getActivity(), dao.getThongKe().toString(), Toast.LENGTH_SHORT).show();
        adapter = new topAdapter(list,dao);
        lv.setAdapter(adapter);



        return v;
    }
}
