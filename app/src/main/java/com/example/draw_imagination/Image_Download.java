package com.example.draw_imagination;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Image_Download {

    // save image
    public void saveImage(ImageView imageView) {
        // file name : current time
        long time = System.currentTimeMillis();
        String imgName = String.valueOf(time);
        String fileName = imgName + ".png";
        // 저장소 경로
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/AppName");
        Log.i("info", "저장 경로 : " + storageDir);
        // 폴더가 없으면 생성
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File file = new File(storageDir, fileName);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(file);
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap imgBitmap = drawable.getBitmap();
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert output != null;
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
