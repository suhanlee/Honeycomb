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

import com.honeycomb.client.lib.bugreporter.data.Device;
import com.honeycomb.client.lib.bugreporter.file.Bottle;
import com.honeycomb.client.lib.bugreporter.model.Message;

public class ReporterUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final String TAG = "ReporterUncaughtExceptionHandler";

    private final Thread.UncaughtExceptionHandler mDefaultHandler;
    private final Reporter mReporter;
    private boolean enabled;

    public ReporterUncaughtExceptionHandler(Reporter reporter, Thread.UncaughtExceptionHandler handler) {
        mReporter = reporter;
        mDefaultHandler = handler;
        enabled = true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Message message = new Message();
        message.app_version = mReporter.getApp().getVersionCode();
        message.device = Device.getManufacture();
        message.contents = getStackTrace(e);
        message.os_version = Device.getOSAPiVersion() + "";
        message.model = Device.getModel();

        Bottle bottle = new Bottle(mReporter.getContext());
        bottle.saveLog(message);


//        if (enabled) {
//            Reporter.postLog(message);
//        }

        if (mReporter.isTermination()) {
            /**
             * after shutdown vm, device consumes many time to dump error-log.
             */
            mDefaultHandler.uncaughtException(t, e);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    private String getStackTrace(Throwable e) {
        String result ="";
        result += e.getMessage() + "\n";

        StackTraceElement[] lists = e.getCause().getStackTrace();
        for(StackTraceElement list: lists) {
            result += list.toString() + "\n";
        }

        return result;
    }



//    android.os.Process.killProcess(android.os.Process.myPid());
//    System.exit(10);
}
