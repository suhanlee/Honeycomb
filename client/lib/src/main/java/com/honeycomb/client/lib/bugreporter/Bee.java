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
