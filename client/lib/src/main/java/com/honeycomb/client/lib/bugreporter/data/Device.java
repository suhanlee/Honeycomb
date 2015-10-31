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
package com.honeycomb.client.lib.bugreporter.data;

import android.os.Build;

import com.honeycomb.client.lib.bugreporter.util.SecurityUtil;

public class Device {

    public static String getProduct() {
        return Build.PRODUCT;
    }

    public static String getBoard() {
        return Build.BOARD;
    }

    public static String getTAGS() {
        return Build.TAGS;
    }

    public static String getTYPE() {
        return Build.TYPE;
    }

    public static String getOSVersion() {
        String unknown = "UNKNOWN";

        switch (getOSAPiVersion()) {
            case 23:
                return "6.0";
            case 22:
                return "5.1";
            case 21:
                return "5.0";
            case 20:
                return "5.0-pre";
            case 19:
                return "4.4";
            case 18:
                return "4.3";
            case 17:
                return "4.2";
            case 16:
                return "4.1";
        }

        return unknown;
    }

    public static int getOSAPiVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getManufacture() {
        return Build.MANUFACTURER;
    }

    public static boolean isRooted() {
        return SecurityUtil.isRooted();
    }
}
