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

/**
 * Created by suhanlee on 15. 7. 25..
 */
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
