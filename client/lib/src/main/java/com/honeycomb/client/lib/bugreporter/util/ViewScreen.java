/*
 * Copyright (C) 2015 Suhan Lee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.honeycomb.client.lib.bugreporter.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ViewScreen {

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
