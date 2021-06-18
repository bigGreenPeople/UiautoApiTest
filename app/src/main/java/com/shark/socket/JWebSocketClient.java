package com.shark.socket;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.shark.context.ContextUtils;
import com.shark.context.RunUiInterface;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClient extends WebSocketClient {
    public final static String TAG = "SharkChilli";
    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测
    private Handler mHandler;

    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (isClosed()) {
//                Log.i(TAG, "开启重连");
                reconnectWs();
            }
            //定时对长连接进行心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    //重连
                    Log.i(TAG, "reconnectWs.................... ");
                    reconnect();
                } catch (Exception e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        }.start();
    }

    public JWebSocketClient(URI serverUri) {
        super(serverUri);
        ContextUtils contextUtils = ContextUtils.getInstance();
        contextUtils.runOnUiThread(() -> {
            mHandler = new Handler();
        });
    }

    public void send(WebSocketMessage webSocketMessage) {
        String json = new Gson().toJson(webSocketMessage);
        Log.i(TAG, "send: " + json);
        this.send(json);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        Log.e(TAG, "onOpen()");
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
    }

    @Override
    public void onMessage(String message) {
        Log.e(TAG, "onMessage:" + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e(TAG, "onClose()");
    }

    public void sendMessage(String message) {
        if (!isClosed()) {
            this.send(message);
        }
    }

    public void closeConnect() {
        if (this.isOpen()) {
            this.close();
        }
    }

    @Override
    public void onError(Exception ex) {
        Log.e(TAG, "onError()", ex);
    }
}
