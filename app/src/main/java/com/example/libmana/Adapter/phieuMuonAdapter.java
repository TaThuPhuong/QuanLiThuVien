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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.libmana.DAO.LoaiSachDAO;
import com.example.libmana.DAO.PhieuMuonDAO;
import com.example.libmana.DAO.SachDAO;
import com.example.libmana.DAO.thanhVienDAO;
import com.example.libmana.DTO.PhieuMuon;

import com.example.libmana.DTO.Sach;
import com.example.libmana.DTO.ThanhVien;
import com.example.libmana.R;
import com.example.libmana.ui.phieuMuon.phieuMuonFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class phieuMuonAdapter extends BaseAdapter {
    TextView tvtV,tvsach,tvgia,tvngay,tvtra;
    ImageView imgSua, imgXoa;
    CheckBox traSach;

    List<PhieuMuon> lst;
    PhieuMuonDAO pmdao;
    SachDAO sdao;
    thanhVienDAO tvdao;
    String sach,tv;
    int maSach,matv,tien;

    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
    Date date = new Date();

    phieuMuonFragment pmF = new phieuMuonFragment();

    public phieuMuonAdapter(List<PhieuMuon> lst, PhieuMuonDAO pmdao) {
        super();
        this.lst = lst;
        this.pmdao = pmdao;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        PhieuMuon pm = lst.get(i);
        return pm;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if(view == null){
            v = View.inflate(viewGroup.getContext(), R.layout.item_phieu_muon,null);
        }else {
            v = view;
        }

        PhieuMuon pm = lst.get(i);

        if (pm != null) {
            tvtV = v.findViewById(R.id.tv_thanhVien);
            tvsach = v.findViewById(R.id.tv_sach);
            tvgia = v.findViewById(R.id.tv_giaThue);
            tvngay = v.findViewById(R.id.tv_ngay);
            tvtra = v.findViewById(R.id.tvTraSach);
            
            imgSua = v.findViewById(R.id.imgSua);
            imgXoa = v.findViewById(R.id.imgXoa);

            sdao = new SachDAO(viewGroup.getContext());
            tvdao = new thanhVienDAO(viewGroup.getContext());

            Sach s = sdao.getID(String.valueOf(pm.getMaSach()));
            ThanhVien tv = tvdao.getID(String.valueOf(pm.getMaTV()));

            tvtV.setText(tv.getHoTen());
            tvsach.setText(s.getTenSach());
            tvngay.setText(pm.getNgay());
            tvgia.setText(s.getGiaThue()+"");

            if(pm.getTraSach() == 1){
                tvtra.setText("Đã trả sách");
                tvtra.setTextColor(Color.GRAY);
            }else {
                tvtra.setText("Chưa trả sách");
                tvtra.setTextColor(Color.RED);
            }

        }


        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                builder.setTitle("Xóa phiếu mượn?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Bạn chắc chắn xóa phiếu mượn ?");

                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int res = pmdao.delete(pm);
                        if(res >0){
                            lst.remove(i);
                            notifyDataSetChanged();
                            Toast.makeText(viewGroup.getContext(), "Bạn vừa xóa phiếu mượn ", Toast.LENGTH_SHORT).show();
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
                showDialogEdit(viewGroup.getContext(), pm,i);
            }
        });

        return v;
    }

