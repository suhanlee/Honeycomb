package com.suhan.honeycomb.lib.bugreporter.file;

import android.content.Context;
import android.util.Log;

import com.suhan.honeycomb.lib.bugreporter.model.Message;

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

/**
 * Created by suhan on 10/13/15.
 */
public class Bottle {
    private final String TAG = "Bottle";
    private final String SEPARATE = "/";

    private final Context mContext;
    private final File mFile;
    private final String mFilePath;

    public Bottle(Context context) {
        mContext = context;
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
        Log.i(TAG, list.toString());
        return listMsg;
    }

    public void clearAll() {
        String[] list = getList(mFile);
        String dirPath = mFile.getAbsolutePath() + SEPARATE;
        for (String elem : list) {
            String filePath = dirPath + elem;
            deleteLog(filePath);
        }
    }

    public String[] getList(File dir) {
        if(dir != null && dir.exists()) {
            return dir.list();
        }
        return null;
    }

    public String getFileName() {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd-HH-mm-ss", Locale.getDefault() );
        Date currentTime = new Date ();
        String filename = "Log_" + mSimpleDateFormat.format ( currentTime );
        return filename;
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            s.writeObject(msg);
            s.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message readLog(String filePath) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInput s = null;
        try {
            s = new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = null;
        try {
            msg = (Message)s.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msg;
    }

    public void deleteLog(String filename) {
        File f = new File(filename);

        if (f.delete()) {
           Log.i(TAG, "delete file : success");
        } else {
           Log.e(TAG, "delete file : fail");
        }
    }
}
