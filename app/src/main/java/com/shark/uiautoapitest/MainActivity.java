package com.shark.uiautoapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shark.context.ContextUtils;
import com.shark.socket.JWebSocketClient;
import com.shark.socket.WebSocketMessage;
import com.shark.tools.ScreenShot;
import com.shark.view.ViewInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private Button jumpButton;
    private JWebSocketClient mJWebSocketClient;
    private EditText mEditText;

    public final static String TAG = "SharkChilli";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextUtils instance = ContextUtils.getInstance(getClassLoader());
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edit_test);
        mButton = findViewById(R.id.test);
        jumpButton = findViewById(R.id.test2);

        mButton.setOnClickListener(v -> {
            if (mJWebSocketClient == null) {
                Toast.makeText(MainActivity.this, "mJWebSocketClient is null", Toast.LENGTH_LONG).show();
                return;
            }

//            mJWebSocketClient.sendMessage("test111111111fffff");
            byte[] activityScreenBytes = ScreenShot.getActivityScreenBytes(MainActivity.this);
            WebSocketMessage imgMessage = WebSocketMessage.createImgMessage("myid007", activityScreenBytes);
            mJWebSocketClient.send(activityScreenBytes);
        });


        jumpButton.setOnClickListener(v -> {
//            Intent intent = new Intent(this, UiActivity.class);
//            startActivity(intent);
            byte[] activityScreenBytes = ScreenShot.getActivityScreenBytes(MainActivity.this);
            String s = new String(activityScreenBytes);
            WebSocketMessage textMessage = WebSocketMessage.createTextMessage("myid007", "dsadsasad");
            mJWebSocketClient.send(textMessage);
        });
    }

    public void connect(View view) {
        String text = mEditText.getText().toString();
        URI uri = URI.create(text);
        mJWebSocketClient = new JWebSocketClient(uri);
        if (mJWebSocketClient != null) {
            try {
                mJWebSocketClient.connectBlocking();
            } catch (Exception e) {
                Toast.makeText(this, "连接失败!!!", Toast.LENGTH_LONG).show();
                throw new RuntimeException(e);
            }
        }

        if (mJWebSocketClient.isOpen()) {
            Toast.makeText(this, "连接成功", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "连接失败!!!", Toast.LENGTH_LONG).show();
//        if (!mJWebSocketClient.isClosed()) {
//            Toast.makeText(this, "连接成功", Toast.LENGTH_LONG).show();
//        } else
//            Toast.makeText(this, "连接失败!!!", Toast.LENGTH_LONG).show();

    }


    public ViewInfo getViewInfo(View view) {
        ViewInfo viewInfo = new ViewInfo();

        viewInfo.setId(view.getId());
        viewInfo.setClassName(view.getClass().getName());
        viewInfo.setEnabled(view.isEnabled());
        viewInfo.setShown(view.isShown());

        if (view instanceof TextView)
            viewInfo.setText(((TextView) view).getText().toString());

        if (view instanceof ViewGroup) {
            ArrayList<ViewInfo> childList = new ArrayList<>();

            ViewGroup vp = (ViewGroup) view;

            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                ViewInfo childViewInfo = getViewInfo(viewchild);
                childList.add(childViewInfo);
            }
            viewInfo.setChildList(childList);
        }

        return viewInfo;
    }

    public void jumpUsb(View view) {
        Intent intent = new Intent(this, UsbTestActivity.class);
        startActivity(intent);
    }
}