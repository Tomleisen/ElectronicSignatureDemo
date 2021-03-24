package com.tom.electronicsignaturedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {

    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageview = findViewById(R.id.iv);
        Intent intent = getIntent();
        if (intent != null) {
            String filePath = intent.getStringExtra("filePath");
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imageview.setImageBitmap(bitmap);
        }
    }
}