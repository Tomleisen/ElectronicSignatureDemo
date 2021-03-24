package com.tom.electronicsignaturedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btClean, btSave;
    private SignatureView signatureView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signatureView = findViewById(R.id.signatureView);
        btClean = findViewById(R.id.btClean);
        btSave = findViewById(R.id.btSave);

        btClean.setOnClickListener(this);
        btSave.setOnClickListener(this);
        permissions();
    }

    //先定义
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private void permissions() {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        if (view == btClean) {
            signatureView.clear();
        } else if (view == btSave) {
            saveImage();
        }
    }


    private void saveImage() {
        if (signatureView != null) {
            SaveImgUtil.saveBitmapToSDCard(signatureView, new SaveImgUtil.SavePictureToLocalListener() {
                @Override
                public void saveSuccess(String filePath) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("filePath", filePath);
                            startActivity(intent);
                            if (signatureView != null) {
                                signatureView.clear();
                            }
//                            if (imageview != null) {
//                                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//                                //将Bitmap设置到imageview
//                                imageview.setImageBitmap(bitmap);
//                            }
                        }
                    }, 300);
                }

                @Override
                public void saveFailure(String errMsg) {
                    Toast.makeText(MainActivity.this, errMsg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}