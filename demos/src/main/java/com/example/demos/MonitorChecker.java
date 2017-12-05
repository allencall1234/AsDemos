package com.example.demos;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 524202 on 2017/12/4.
 */

public class MonitorChecker {

    //从电量对应的电池温度判断是否模拟器
    public static boolean checkBattery(Context context) {
        IntentFilter intentFilter = new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = context.registerReceiver(null, intentFilter);

        int voltage = batteryStatusIntent.getIntExtra("voltage", 99999);
        int temperature = batteryStatusIntent.getIntExtra("temperature", 99999);
        Log.d("zlt", "电池温度:" + temperature + ",电量：" + voltage);
        if (((voltage == 0) && (temperature == 0))
                || ((voltage == 10000) && (temperature == 0))) {
            //这是通过电池的伏数和温度来判断是真机还是模拟器
            return true;
        }
        return false;
    }

    // 检测手机上的一些硬件信息
    public static Boolean CheckEmulatorBuild(Context context) {
        String BOARD = android.os.Build.BOARD;
        String BOOTLOADER = android.os.Build.BOOTLOADER;
        String BRAND = android.os.Build.BRAND;
        String DEVICE = android.os.Build.DEVICE;
        String HARDWARE = android.os.Build.HARDWARE;
        String MODEL = android.os.Build.MODEL;
        String PRODUCT = android.os.Build.PRODUCT;
        if (BOARD == "unknown" || BOOTLOADER == "unknown" || BRAND == "generic"
                || DEVICE == "generic" || MODEL == "sdk" || PRODUCT == "sdk"
                || HARDWARE == "goldfish") {
            Log.v("Result:", "Find Emulator by EmulatorBuild!");
            return true;
        }
        Log.v("Result:", "Not Find Emulator by EmulatorBuild!");
        return false;
    }

    public static boolean isMonitor(Context context) {
        Class<?> systemProperties = null;
        try {
            ClassLoader classLoader = context.getClassLoader();
            systemProperties = classLoader.loadClass("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class, String.class);
//            String result = (String) get.invoke(systemProperties, "ro.kernel.qemu", "");
            String result = (String) get.invoke(systemProperties, "ro.build.host", "");
            return result.equals("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
