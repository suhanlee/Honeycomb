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
