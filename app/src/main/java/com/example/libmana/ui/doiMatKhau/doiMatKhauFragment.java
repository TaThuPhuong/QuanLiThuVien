package com.example.libmana.ui.doiMatKhau;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class doiMatKhauFragment extends Fragment {
    LinearLayout lnL;
    Animation animation;

    EditText MKcu, MKmoi, reMK;
    Button them,clear;

    ThuThuDAO dao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.doi_mat_khau_fragment, container, false);

        lnL = v.findViewById(R.id.linearLayoutchange);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.login);
        lnL.startAnimation(animation);

        MKmoi = v.findViewById(R.id.edMKMoi);
        MKcu = v.findViewById(R.id.edMKCu);
        reMK =v.findViewById(R.id.edNewPass);

        them = v.findViewById(R.id.btnDoiMK);
        clear = v.findViewById(R.id.btnNhapLai);

        dao = new ThuThuDAO(getActivity());

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MKmoi.setText("");
                MKcu.setText("");
                reMK.setText("");
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("taikhoan",MODE_PRIVATE);
                String user = pref.getString("tennguoidung","");
                String passOld = pref.getString("matkhau","");

                String pass_cu = MKcu.getText().toString();
                String pass = MKmoi.getText().toString();
                String rePass = reMK.getText().toString();

//                Toast.makeText(getActivity(), passOld, Toast.LENGTH_SHORT).show();
                if(passOld.equalsIgnoreCase("admin")){
                    Toast.makeText(getActivity(), "Admin không được đổi mật khẩu", Toast.LENGTH_SHORT).show();
                    MKmoi.setText("");
                    MKcu.setText("");
                    reMK.setText("");

                }

                if(passOld.equalsIgnoreCase("admin")){
                    Toast.makeText(getActivity(), "Admin không được đổi mật khẩu", Toast.LENGTH_SHORT).show();
                    MKmoi.setText("");
                    MKcu.setText("");
                    reMK.setText("");

                }else if(pass.isEmpty() || pass_cu.isEmpty() || rePass.isEmpty()){
                    Toast.makeText(getActivity(), "Bạn không được để trống các trường", Toast.LENGTH_SHORT).show();
                }else if(!pass_cu.equals(passOld)){
                    Toast.makeText(getActivity(), "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                }else if(pass.equalsIgnoreCase("admin")){
                    Toast.makeText(getActivity(), "Không được dùng pass này", Toast.LENGTH_SHORT).show();
                }else if(!pass.equals(rePass)){
                    Toast.makeText(getActivity(), "Mật khẩu nhập không trùng khớp", Toast.LENGTH_SHORT).show();
                }else {
                    ThuThu tt = dao.getID(user);
                    tt.setMatKhau(pass);

                    int res = dao.updatePass(tt);

                    if(res>0){
                        Toast.makeText(getActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        MKmoi.setText("");
                        MKcu.setText("");
                        reMK.setText("");
                    }else {
                        Toast.makeText(getActivity(), "Đổi mật khẩu lỗi", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



        return v;
    }

}