//===========
    public void showDialogAdd(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_phieu_muon);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        Spinner tenSach = dialog.findViewById(R.id.spSach);
        Spinner thanhVien = dialog.findViewById(R.id.spTV);

        CheckBox traSach = dialog.findViewById(R.id.cbTraSach);

        Button btnAdd = dialog.findViewById(R.id.btnLuuThemSach);
        Button btnClear = dialog.findViewById(R.id.xoaTextSach);

        pmdao = new PhieuMuonDAO(dialog.getContext());

        SachDAO sDAO = new SachDAO(dialog.getContext());
        thanhVienDAO tvDAO = new thanhVienDAO(dialog.getContext());

        List<String> listTen = sDAO.getTenSach();
        List<String> listTenTV = tvDAO.getTenTV();

        ArrayAdapter arrayAdapter = new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listTen);
        tenSach.setAdapter(arrayAdapter);

        ArrayAdapter array= new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listTenTV);
        thanhVien.setAdapter(array);



        tenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sach = adapterView.getItemAtPosition(position).toString();
                maSach = sDAO.getMaGia(sach).getMaSach();
                tien = sDAO.getMaGia(sach).getGiaThue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        thanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv = adapterView.getItemAtPosition(i).toString();
                matv = tvDAO.getMa(tv).getMaTV();
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
                PhieuMuon pm = new PhieuMuon();
                if(traSach.isChecked()){
                    pm.setTraSach(1);
                }else {
                    pm.setTraSach(0);
                }

                pm.setMaSach(maSach);
                pm.setMaTV(matv);
                pm.setNgay(String.valueOf(sdf.format(date)));
                pm.setTienThue(tien);
                pm.setMaTT("");

                long res = pmdao.insert(pm);
                if(res > 0){
                    lst.clear();
                    //load lại từ CSDL
                    lst.addAll(pmdao.getAll());
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

    public void showDialogEdit(Context context, PhieuMuon pm, int index){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_phieu_muon);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView title = dialog.findViewById(R.id.titleThemPhieuMuon);
        title.setText("Sửa Phiếu Mượn");

        Spinner tenSach = dialog.findViewById(R.id.spSach);
        Spinner thanhVien = dialog.findViewById(R.id.spTV);

        Button btnAdd = dialog.findViewById(R.id.btnLuuThemSach);
        Button btnClear = dialog.findViewById(R.id.xoaTextSach);

        btnAdd.setText("Cập Nhật");

        CheckBox traSach = dialog.findViewById(R.id.cbTraSach);


        SachDAO sDAO = new SachDAO(dialog.getContext());
        thanhVienDAO tvDAO = new thanhVienDAO(dialog.getContext());

        List<String> listTen = sDAO.getTenSach();
        List<String> listTenTV = tvDAO.getTenTV();

        ArrayAdapter arrayAdapter = new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listTen);
        tenSach.setAdapter(arrayAdapter);

        ArrayAdapter array= new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listTenTV);
        thanhVien.setAdapter(array);

        int vitriSach=-1;
        int vitriTen = -1;
        int mS = lst.get(index).getMaSach();
        int mT = lst.get(index).getMaTV();

        for (int n = 0; n < listTen.size(); n++) {

            int x = sDAO.getMaGia(listTen.get(n)).getMaSach();
            if (x == mS) {
                vitriSach = n;
                break;
            }
        }

        for (int m = 0; m < listTenTV.size(); m++) {

            int x = tvDAO.getMa(listTenTV.get(m)).getMaTV();
            if (x == mT) {
                vitriTen = m;
                break;
            }
        }

        //gán dữ liệu
        if(pm.getTraSach() == 1){
            traSach.setChecked(true);
        }else {
            traSach.setChecked(false);
        }
        tenSach.setSelection(vitriSach);
        thanhVien.setSelection(vitriTen);

        tenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sach = adapterView.getItemAtPosition(position).toString();
                maSach = sDAO.getMaGia(sach).getMaSach();
                tien = sDAO.getMaGia(sach).getGiaThue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        thanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv = adapterView.getItemAtPosition(i).toString();
                matv = tvDAO.getMa(tv).getMaTV();
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
                pm.setTienThue(tien);
                pm.setMaSach(maSach);
                pm.setMaTV(matv);
                if(traSach.isChecked() == true){
                    pm.setTraSach(1);
                }else {
                    pm.setTraSach(0);
                }
                pm.setMaTT("");


                int res = pmdao.update(pm);

                if(res > 0){
                    lst.clear();
                    //load lại từ CSDL
                    lst.addAll(pmdao.getAll());
                    //báo cho Adapter
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
