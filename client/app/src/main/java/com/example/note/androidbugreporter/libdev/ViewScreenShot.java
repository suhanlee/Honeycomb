package com.example.note.androidbugreporter.libdev;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by suhanlee on 15. 10. 20..
 */
public class ViewScreenShot {


    public static boolean capture(View view, String filename) {
        view.buildDrawingCache();
        Bitmap captureView = view.getDrawingCache();
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/" + filename);
            if (captureView != null) {
                captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
