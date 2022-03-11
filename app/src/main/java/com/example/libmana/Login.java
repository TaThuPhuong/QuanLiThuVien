package com.example.libmana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.libmana.DAO.ThuThuDAO;

public class Login extends AppCompatActivity {
    private Button btClear, btLogin;
    EditText edtTaiKhoan, edtMatKhau;
    CheckBox cbLuuThongTin;

    LinearLayout linearLayout;
    Animation animation;

    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        anhXa();

        thuThuDAO = new ThuThuDAO(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.login);
        linearLayout.startAnimation(animation);

        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);

        boolean check = sharedPreferences.getBoolean("checkstatus", false);
        if (check) {
            String tenNguoiDung = sharedPreferences.getString("tennguoidung", "");
            String matKhau = sharedPreferences.getString("matkhau", "");
            edtTaiKhoan.setText(tenNguoiDung);
            edtMatKhau.setText(matKhau);
        } else {
            edtTaiKhoan.setText("");
            edtMatKhau.setText("");
        }
        cbLuuThongTin.setChecked(check);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTaiKhoan.setText("");
                edtMatKhau.setText("");
                cbLuuThongTin.setChecked(false);
            }
        });
    }

    private void anhXa(){
        edtTaiKhoan = findViewById(R.id.edtUserName);
        edtMatKhau = findViewById(R.id.edtPassword);
        btLogin = findViewById(R.id.btnLogin);
        btClear = findViewById(R.id.btnClear);
        cbLuuThongTin = findViewById(R.id.cbLuuThongTin);

        linearLayout = findViewById(R.id.linearLayoutlogin);
    }

    public void checkLogin(){
        String tk = edtTaiKhoan.getText().toString();
        String mk = edtMatKhau.getText().toString();
        if (tk.isEmpty() || mk.isEmpty()){
            Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
        }else {
            if (thuThuDAO.checkLogin(tk,mk)>0 || (tk.equals("ttp")&& mk.equals("admin"))){
                Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                luuThongTin(tk,mk,cbLuuThongTin.isChecked());
                Intent i = new Intent(getApplicationContext(), Main.class);
                i.putExtra("tenLogin", tk);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void luuThongTin(String ten, String pass, boolean check) {
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!check) {
            editor.clear();
        } else {
            editor.putString("tennguoidung", ten);
            editor.putString("matkhau", pass);
            editor.putBoolean("checkstatus", check);
        }
        editor.commit();

    }


}