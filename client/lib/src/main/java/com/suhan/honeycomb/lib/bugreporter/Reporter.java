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

package com.suhan.honeycomb.lib.bugreporter;

import android.content.Context;
import android.util.Log;

import com.suhan.honeycomb.lib.bugreporter.data.App;
import com.suhan.honeycomb.lib.bugreporter.file.Bottle;
import com.suhan.honeycomb.lib.bugreporter.model.Message;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Reporter {
    private static String mEndPoint;
    private static Reporter mInstance;
    private static RestAdapter mRestAdapter;
    private static LogService mService;
    private static Thread.UncaughtExceptionHandler mDefaultHandler;
    private static String TAG = "Reporter";

    private boolean mCrashTermination;
    private Context mContext;
    private App mApp;

    public static Reporter getInstance() {
        if (mInstance == null) {
            mInstance = new Reporter();
        }
        return mInstance;
    }

    private Reporter() {
        mCrashTermination = true;
    }

    public static void init(Context context, String endPoint) {
        mEndPoint = endPoint;
        initAdapter();
        getInstance().setContext(context);
        getInstance().setCaughtHandler();
        getInstance().initApp(context);
        getInstance().sendPreviousLog();
    }

    private void sendPreviousLog() {
        Bottle bottle = new Bottle(mContext);
        List<Message> listMsg = bottle.loadPreviousLog();

        for (Message msg : listMsg) {
            Reporter.postLog(msg);
        }

        bottle.clearAll();
    }

    private void setCaughtHandler() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new ReporterUncaughtExceptionHandler(this, mDefaultHandler));
    }

    private static void initAdapter() {
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(mEndPoint)
                .build();
        mService = mRestAdapter.create(LogService.class);
    }

    public static void postLog(Message message) {
        mService.postLog(message, new Callback<Message>() {
            @Override
            public void success(Message message, Response response) {
                Log.i(TAG, "success" + response);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "failure" + error);
            }
        });
    }

    public void setTermination(boolean termination) {
        mCrashTermination = termination;
    }

    public boolean isTermination() {
        return mCrashTermination;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Context  getContext() {
        return mContext;
    }

    public void initApp(Context context) {
        mApp = new App(context);
    }

    public App getApp() {
        return mApp;
    }
}
