package com.landsoft.intentserviceappdownload;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.landsoft.intentserviceappdownload.Helper.Constants;
import com.landsoft.intentserviceappdownload.Service.MyIntentService;
import com.landsoft.intentserviceappdownload.Service.MyReceiver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnDownload;
    public static  TextView tvStartus;

    MyReceiver myReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();
        mapWidget();
        setClickButton();
    }

    // xin quyen doc ghi file  > android 5.
//    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
//    <uses-permission android:name="android.permission.INTERNET"/>
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        đăng lý BroadcastReceiver trong Activity.
//        khi activity còn mở thì còn broadcast receiver còn hoạt động
//        chúng ta có thể đăng ký trong AndroidManifest nó chạy khi cả ứng dụng đóng
        myReceiver = new MyReceiver();
        registerBroadcastReceiver(Constants.SET_ACTION);
    }
// phuong thức đăng ký
    private void registerBroadcastReceiver(String setAction) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(setAction);
        registerReceiver(myReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        huy dang ky broadcast receiver
        unregisterReceiver(myReceiver);

    }

    private void setClickButton() {
        btnDownload.setOnClickListener(this);
    }

    private void mapWidget() {
        btnDownload = findViewById(R.id.btn_download);
        tvStartus = findViewById(R.id.tv_status);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_download:
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                Bundle bundle = new Bundle(); // gửi 1 gói qua luon cho nhanh nhe ba con
                String url = "https://stackoverflow.com/questions/22627184/android-alert-dialog-from-inside-an-intent-service.html";
                bundle.putString(Constants.URL_DOWNLOAD,url);
                intent.putExtra(Constants.BUNDLE_MAINACTIVITY_MYINTENTSERVICE,bundle);
                startService(intent);
                tvStartus.setText("Dang download");
                break;
        }
    }
}
