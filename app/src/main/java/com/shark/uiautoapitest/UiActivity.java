package com.shark.uiautoapitest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shark.context.ContextUtils;

import java.util.List;

public class UiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void printActivity(View view) {
//        List<Activity> runningActivitys = ContextUtils.getRunningActivitys();
//        Log.i("SharkChilli", "getTopActivity: " + ContextUtils.getTopActivity());

//        runningActivitys.forEach(activity -> {
//            Log.i("SharkChilli", "activity: " + activity);
//        });
    }
}
