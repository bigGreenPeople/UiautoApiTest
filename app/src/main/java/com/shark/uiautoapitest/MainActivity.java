package com.shark.uiautoapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shark.context.ContextUtils;
import com.shark.signal.IRecvListener;
import com.shark.socket.JWebSocketClient;
import com.shark.socket.WebSocketMessage;
import com.shark.tools.ScreenShot;
import com.shark.view.ViewInfo;
import com.shark.view.ViewManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IRecvListener {
    private Button mButton;
    private Button jumpButton;
    private JWebSocketClient mJWebSocketClient;
    private EditText mEditText;

    public final static String TAG = "SharkChilli";
    private ContextUtils mContextUtils;
    private ViewManager mViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContextUtils = ContextUtils.getInstance(getClassLoader());
        mViewManager = ViewManager.getInstance(getClassLoader());

        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edit_test);
        mButton = findViewById(R.id.test);
        jumpButton = findViewById(R.id.test2);

//        WindowManager windowManager = getWindowManager();
//        WindowMetrics currentWindowMetrics = windowManager.getCurrentWindowMetrics();
//        connect("ws://192.168.124.7:9873");
        mButton.setOnClickListener(v -> {
//            if (mJWebSocketClient == null) {
//                Toast.makeText(MainActivity.this, "mJWebSocketClient is null", Toast.LENGTH_LONG).show();
//                return;
//            }
//
////            mJWebSocketClient.sendMessage("test111111111fffff");
//            byte[] activityScreenBytes = ScreenShot.getActivityScreenBytes(MainActivity.this);
//            WebSocketMessage imgMessage = WebSocketMessage.createImgMessage("myid007", activityScreenBytes);
//            mJWebSocketClient.send(activityScreenBytes);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder = alertDialogBuilder.setTitle("??????").setMessage("??????");

            alertDialogBuilder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("??????", new DialogInterface.OnClickListener() {//????????????
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Map<String, ViewInfo> activitysLayout = mViewManager.getActivitysLayout(mContextUtils.getRunningActivitys());
                    String activitysLayoutInfo = new Gson().toJson(activitysLayout);
                    activitysLayout.forEach((key, val) -> {
                        Log.i(TAG, key + " : " + val);

                    });
                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
            }).setNeutralButton("????????????", new DialogInterface.OnClickListener() {//??????????????????
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        ArrayList<View> windowViews = mViewManager.getWindowsView(MainActivity.this);
                        for (int j = 0; j < windowViews.size(); j++) {
                            byte[] activityScreenBytes = ScreenShot.getViewScreenBytes(windowViews.get(j));
                            mJWebSocketClient.send(activityScreenBytes);
//                            ScreenShot.shoot(mViews.get(j), "sdcard/window" + j + ".png");

                        }


                    } catch (Exception e) {
                        Log.e(TAG, "onCreate: ", e);
                    }
                    Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.setTitle("??????").setMessage("??????").create();
            alertDialog.show();

        });

        jumpButton.setOnClickListener(v -> {
//            Map<String, ViewInfo> activitysLayout = mViewManager.getActivitysLayout(mContextUtils.getRunningActivitys());
//            String activitysLayoutInfo = new Gson().toJson(activitysLayout);
//            WebSocketMessage textMessage = WebSocketMessage.createLayoutMessage("0", activitysLayoutInfo);
//            mJWebSocketClient.send(textMessage);
            Intent intent = new Intent(MainActivity.this, OvActivity.class);
            startActivity(intent);
        });
    }

    public void connect(View view) {
        String text = mEditText.getText().toString();
        connect(text);

    }

    private void connect(String text) {
        URI uri = URI.create(text);
        mJWebSocketClient = new JWebSocketClient(uri, this);

        if (mJWebSocketClient != null) {
            try {
                mJWebSocketClient.connectBlocking();
            } catch (Exception e) {
                Toast.makeText(this, "????????????!!!", Toast.LENGTH_LONG).show();
                throw new RuntimeException(e);
            }
        }

        if (mJWebSocketClient.isOpen()) {
            Toast.makeText(this, "????????????", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "????????????!!!", Toast.LENGTH_LONG).show();
//        if (!mJWebSocketClient.isClosed()) {
//            Toast.makeText(this, "????????????", Toast.LENGTH_LONG).show();
//        } else
//            Toast.makeText(this, "????????????!!!", Toast.LENGTH_LONG).show();
    }


    public void jumpUsb(View view) {
        Intent intent = new Intent(this, UsbTestActivity.class);
        startActivity(intent);
    }

    @Override
    public void recvMessage(String message) {
        WebSocketMessage webSocketMessage = new Gson().fromJson(message, WebSocketMessage.class);
        if (WebSocketMessage.Type.GET_LAYOUT_IMG.equals(webSocketMessage.getType())) {
            if (mJWebSocketClient == null) {
                Toast.makeText(MainActivity.this, "mJWebSocketClient is null", Toast.LENGTH_LONG).show();
                return;
            }

//            mContextUtils.getRunningActivitys().forEach(activity -> {
//                byte[] activityScreenBytes = ScreenShot.getActivityScreenBytes(activity);
//                mJWebSocketClient.send(activityScreenBytes);
//            });
            try {
                ArrayList<View> windowViews = mViewManager.getWindowsView(MainActivity.this);
                for (int j = 0; j < windowViews.size(); j++) {
                    byte[] activityScreenBytes = new byte[]{};

                    if (j==0){
                        activityScreenBytes = ScreenShot.getActivityScreenBytes(MainActivity.this);
                    }else {
                        activityScreenBytes = ScreenShot.getViewScreenBytes(windowViews.get(j));
                    }
                    mJWebSocketClient.send(activityScreenBytes);
//                            ScreenShot.shoot(mViews.get(j), "sdcard/window" + j + ".png");

                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: ", e);
            }
            // ????????????
            WebSocketMessage textMessage = WebSocketMessage.createMessage(WebSocketMessage.Type.GET_LAYOUT_IMG_END);
            mJWebSocketClient.send(textMessage);
        } else if (WebSocketMessage.Type.GET_LAYOUT.equals(webSocketMessage.getType())) {
            Map<String, ViewInfo> activitysLayout = mViewManager.getActivitysLayout(mContextUtils.getRunningActivitys());
            String activitysLayoutInfo = new Gson().toJson(activitysLayout);
            WebSocketMessage textMessage = WebSocketMessage.createLayoutMessage("0", activitysLayoutInfo);
            mJWebSocketClient.send(textMessage);
        }
    }
}