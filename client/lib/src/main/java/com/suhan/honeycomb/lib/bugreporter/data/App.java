package com.suhan.honeycomb.lib.bugreporter.data;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by suhan on 10/13/15.
 */
public class App {
    private final Context mContext;
    private final PackageManager mPackageManaer;
    private PackageInfo mPackageInfo;

    public App(Context context) {
        mContext = context;
        mPackageManaer = context.getPackageManager();
        try {
            mPackageInfo = mPackageManaer.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getVersionCode() {
        return mPackageInfo.versionCode + "";
    }

    public String getVersionName() {
        return mPackageInfo.versionName;
    }

    public String getSharedUserId() {
        return mPackageInfo.sharedUserId;
    }
}
