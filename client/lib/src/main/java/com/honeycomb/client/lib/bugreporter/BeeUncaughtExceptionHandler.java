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

import android.os.Handler;

import com.honeycomb.client.lib.bugreporter.data.Device;
import com.honeycomb.client.lib.bugreporter.model.Message;
import com.honeycomb.client.lib.bugreporter.storage.Bottle;
import com.honeycomb.client.lib.bugreporter.util.ViewScreen;

public class BeeUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final String TAG = "BeeUncaughtExceptionHandler";

    private final Thread.UncaughtExceptionHandler mDefaultHandler;
    private final Handler mUiHandler;
    private Configuration mConfiguration;
    private boolean enabled;

    private static volatile boolean mCrashing = false;

    public BeeUncaughtExceptionHandler(Configuration configuration, Thread.UncaughtExceptionHandler handler, Handler uiHandler) {
        mConfiguration = configuration;
        mDefaultHandler = handler;
        mUiHandler = uiHandler;
        enabled = true;

        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, final Throwable e) {

        // Don't re-enter -- avoid infinite loops if crash-reporting crashes.
        if (mCrashing) return;
        mCrashing = true;

        Message message = new Message();
        message.app_version = Bee.getApp().getVersionCode();
        message.device = Device.getManufacture();
        message.contents = getStackTrace(e);
        message.os_version = Device.getOSAPiVersion() + "";
        message.model = Device.getModel();

        Bottle bottle = new Bottle(Bee.getContext());
        bottle.saveLog(message);

//        if (enabled) {
//            Reporter.postLog(message);
//        }

        /**
         * Take a picture on view
         */
        ViewScreen.capture(Bee.getActivity().getWindow().getDecorView().getRootView(), "test_screenshot.jpg");

//        try {
//            if (mConfiguration.isTermination()) {
//                /**
//                 * after shutdown vm, device consumes many time to dump error-log.
//                 */
//                if (mDefaultHandler != null) {
//                    mDefaultHandler.uncaughtException(t, e);
//                }
//                Handler handler = new UiHandler();
//                handler.sendEmptyMessage(0);
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        } finally {
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(10);
//        }

        /**
         * Send Background Excception
         */
        mUiHandler.post(new Runnable() {
            public void run() {
                throw new BackgroundThreadException(e);
            }
        });
    }

    private String getStackTrace(Throwable e) {
        String result ="";
        result += e.getMessage() + "\n";

        StackTraceElement[] lists = e.getStackTrace();
        for(StackTraceElement list: lists) {
            result += list.toString() + "\n";
        }

        return result;
    }
}
