package com.example.mobiletest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    TextView textView = null;

    String[] regs = {"service", "android", "com", "providers", "google"};
    String[] properties = {"ro.build.fingerprint", "qemu.sf.fake_camera",
            "ro.build.host", "ro.product.cpu.abi", "ro.kernel.qemu"};
    String[] commonApps = {
            "com.netease.cloudmusic",//网易云音乐
            "android.task.kugou",//酷狗音乐
            "com.tencent.mm",//微信
            "com.tencent.mobileqq",//qq
            "com.eg.android.AlipayGphone",//支付宝
            "com.baidu.BaiduMap",//百度地图
            "com.autonavi.minimap",//高德地图
            "com.snda.wifilocating",//万能钥匙
            "com.sina.weibo",//新浪微博
            "com.ss.android.article.news",//今日头条
            "com.taobao.taobao",//淘宝
            "com.jingdong.app.mall"//京东
    };
    Map<String, Integer> maps = new HashMap<>();

    List<String> sysAppList = null; //系统app列表
    List<String> thirdAppList = null;//第三方app列表

    int systemAppCount = 0;

    String mapMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);
        textView.setTextSize(14);
        textView.setPadding(16, 16, 16, 0);
        for (String var : properties) {
            textView.append(var + " = " + getProperties(this, var) + "\n\n");
        }

        getAllPackages();
        analysisSystemApp();

        for (String var : regs) {
            maps.remove(var);
        }

        if (maps.size() > 0) {
            int count = 0;
            Iterator iterator = maps.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (maps.get(key) >= 10) {
                    count++;
                }
            }

            if (count > 1) {
                textView.append("\n\n\n运行设备为真机!");
            } else {
                int value = 0;
                systemAppCount = sysAppList.size();
                //系统app数量大于100通常为真机,权重加0.3
                if (systemAppCount >= 120) {
                    value += 40;
                } else if (systemAppCount > 100) {
                    value += 30;
                } else if (systemAppCount > 80) {
                    value += 20;
                } else {
                    value += 10;
                }

                //摄像头配置信息值不为both通常为真机，权重加0.1
                if (!getProperties(this, properties[1]).contains("both")) {
                    value += 6;
                }

                //如果指纹参数不是以android开头的默认为真机,权重加0.1
                if (!getProperties(this, properties[0]).toLowerCase().startsWith("android")) {
                    value += 6;
                }

                //如果host不等于ubuntu,权重中0.1
                if (!getProperties(this, properties[2]).equals("ubuntu")) {
                    value += 6;
                }

                //如果ro.kernel.qemu值不为1(通常模拟器值为1),权重加0.1
                if (!getProperties(this, properties[4]).equals("1")) {
                    value += 6;
                }

                //如果电池电量不为0,权重加0.1
                if (getBatteryTemperature(this) > 0) {
                    value += 6;
                }

                //是否安装网易云音乐或者是酷狗音乐
                if (thirdAppList.contains(commonApps[0]) || thirdAppList.contains(commonApps[1])) {
                    value += 4;
                }

                //安装微信
                if (thirdAppList.contains(commonApps[2])) {
                    value += 3;
                }

                //安装qq
                if (thirdAppList.contains(commonApps[3])) {
                    value += 3;
                }

                //安装支付宝
                if (thirdAppList.contains(commonApps[4])) {
                    value += 4;
                }

                //安装百度地图或者高德地图
                if (thirdAppList.contains(commonApps[5]) || thirdAppList.contains(commonApps[6])) {
                    value += 4;
                }

                //安装万能钥匙
                if (thirdAppList.contains(commonApps[7])) {
                    value += 5;
                }

                //安装新浪微博
                if (thirdAppList.contains(commonApps[8])) {
                    value += 4;
                }

                //安装头条
                if (thirdAppList.contains(commonApps[9])) {
                    value += 4;
                }

                //安装京东
                if (thirdAppList.contains(commonApps[10])) {
                    value += 5;
                }

                //安装淘宝
                if (thirdAppList.contains(commonApps[11])) {
                    value += 4;
                }

                if (value > 75) {
                    textView.append("\n\n运行设备为真机!");
                } else {
                    textView.append("\n\n运行设备为模拟器!");
                    throw new IllegalArgumentException("运行设备为模拟器: value = " + value + ", " + mapMessage);
                }
            }

        } else {
            textView.append("\n\n运行设备为模拟器!");
            throw new IllegalArgumentException("运行设备为模拟器: " + mapMessage);
        }
    }

    private void analysisSystemApp() {
        maps.clear();

        for (String info : sysAppList) {
            String[] vars = info.split("\\.");
            for (String str : vars) {
                Integer value = maps.get(str);
                if (value != null) {
                    maps.put(str, value + 1);
                } else {
                    maps.put(str, 1);
                }
            }
        }

        Iterator iterator = maps.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (maps.get(key) <= 2) {
                iterator.remove();
                maps.remove(key);
            }
        }

        mapMessage = maps.toString();
        textView.append(mapMessage + "\n");
    }

    public void getAllPackages() {
        PackageManager packageManager = getPackageManager();

        sysAppList = new ArrayList<>();
        thirdAppList = new ArrayList<>();

        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    sysAppList.add(packageInfo.packageName);
                } else {
                    thirdAppList.add(packageInfo.packageName);
                }
            }
        } catch (Exception e) {
        }
    }

    public static String getProperties(Context context, String name) {
        Class<?> systemProperties = null;
        try {
            ClassLoader classLoader = context.getClassLoader();
            systemProperties = classLoader.loadClass("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class, String.class);
//            String result = (String) get.invoke(systemProperties, "ro.kernel.qemu", "");
            return (String) get.invoke(systemProperties, name, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //从电量对应的电池温度判断是否模拟器
    public int getBatteryTemperature(Context context) {
        IntentFilter intentFilter = new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = context.registerReceiver(null, intentFilter);

        int voltage = batteryStatusIntent.getIntExtra("voltage", 99999);
        int temperature = batteryStatusIntent.getIntExtra("temperature", 99999);
        Log.d("zlt", "电池温度:" + temperature + ",电量：" + voltage);
        return voltage;
    }

}
