package com.example.tiemchuixe.view;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.example.tiemchuixe.R;
import com.example.tiemchuixe.controller.NhanVienDAO;
import com.example.tiemchuixe.model.NhanVien;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        1001
                );
            }
        }


        createNotificationChannel();


        String loggedInVaiTro = LoginActivity.getLoggedInVaiTro(this);
        if (loggedInVaiTro == null) {
            LoginActivity.logout(this);
            return;
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        int maNV = LoginActivity.getLoggedInMaNV(this);
        String vaiTro = LoginActivity.getLoggedInVaiTro(this);
        String tenHienThi = "";
        NhanVienDAO nhanVienDAO = new NhanVienDAO(this);
        nhanVienDAO.open();
        NhanVien nhanVien = nhanVienDAO.getNhanVienById(maNV);
        nhanVienDAO.close();
        if (nhanVien != null) {
            tenHienThi = nhanVien.getTenNhanVien();
        }

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Xin chào " + vaiTro + " " + tenHienThi + "!");


        LinearLayout layoutUserInfo = findViewById(R.id.layoutUserInfo);
        layoutUserInfo.removeAllViews();
        if (nhanVien != null) {
            addInfoRow(layoutUserInfo, "Mã NV", String.valueOf(nhanVien.getMaNV()));
            addInfoRow(layoutUserInfo, "Tên đăng nhập", nhanVien.getTenDangNhap());
            addInfoRow(layoutUserInfo, "Chức vụ", nhanVien.getVaiTro());
        }




        findViewById(R.id.btnCreateTicket).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateTicketActivity.class);
            startActivity(intent);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_employee) {
                startActivity(new Intent(this, EmployeeListActivity.class));
            } else if (id == R.id.nav_service) {
                startActivity(new Intent(this, ServiceListActivity.class));
            } else if (id == R.id.nav_create_ticket) {
                startActivity(new Intent(this, CreateTicketActivity.class));
            } else if (id == R.id.nav_list_ticket) {
                startActivity(new Intent(this, ListTicketActivity.class));
            } else if (id == R.id.nav_revenue) {
                startActivity(new Intent(this, RevenueActivity.class));
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(this, EmployeeInfoActivity.class);
                intent.putExtra("maNV", maNV);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                LoginActivity.logout(this);
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void addInfoRow(LinearLayout parent, String label, String value) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(label + ": " + value);
        tv.setTextSize(16f);
        parent.addView(tv);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "phieu_channel",
                    "Thông báo phiếu rửa xe",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}