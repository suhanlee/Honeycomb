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

package com.honeycomb.client.lib.bugreporter;

import android.app.Application;
import android.content.Context;

import com.honeycomb.client.lib.bugreporter.data.App;
import com.honeycomb.client.lib.bugreporter.sender.LogService;
import com.honeycomb.client.lib.bugreporter.sender.Sender;

import retrofit.RestAdapter;

public class Bee {
    private static String TAG = "Bee";

    private static String mEndPoint;
    private static RestAdapter mRestAdapter;
    private static LogService mService;
    private static Thread.UncaughtExceptionHandler mDefaultHandler;

    private static Context mContext;
    private static App mApp;
    private static Application mApplication;
    private static Configuration mConfiguration;
    private static Sender mSender;
    private static BeeUncaughtExceptionHandler mUncaughtExceptionHandler;

    public static void init(Application application, String endPoint) {
        mEndPoint = endPoint;
        init(application);
    }

    private static void init(Application application) {
        mApplication = application;
        mContext = application.getApplicationContext();
        mApp = new App(mContext);
        mConfiguration = new Configuration();
        mSender = new Sender(mContext, mEndPoint);
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        mUncaughtExceptionHandler = new BeeUncaughtExceptionHandler(mConfiguration, mDefaultHandler);

        mSender.sendAllLog();
    }

    public static Context  getContext() {
        return mContext;
    }

    public static App getApp() {
        return mApp;
    }
}
