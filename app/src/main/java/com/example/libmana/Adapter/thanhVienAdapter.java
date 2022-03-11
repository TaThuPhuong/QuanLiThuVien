package com.example.libmana.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.libmana.DAO.thanhVienDAO;
import com.example.libmana.DTO.LoaiSach;
import com.example.libmana.DTO.ThanhVien;
import com.example.libmana.R;

import java.util.ArrayList;

public class thanhVienAdapter extends BaseAdapter {

    ArrayList<ThanhVien> lists;
    TextView tv_ten,tv_ns;
    ImageView imgSua, imgXoa;

    thanhVienDAO dao;

    public thanhVienAdapter(ArrayList<ThanhVien> lists, thanhVienDAO dao) {
        this.lists = lists;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        ThanhVien tv = lists.get(i);
        return tv;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if(view == null){
            v = View.inflate(viewGroup.getContext(), R.layout.item_tv,null);
        }else {
            v = view;
        }

        ThanhVien tv = lists.get(i);
        if (tv != null) {
            tv_ten = v.findViewById(R.id.tv_tenTV);
            tv_ten.setText(tv.getHoTen());

            tv_ns = v.findViewById(R.id.tv_nsTV);
            tv_ns.setText("Năm Sinh: "+tv.getNamSinh());

            imgXoa = v.findViewById(R.id.imgXoa);
            imgSua = v.findViewById(R.id.imgSua);

        }

        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                builder.setTitle("Xóa thành viên?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Bạn chắc chắn xóa thành viên "+ tv.getHoTen());

                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int res = dao.delete(tv);
                        if(res >0){
                            lists.remove(i);
                            notifyDataSetChanged();

                            Toast.makeText(viewGroup.getContext(), "Bạn vừa xóa thành viên:  "+ tv.getHoTen(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(viewGroup.getContext(), "Xóa lỗi", Toast.LENGTH_SHORT).show();
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
                showDialogEdit(viewGroup.getContext(), tv,i);
            }
        });
        return v;
    }






    //    dialog---------------------------------------------------------------
    public void showDialogAdd(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_tv);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        EditText edTen = dialog.findViewById(R.id.ten_tv);
        EditText edNs = dialog.findViewById(R.id.namSinh_tv);
        Button btnAdd = dialog.findViewById(R.id.btnThemTV);
        Button btnClear = dialog.findViewById(R.id.xoaText);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThanhVien tV = new ThanhVien();

                String ten = edTen.getText().toString();
                String ns = edNs.getText().toString();

                if(ten.length() < 2 || ten.equalsIgnoreCase("  ")){
                    Toast.makeText(context, "Tên thành viên không được để trống và phải > 2 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }else if(ns.length() != 4){
                    Toast.makeText(context, "Năm sinh không được để trống và phải là năm", Toast.LENGTH_SHORT).show();
                    return;
                }


                tV.setHoTen(ten);
                tV.setNamSinh(ns);


                long res = dao.insert(tV);
                if(res > 0){
                    lists.clear();
                    //load lại từ CSDL
                    lists.addAll(dao.getAll());
                    //báo cho Adapter
                    notifyDataSetChanged();
                    Toast.makeText(context, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    //tắt hiển thị dialag
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                } }});

        dialog.show();
    }

    public void showDialogEdit(Context context, ThanhVien tv, int index){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_tv);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView title = dialog.findViewById(R.id.titleThanhVien);

        EditText edTen = dialog.findViewById(R.id.ten_tv);
        EditText edNs = dialog.findViewById(R.id.namSinh_tv);
        Button btnAdd = dialog.findViewById(R.id.btnThemTV);
        Button btnClear = dialog.findViewById(R.id.xoaText);



        title.setText("SỬA THÀNH VIÊN");
        btnAdd.setText("CẬP NHẬT");
        //gán dữ liệu
        edTen.setText(tv.getHoTen());
        edNs.setText(tv.getNamSinh());
        //nút bấm
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edTen.getText().toString();
                String ns = edNs.getText().toString();

                if(ten.length() < 2 || ten.equalsIgnoreCase("  ")){
                    Toast.makeText(context, "Tên thành viên không được để trống và phải > 2 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }else if(ns.length() != 4 ){
                    Toast.makeText(context, "Năm sinh không được để trống và phải là năm", Toast.LENGTH_SHORT).show();
                    return;
                }

                tv.setHoTen(ten);
                tv.setNamSinh(ns);

                int res = dao.update(tv);
                if(res > 0){
                    lists.clear();
                    //load lại từ CSDL
                    lists.addAll(dao.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();//ẩn
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}
