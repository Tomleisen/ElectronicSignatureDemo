package com.tom.electronicsignaturedemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Tomleisen on 2021/3/24.
 * Email : xy162162a@163.com
 * Operation : https://www.jianshu.com/p/c4f017603413
 */
public class SaveImgUtil {

    public static final String DIR = Environment.getExternalStorageDirectory() + File.separator + "SYApp" + File.separator + "Client" + File.separator + "signature/";

    public static void saveBitmapToSDCard(View view, SavePictureToLocalListener savePictureToLocalListener) {
        Bitmap bitmap = convertViewToBitmap(view);
        // 判断手机设备是否有SD卡
        boolean isHasSDCard = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (isHasSDCard) {
            try {
                // 获取SDCard指定目录下
                String sdCardDir = DIR;
                File dirFile = new File(sdCardDir);  //目录转化成文件夹
                if (!dirFile.exists()) {                //如果不存在，那就建立这个文件夹
                    dirFile.mkdirs();
                }
                //文件夹有啦，就可以保存图片啦
                String imageFileName = "电子签名" + ".jpg";
                File file = new File(sdCardDir, /*System.currentTimeMillis()*/imageFileName);// 在SDcard的目录下创建图片文,以当前时间为其命名
                String filePath = file.getAbsolutePath();
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                savePictureToLocalListener.saveSuccess(filePath);
            } catch (Exception e) {
                e.printStackTrace();
                savePictureToLocalListener.saveFailure("图片保存失败");
            }
        } else {
            savePictureToLocalListener.saveFailure("您的手机没有SD卡，无法保存图片");
        }
    }


    private static Bitmap convertViewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        //利用bitmap生成画布
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //把view中的内容绘制在画布上
        view.draw(canvas);
        return bitmap;
    }

    public interface SavePictureToLocalListener {
        void saveSuccess(String filePath);

        void saveFailure(String errMsg);
    }


    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
