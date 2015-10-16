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
package com.honeycomb.client.lib.bugreporter.file;

import android.content.Context;
import android.util.Log;

import com.honeycomb.client.lib.bugreporter.model.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Bottle {
    private final String TAG = "Bottle";
    private final String SEPARATE = "/";

    private final File mFile;
    private final String mFilePath;

    public Bottle(Context context) {
        mFile = context.getFilesDir();
        mFilePath = mFile.getAbsolutePath() + SEPARATE;
    }

    public List<Message> loadPreviousLog() {
        List<Message> listMsg = new ArrayList<Message>();

        String[] list = getList(mFile);
        for (String elem : list) {
            String filePath = mFilePath + elem;
            Message msg = readLog(filePath);
            listMsg.add(msg);
        }
        return listMsg;
    }

    public void clearAll() {
//        String[] list = getList(mFile);
//        String dirPath = mFile.getAbsolutePath() + SEPARATE;
//        for (String elem : list) {
//            String filePath = dirPath + elem;
//            deleteLog(filePath);
//        }
    }

    private String[] getList(File dir) {
        if(dir != null && dir.exists()) {
            return dir.list();
        }
        return null;
    }

    private String getFileName() {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd-HH-mm-ss", Locale.getDefault() );
        Date currentTime = new Date ();
        return "Log_" + mSimpleDateFormat.format ( currentTime );
    }

    public void saveLog(Message msg) {
        FileOutputStream f = null;
        try {
            f = new FileOutputStream(mFilePath + getFileName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectOutput s = null;
        try {
            s = new ObjectOutputStream(f);
            s.writeObject(msg);
            s.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message readLog(String filePath) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInput s = null;
        Message msg = null;
        try {
            s = new ObjectInputStream(in);
            msg = (Message)s.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msg;
    }

    private void deleteLog(String filename) {
        File f = new File(filename);

        if (f.delete()) {
           Log.i(TAG, "delete file : success");
        } else {
           Log.e(TAG, "delete file : fail");
        }
    }
}
