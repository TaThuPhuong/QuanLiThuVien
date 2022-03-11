package com.example.libmana.ui.themTV;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libmana.DAO.ThuThuDAO;
import com.example.libmana.DTO.ThuThu;
import com.example.libmana.R;

import java.util.ArrayList;

public class themThanhVienFragment extends Fragment {
    LinearLayout lnL;
    Animation animation;

    EditText tenTK, tenTT, mk,reMK;
    Button them,clear;

    ThuThuDAO ttDAO;
    ArrayList<ThuThu> lst;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.them_nguoi_dung_fragment, container, false);

        lnL = v.findViewById(R.id.linearLayoutchange);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.login);
        lnL.startAnimation(animation);

        tenTK = v.findViewById(R.id.edNewTK);
        tenTT = v.findViewById(R.id.edNewTen);
        mk = v.findViewById(R.id.edNewMK);
        reMK = v.findViewById(R.id.edXNNewPass);

        them = v.findViewById(R.id.btnNewTT);
        clear = v.findViewById(R.id.btnNhapLai);

        ttDAO = new ThuThuDAO(getActivity());
        lst = new ArrayList<>();

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenTK.setText("");
                tenTT.setText("");
                mk.setText("");
                reMK.setText("");
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThuThu tt = new ThuThu();

                String tTK = tenTK.getText().toString();
                String tTT = tenTT.getText().toString();
                String nMK = mk.getText().toString();
                String rMK = reMK.getText().toString();
                
                if(tTK.isEmpty() || tTT.isEmpty() || nMK.isEmpty() || rMK.isEmpty()){
                    Toast.makeText(getActivity(), "Các trường không được để trống", Toast.LENGTH_SHORT).show();
                }else if(tTK.equals(" ") || tTT.equals(" ")|| nMK.equals(" ") || rMK.equals(" ")) {
                    Toast.makeText(getActivity(), "Các trường không được để trống", Toast.LENGTH_SHORT).show();
                }else if(nMK.equalsIgnoreCase("admin")){
                    Toast.makeText(getActivity(), "Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                }else if(!rMK.equals(nMK)){
                        Toast.makeText(getActivity(), "Mật khẩu phải trùng nhau", Toast.LENGTH_SHORT).show();
                }else {
                    tt.setMaTT(tTK);
                    tt.setMatKhau(nMK);
                    tt.setHoTen(tTT);

                    long res = ttDAO.insert(tt);

                    if(res>0){
                        lst.clear();
                        //load lại từ CSDL
                        lst.addAll(ttDAO.getAll());

                        Toast.makeText(getActivity(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        tenTK.setText("");
                        tenTT.setText("");
                        mk.setText("");
                        reMK.setText("");

                    }else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        return v;
    }


}
