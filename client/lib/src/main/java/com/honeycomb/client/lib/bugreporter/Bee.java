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

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.honeycomb.client.lib.bugreporter.activity.CrashActivity;
import com.honeycomb.client.lib.bugreporter.data.App;
import com.honeycomb.client.lib.bugreporter.sender.Sender;

public class Bee {
    private static String mEndPoint;
    private static Thread.UncaughtExceptionHandler mDefaultHandler;

    private static Context mContext;
    private static App mApp;
    private static Application mApplication;
    private static Configuration mConfiguration;
    private static Sender mSender;
    private static BeeUncaughtExceptionHandler mUncaughtExceptionHandler;
    private static Activity mActivity;

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
        registerActivityLifeCycle();

        mSender.sendAllLog();

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        mUncaughtExceptionHandler = new BeeUncaughtExceptionHandler(mConfiguration, mDefaultHandler, new Handler());

        while (true) {
            try {
                Looper.loop();
                Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
                throw new RuntimeException("Main thread loop unexpectedly exited");
            } catch (BackgroundThreadException bte) {
                // Background Thread Crash
                showCrashDisplayActivity(bte);
            } catch (Exception e) {
                // UI Crash
                showCrashDisplayActivity(e);
            }
        }
    }

    static void showCrashDisplayActivity(Throwable e) {
        Intent i = new Intent(mApplication, CrashActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("e", e);
        mApplication.startActivity(i);
    }

    private static void registerActivityLifeCycle() {
        mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                mActivity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static Activity getActivity() {
        return mActivity;
    }

    public static Context  getContext() {
        return mContext;
    }

    public static App getApp() {
        return mApp;
    }
}
