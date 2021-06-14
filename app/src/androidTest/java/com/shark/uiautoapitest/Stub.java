package com.shark.uiautoapitest;

import android.app.Instrumentation;
import android.app.UiAutomation;
import android.util.Log;
import android.view.KeyEvent;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.uiautomator.UiDevice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Stub {
    private final String TAG = "SharkChilli";

    private UiDevice device;
    private UiAutomation uiAutomation;
    private Instrumentation mInstrumentation;

    @Before
    public void setUp() throws Exception {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        uiAutomation = mInstrumentation.getUiAutomation();
        device = UiDevice.getInstance(mInstrumentation);

        Log.i(TAG, "setUp");

            while (true){
                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "setUp: ", e);
                }
                Log.i(TAG, "输入 start");

                if (device.pressKeyCode(KeyEvent.KEYCODE_K)) {
                    Log.i(TAG, "输入完毕");
                }
            }

    }

    @Test
    public void testUIAutomatorStub() {
        Log.i(TAG, "testUIAutomatorStub 222222");
    }

    @After
    public void tearDown() {
        Log.i(TAG, "tearDown");
    }
}
