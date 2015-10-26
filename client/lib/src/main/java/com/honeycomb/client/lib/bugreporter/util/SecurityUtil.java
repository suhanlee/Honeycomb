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

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class SecurityUtil {
    private static final String ROOT_PATH = Environment.getExternalStorageDirectory() + "";

    private static String[] rootFilePath = new String[] {
            ROOT_PATH + "/system/bin/su",
            ROOT_PATH + "/system/xbin/su",
            ROOT_PATH + "/system/app/SuperUser.apk",
            ROOT_PATH + "/data/data/com.noshufou.android.su"
    };

    private static boolean isAvailableSu() {
        try {
            Runtime.getRuntime().exec("su");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isRooted() {
        if(isAvailableSu()) {
            return true;
        }

        if (checkRootingFiles(createFiles(rootFilePath))) {
            return true;
        }

        return false;
    }

    private static boolean checkRootingFiles(File[] files) {
        for (File file : files) {
            if (file != null && file.exists() && file.isFile()) {
                return true;
            }
        }
        return false;
    }

    private static File[] createFiles(String[] filePath) {
        File[] files = new File[filePath.length];
        for (int i = 0; i < filePath.length; i++) {
            files[i] = new File(filePath[i]);
        }
        return files;
    }
}
