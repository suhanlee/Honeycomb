package com.example.note.androidbugreporter;

import android.test.ActivityInstrumentationTestCase2;

import com.example.note.androidbugreporter.libdev.ViewScreenShot;
import com.honeycomb.client.lib.bugreporter.data.Device;

/**
 * Created by suhanlee on 15. 10. 20..
 */
public class MainActiivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mMainActivity;

    public MainActiivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
    }

    public void testGetScreenshot() {
        assertTrue(ViewScreenShot.capture(mMainActivity.getWindow().getDecorView().getRootView(), "test1.jpg"));
    }

    public void testIsRooted() {
        assertFalse(Device.isRooted());
    }

}
