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
package com.honeycomb.client.lib.bugreporter.util;

import android.util.Log;

import com.honeycomb.client.lib.BuildConfig;

public class ALog {

    private static String TAG = "ALOG";
    private static int MAX_LENGTH = 2048;
    private static String PREFIX = "";

    private static String getPrefix(StackTraceElement callerElement) {
            return PREFIX + "[" +
                    callerElement.getFileName().replace(".java", "") + ":" +
                    callerElement.getMethodName() + ":" +
                    callerElement.getLineNumber() + "]";

    }

    private static String replaceLineFeeds(String str) {
        if(str == null || str.length() == 0) {
            return null;
        }

        String clean = str.replace('\n', '_').replace('\r', '_');

        if(str.equals(clean) == false) {
            clean += " (Modified)";
        }
        return clean;
    }

    private static void print(LogFunction log, Object... obj) {
        // only debug mode.
        if (!BuildConfig.DEBUG) {
            return;
        }

        Exception exception = new Exception();
        StackTraceElement callerElement = exception.getStackTrace()[1];
        String strString = obj.length == 0 ? "" : obj[0].toString();

        boolean isFirst = true;

        String clean = replaceLineFeeds( strString );

        while ( clean != null && clean.length() > 0 ) {
            String strCurrent = clean;
            if ( clean.length() > MAX_LENGTH) {
                strCurrent = clean.substring( 0, MAX_LENGTH);
            }

            if (isFirst) {
                log.p(getPrefix(callerElement) + strCurrent);
            }else{
                log.p(strCurrent);
            }

            if (clean.length() > MAX_LENGTH) {
                clean = clean.substring(MAX_LENGTH, clean.length() );
            }else{
                clean = null;
            }
            isFirst = false;
        }
    }

    public static void i(Object... obj) {
        print(new LogFunction() {
            @Override
            public void p(String str) {
                Log.i(TAG, str);
            }
        }, obj);
    }

    public static void w(Object... obj) {
        print(new LogFunction() {
            @Override
            public void p(String str) {
                Log.w(TAG, str);
            }
        }, obj);
    }

    public static void e(Object... obj) {
        print(new LogFunction() {
            @Override
            public void p(String str) {
                Log.e(TAG, str);
            }
        },  obj);
    }

    private interface LogFunction {
        void p(String str);
    }
}
