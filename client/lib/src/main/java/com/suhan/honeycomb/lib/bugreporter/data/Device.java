/**
 The MIT License (MIT)

 Copyright (c) 2015 suhan lee

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.suhan.honeycomb.lib.bugreporter.data;

import android.os.Build;

public class Device {

    /**
        Log.i(TAG, "BOARD = " + Build.BOARD);
		Log.i(TAG, "BRAND = " + Build.BRAND);
		Log.i(TAG, "CPU_ABI = " + Build.CPU_ABI);
		Log.i(TAG, "DEVICE = " + Build.DEVICE);
		Log.i(TAG, "DISPLAY = " + Build.DISPLAY);
		Log.i(TAG, "FINGERPRINT = " + Build.FINGERPRINT);
		Log.i(TAG, "HOST = " + Build.HOST);
		Log.i(TAG, "ID = " + Build.ID);
		Log.i(TAG, "MANUFACTURER = " + Build.MANUFACTURER);
		Log.i(TAG, "MODEL = " + Build.MODEL);
		Log.i(TAG, "PRODUCT = " + Build.PRODUCT);
		Log.i(TAG, "TAGS = " + Build.TAGS);
		Log.i(TAG, "TYPE = " + Build.TYPE);
		Log.i(TAG, "USER = " + Build.USER);
		Log.i(TAG, "VERSION.RELEASE = " + Build.VERSION.RELEASE);
     */

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
}
