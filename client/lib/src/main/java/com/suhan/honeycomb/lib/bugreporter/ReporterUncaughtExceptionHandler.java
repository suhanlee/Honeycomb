package com.suhan.honeycomb.lib.bugreporter;

import com.suhan.honeycomb.lib.bugreporter.data.Device;
import com.suhan.honeycomb.lib.bugreporter.file.Bottle;
import com.suhan.honeycomb.lib.bugreporter.model.Message;

/**
 * Created by suhanlee on 15. 7. 25..
 */
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
        System.out.println(t);
        System.out.println(e);

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
            mDefaultHandler.uncaughtException(t, e);
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
