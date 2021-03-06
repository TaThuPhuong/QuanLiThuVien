package com.example.libmana.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.libmana.DAO.LoaiSachDAO;
import com.example.libmana.DTO.LoaiSach;
import com.example.libmana.R;
import com.example.libmana.ui.loaiSach.loaiSachFragment;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachAdapter extends BaseAdapter {

    ArrayList<LoaiSach> lists;
    TextView tv_TenLoai;
    ImageView imgSua, imgXoa;

    LoaiSachDAO dao;

    public LoaiSachAdapter(ArrayList<LoaiSach> lists, LoaiSachDAO dao) {
        super();
        this.lists = lists;
        this.dao = dao;
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        LoaiSach loaiSach =lists.get(i);
        return loaiSach;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;
        if(convertView == null){
            v = View.inflate(parent.getContext(), R.layout.item_loai_sach,null);
        }else {
            v = convertView;
        }

        final LoaiSach item = lists.get(position);
        if (item != null) {
            tv_TenLoai = v.findViewById(R.id.tv_loai_sach);
            tv_TenLoai.setText(item.getTenLoai());

            imgXoa = v.findViewById(R.id.imgXoa);
            imgSua = v.findViewById(R.id.imgSua);

        }

        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("X??a lo???i s??ch?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("B???n ch???c ch???n x??a lo???i s??ch "+ item.getTenLoai());

                builder.setPositiveButton("Kh??ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int res = dao.delete(item);
                        if(res >0){
                            lists.remove(position);
                            notifyDataSetChanged();

                            Toast.makeText(parent.getContext(), "B???n v???a x??a lo???i s??ch:  "+ item.getTenLoai(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(parent.getContext(), "X??a l???i", Toast.LENGTH_SHORT).show();
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
                showDialogEdit(parent.getContext(), item,position);
            }
        });
        if (position % 2 == 0) {
            tv_TenLoai.setTextColor(Color.RED);
        } else {
            tv_TenLoai.setTextColor(Color.BLUE);
        }
        return v;

    }

//    dialog---------------------------------------------------------------
    public void showDialogAdd(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_loai_sach);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        EditText edTen = dialog.findViewById(R.id.them_loai_sach);
        Button btnAdd = dialog.findViewById(R.id.btnThemLoaiSach);
        Button btnClear = dialog.findViewById(R.id.xoaText);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edTen.setText("");
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiSach loaiSach = new LoaiSach();

                String ten = edTen.getText().toString();
                if(ten.length() < 2 || ten.equalsIgnoreCase("  ")){
                    Toast.makeText(context, "T??n lo???i s??ch kh??ng ???????c ????? tr???ng v?? ph???i > 2 k?? t???", Toast.LENGTH_SHORT).show();
                    return;
                }

                loaiSach.setTenLoai(ten);
                long res = dao.insert(loaiSach);
                if(res > 0){
                    lists.clear();
                    //load l???i t??? CSDL
                    lists.addAll(dao.getAll());
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

    public void showDialogEdit(Context context, LoaiSach loaiSach, int index){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_loai_sach);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView title = dialog.findViewById(R.id.titleThemLoaiSach);
        EditText edTen = dialog.findViewById(R.id.them_loai_sach);
        Button btnAdd = dialog.findViewById(R.id.btnThemLoaiSach);
        Button btnClear = dialog.findViewById(R.id.xoaText);

        title.setText("S???A LO???I S??CH");
        btnAdd.setText("C???P NH???T");
        //g??n d??? li???u
        edTen.setText(loaiSach.getTenLoai());
        //n??t b???m
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edTen.setText("");
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edTen.getText().toString();
                if(ten.isEmpty()){
                    Toast.makeText(context, "Kh??ng ???????c ????? tr???ng t??n", Toast.LENGTH_SHORT).show();
                    return; }
                loaiSach.setTenLoai(ten);
                int res = dao.update(loaiSach);
                if(res > 0){
                    lists.set(index, loaiSach);
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
