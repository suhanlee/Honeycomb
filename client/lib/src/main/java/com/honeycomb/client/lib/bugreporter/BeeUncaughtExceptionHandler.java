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
import com.honeycomb.client.lib.bugreporter.storage.Bottle;
import com.honeycomb.client.lib.bugreporter.model.Message;

public class BeeUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final String TAG = "BeeUncaughtExceptionHandler";

    private final Thread.UncaughtExceptionHandler mDefaultHandler;
    private Configuration mConfiguration;
    private boolean enabled;

    public BeeUncaughtExceptionHandler(Configuration configuration, Thread.UncaughtExceptionHandler handler) {
        mConfiguration = configuration;
        mDefaultHandler = handler;
        enabled = true;

        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
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

        if (mConfiguration.isTermination()) {
            /**
             * after shutdown vm, device consumes many time to dump error-log.
             */
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(t, e);
            }
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
}
