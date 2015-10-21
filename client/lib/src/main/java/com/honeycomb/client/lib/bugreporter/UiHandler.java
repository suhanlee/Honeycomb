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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class UiHandler extends Handler {
    public UiHandler() {
        super(UiThread.get().getLooper());
    }

    @Override
    public void handleMessage(Message msg) {
//        AlertDialog dialog  = createDialog("test", "hi");
//        dialog.show();
        Toast.makeText(Bee.getContext(), "Test", Toast.LENGTH_LONG).show();
    }

    private static AlertDialog createDialog(String title, String message) {
        AlertDialog.Builder ab = new AlertDialog.Builder(Bee.getContext().getApplicationContext());
        ab.setTitle(title);
        ab.setMessage(message);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return ab.create();
    }
}

