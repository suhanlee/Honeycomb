package com.example.note.androidbugreporter.application;

import android.app.Application;

import com.honeycomb.client.lib.bugreporter.Bee;

/**
 * Created by suhani on 15. 10. 18..
 */
public class ExamApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * For emulator ( host computer address : 10.0.2.2 )
         */
        Bee.init(this, "http://10.0.2.2:3000");
    }
}
