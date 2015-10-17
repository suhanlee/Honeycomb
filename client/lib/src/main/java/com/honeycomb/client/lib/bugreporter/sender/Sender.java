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
package com.honeycomb.client.lib.bugreporter.sender;

import android.content.Context;
import android.util.Log;

import com.honeycomb.client.lib.bugreporter.storage.Bottle;
import com.honeycomb.client.lib.bugreporter.model.Message;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Sender {
    private String TAG = "Sender";

    private Context mContext;
    private String mEndPoint;
    private RestAdapter mRestAdapter;
    private LogService mService;

    public Sender(Context context, String endPoint) {
        mContext = context;
        mEndPoint = endPoint;

        initAdapter();
    }

    private void initAdapter() {
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(mEndPoint)
                .build();
        mService = mRestAdapter.create(LogService.class);
    }

    public void sendLog(Message message) {
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

    public void sendAllLog() {
        Bottle bottle = new Bottle(mContext);
        List<Message> listMsg = bottle.loadPreviousLog();

        for (Message msg : listMsg) {
            sendLog(msg);
        }

        bottle.clearAll();
    }

}
