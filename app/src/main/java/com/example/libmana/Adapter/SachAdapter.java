package com.example.libmana.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.libmana.DAO.LoaiSachDAO;
import com.example.libmana.DAO.SachDAO;
import com.example.libmana.DTO.LoaiSach;
import com.example.libmana.DTO.Sach;
import com.example.libmana.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SachAdapter extends BaseAdapter {
    TextView ten,gia,loai;
    ImageView imgSua, imgXoa;
    ArrayList<Sach> lst;
    SachDAO dao;
    String spn;
    int maLoai,maUD;

    public SachAdapter(ArrayList<Sach> lst, SachDAO dao) {
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
        Sach sach = lst.get(i);
        return sach;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if(view == null){
            v = View.inflate(viewGroup.getContext(), R.layout.item_sach,null);
        }else {
            v = view;
        }

        Sach s = lst.get(i);


        if (s != null) {
            LoaiSachDAO lsDAO = new LoaiSachDAO(viewGroup.getContext());
            LoaiSach loaiSach = lsDAO.getTen(String.valueOf(s.getLoaiSach()));
            ten = v.findViewById(R.id.tv_tenSach);
            ten.setText(s.getTenSach());

            gia = v.findViewById(R.id.tv_giaThue);
            gia.setText(s.getGiaThue()+"");

            loai = v.findViewById(R.id.tv_loaiSach);
            loai.setText(loaiSach.getTenLoai());

            imgXoa = v.findViewById(R.id.imgXoa);
            imgSua = v.findViewById(R.id.imgSua);
        }

        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                builder.setTitle("X??a s??ch?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("B???n ch???c ch???n x??a s??ch "+ s.getTenSach());

                builder.setPositiveButton("Kh??ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int res = dao.delete(s);
                        if(res >0){
                            lst.remove(i);
                            notifyDataSetChanged();
                            Toast.makeText(viewGroup.getContext(), "B???n v???a x??a s??ch:  "+ s.getTenSach(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(viewGroup.getContext(), "X??a l???i", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEdit(viewGroup.getContext(),s,i);
            }
        });



        return v;
    }


    //    dialog---------------------------------------------------------------
    public void showDialogAdd(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_sach);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        EditText tenSach = dialog.findViewById(R.id.ten_sach);
        EditText tienThue = dialog.findViewById(R.id.tien_thue);

        Spinner loaiSach = dialog.findViewById(R.id.spLoaiSach);

        Button btnAdd = dialog.findViewById(R.id.btnLuuThemSach);
        Button btnClear = dialog.findViewById(R.id.xoaTextSach);

        dao = new SachDAO(dialog.getContext());

        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(dialog.getContext());

        List<String> listLs = loaiSachDAO.getTenLoai();

        ArrayAdapter arrayAdapter = new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listLs);
        loaiSach.setAdapter(arrayAdapter);

        loaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                spn = adapterView.getItemAtPosition(position).toString();
                maLoai = loaiSachDAO.getID(spn).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sach sach = new Sach();

                String ten = tenSach.getText().toString();
                String gia = tienThue.getText().toString();

                if(ten.length() < 2 || ten.equalsIgnoreCase("  ")){
                    Toast.makeText(context, "T??n s??ch kh??ng ???????c ????? tr???ng v?? ph???i > 2 k?? t???", Toast.LENGTH_SHORT).show();
                    return;
                }else if( gia.length() < 4 || gia.equalsIgnoreCase("   ")){
                    Toast.makeText(context, "Gi?? kh??ng ???????c ????? tr???ng v?? ph???i l?? ????n v??? ngh??n", Toast.LENGTH_SHORT).show();
                    return;
                }

                int tien = Integer.parseInt(gia);

                sach.setTenSach(ten);
                sach.setGiaThue(tien);
                sach.setLoaiSach(maLoai);

                long res = dao.insert(sach);
                if(res > 0){
                    lst.clear();
                    //load l???i t??? CSDL
                    lst.addAll(dao.getAll());
                    //b??o cho Adapter
                    notifyDataSetChanged();
                    Toast.makeText(context, "Th??m m???i th??nh c??ng", Toast.LENGTH_SHORT).show();
                    //t???t hi???n th??? dialag
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Th??m m???i th???t b???i", Toast.LENGTH_SHORT).show();
                } }});

        dialog.show();
    }

    public void showDialogEdit(Context context, Sach s, int index){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_sach);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView title = dialog.findViewById(R.id.titleThemSach);
        EditText tenSach = dialog.findViewById(R.id.ten_sach);
        EditText tienThue = dialog.findViewById(R.id.tien_thue);


        Spinner loaiSach = dialog.findViewById(R.id.spLoaiSach);

        Button btnAdd = dialog.findViewById(R.id.btnLuuThemSach);
        Button btnClear = dialog.findViewById(R.id.xoaTextSach);

        title.setText("S???A S??CH");
        btnAdd.setText("C???P NH???T");

        SachDAO dao = new SachDAO(dialog.getContext());

        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(dialog.getContext());
        List<String> listLs = loaiSachDAO.getTenLoai();

        ArrayAdapter arrayAdapter = new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listLs);
        loaiSach.setAdapter(arrayAdapter);

        int vitri = -1;
        int ml = lst.get(index).getLoaiSach();
        for (int n = 0; n < listLs.size(); n++) {

            int x = loaiSachDAO.getID(listLs.get(n)).getMaLoai();
            if (x == ml) {
                vitri = n;
                break;
            }
        }

        //g??n d??? li???u
        tenSach.setText(s.getTenSach());
        tienThue.setText(s.getGiaThue()+"");
        loaiSach.setSelection(vitri);


        loaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                spn = adapterView.getItemAtPosition(position).toString();
                maUD = loaiSachDAO.getID(spn).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //n??t b???m
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String ten = tenSach.getText().toString();
                String gia = tienThue.getText().toString();

                if(ten.length() < 2 || ten.equalsIgnoreCase("  ")){
                    Toast.makeText(context, "T??n s??ch kh??ng ???????c ????? tr???ng v?? ph???i > 2 k?? t???", Toast.LENGTH_SHORT).show();
                    return;
                }else if( gia.length() < 4 || gia.equalsIgnoreCase("   ")){
                    Toast.makeText(context, "Gi?? kh??ng ???????c ????? tr???ng v?? ph???i l?? ????n v??? ngh??n", Toast.LENGTH_SHORT).show();
                    return;
                }

                int tien = Integer.parseInt(gia);

                s.setTenSach(ten);
                s.setGiaThue(tien);
                s.setLoaiSach(maUD);

                int res = dao.update(s);

                if(res > 0){
                    lst.clear();
                    //load l???i t??? CSDL
                    lst.addAll(dao.getAll());
                    //b??o cho Adapter
                    notifyDataSetChanged();
                    Toast.makeText(context, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();//???n

                }else {
                    Toast.makeText(context, "C???p nh???t th???t b???i", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}
