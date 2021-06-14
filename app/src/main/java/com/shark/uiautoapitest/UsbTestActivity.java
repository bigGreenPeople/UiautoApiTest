package com.shark.uiautoapitest;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Iterator;

public class UsbTestActivity extends AppCompatActivity {

    public final static String TAG = "SharkChilli";
    private UsbManager manager;
    private UsbDevice mUsbDevice;
    //USB设备
    private HashMap<String, UsbDevice> deviceList;
    //代表USB设备的一个接口
    private UsbInterface mInterface;
    private UsbDeviceConnection mDeviceConnection;

    //代表一个接口的某个节点的类:写数据节点
    private UsbEndpoint usbEpOut;
    //代表一个接口的某个节点的类:读数据节点
    private UsbEndpoint usbEpIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initUsbDevice() {
        //获取usb设备
        manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //获取列表
        deviceList = manager.getDeviceList();
        deviceList.forEach((key, usbDevice) -> {
            Log.i(TAG, "DeviceId=" + usbDevice.getDeviceId() + "   vid=" + usbDevice.getVendorId() + "  pid=" + usbDevice.getProductId());
            mUsbDevice = usbDevice;
        });

        //获取接口
        for (int i = 0; i < mUsbDevice.getInterfaceCount(); i++) {
            // 一般来说一个设备都是一个接口，你可以通过getInterfaceCount()查看接口的个数
            // 这个接口上有两个端点，分别对应OUT 和 IN
            mInterface = mUsbDevice.getInterface(i);
            break;
        }
        if (mInterface == null) {
            Log.i(TAG, "没有找到设备接口！");
            return;
        }

        //用UsbDeviceConnection 与 UsbInterface 进行端点设置和通讯
        if (mInterface.getEndpoint(0) != null) {
            usbEpIn = mInterface.getEndpoint(0);
        }
        if (mInterface.getEndpoint(1) != null) {
            usbEpOut = mInterface.getEndpoint(1);
        }

        if (manager.hasPermission(mUsbDevice)) {
            Log.i(TAG, "mUsbDevice 没有权限");
            return;
        }

        mDeviceConnection = manager.openDevice(mUsbDevice);
        if (mDeviceConnection == null) return;

        //通过claimInterface(UsbInterface,true)找到设备接口
        if (mDeviceConnection.claimInterface(mInterface, true)) {
            Log.i(TAG, "找到设备接口");
        } else {
            mDeviceConnection.close();
        }
    }

    public void sendToUsb(String content){

    }
}
