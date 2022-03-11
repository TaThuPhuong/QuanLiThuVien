package com.example.libmana;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.libmana.DAO.ThuThuDAO;
import com.example.libmana.databinding.MainAppBinding;
import com.example.libmana.ui.Sach.SachFragment;
import com.example.libmana.ui.doanhThu.doanhThuFragment;
import com.example.libmana.ui.doiMatKhau.doiMatKhauFragment;
import com.example.libmana.ui.loaiSach.loaiSachFragment;
import com.example.libmana.ui.phieuMuon.phieuMuonFragment;
import com.example.libmana.ui.thanhVien.thanhVienFragment;
import com.example.libmana.ui.themTV.themThanhVienFragment;
import com.example.libmana.ui.top10.top10_Fragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class Main extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    TextView edUser;
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_app);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

//        set toolbar thay the cho actionBar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu);
        ab.setDisplayHomeAsUpEnabled(true);

////        dung fragment PhieuMuon lam home
        FragmentManager manager = getSupportFragmentManager();
        phieuMuonFragment phieuMuon_fragment = new phieuMuonFragment();
        manager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, phieuMuon_fragment)
                .commit();

        NavigationView nv = findViewById(R.id.nav_view);
//        show user in header
        mHeaderView = nv.getHeaderView(0);
        edUser = mHeaderView.findViewById(R.id.tvUser);

        Intent i = getIntent();
        String user = i.getStringExtra("tenLogin");


//        Admin co quyen add user
        if(user.equalsIgnoreCase("ttp")){
            nv.getMenu().findItem(R.id.nav_themNguoiDung).setVisible(true);
        }

        edUser.setText(user);

//        Dieu huong Navigation
        nv.setNavigationItemSelectedListener((item) ->{

            switch (item.getItemId()){
                case R.id.nav_phieuMuon:
                    setTitle("Quản Lý Phiếu Mượn");
                    phieuMuonFragment phieuMuonFragment = new phieuMuonFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,phieuMuonFragment).commit();
                    break;

                case R.id.nav_Sach:
                    setTitle("Quản Lý Sách");
                    SachFragment sachFragment = new SachFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,sachFragment).commit();
                    break;

                case R.id.nav_loaiSach:
                    setTitle("Quản Lý Loại Sách");
                    loaiSachFragment loaiSachFragment = new loaiSachFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,loaiSachFragment).commit();
                    break;

                case R.id.sub_Logout:
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                    break;

                case R.id.nav_thanhVien:
                    setTitle("Quản Lý Thành Viên");
                    thanhVienFragment thanhVien_fragment = new thanhVienFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,thanhVien_fragment).commit();
                    break;

                case R.id.nav_top:
                    setTitle("Top 10 sách mượn nhiều nhất");
                    top10_Fragment sub_Top = new top10_Fragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,sub_Top).commit();
                    break;

                case R.id.nav_doanhThu:
                    setTitle("Doanh Thu");
                    doanhThuFragment doanhThuFragment = new doanhThuFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,doanhThuFragment).commit();
                    break;

                case R.id.nav_themNguoiDung:
                    setTitle("Thêm Thủ Thư");
                    themThanhVienFragment themThanhVienFragment = new themThanhVienFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,themThanhVienFragment).commit();
                    break;

                case R.id.nav_doiMatKhau:
                    setTitle("Đổi mật khẩu");
                    doiMatKhauFragment fragment = new doiMatKhauFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,fragment).commit();
                    break;


            }
            drawer.closeDrawers();
            return true;
        });

    }


    //nhấn vào icon menu thì mở ra
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}