package com.shark.uiautoapitest;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class OvActivity extends AppCompatActivity {

    public final static String TAG = "SharkChilli";
    private Button ovButton;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ovButton = findViewById(R.id.ov);
        mTextView = findViewById(R.id.ov_text);

//        View decorView = this.getWindow().getDecorView();
        ovButton.setOnClickListener(v -> {
            Log.i(TAG, "mTextView: " + isViewVisible(mTextView));
            Log.i(TAG, "mTextView: " + (mTextView.hasWindowFocus() && mTextView.getVisibility() == View.VISIBLE && mTextView.isShown()));

        });
    }

    private boolean isViewVisible(View host) {
        // Make sure our host view is attached to a visible window.
        if (host.getWindowVisibility() == View.VISIBLE) {
            // An invisible predecessor or one with alpha zero means
            // that this view is not visible to the user.
            Object current = host;
            while (current instanceof View) {
                View view = (View) current;
                // We have attach info so this view is attached and there is no
                // need to check whether we reach to ViewRootImpl on the way up.
                if (view.getAlpha() <= 0 || view.getVisibility() != View.VISIBLE) {
                    return false;
                }
                current = view.getParent();
            }

            // Check if the view is visible in window.
            // host.getWindowVisibleDisplayFrame(mWindowRect);
            Rect visibleRect = new Rect();
            // Check if the view is entirely visible.
            if (!host.getLocalVisibleRect(visibleRect)) {
                return false;
            }

            return visibleRect.top == 0 && visibleRect.left == 0 &&
                    visibleRect.bottom == host.getHeight() && visibleRect.right == host.getWidth();
        }
        return false;
    }

}
