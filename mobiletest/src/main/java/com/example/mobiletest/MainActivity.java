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
import android.widget.Scroller;
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
            "ro.build.host", "ro.product.cpu.abi", "ro.kernel.qemu",
            "ro.boot.serialno", "ro.board.platform"};
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

    int systemAppCount = 1;

    String mapMessage = "";

    double percentage = 1.0f;

    String scoreMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);
        textView.setTextSize(14);
        textView.setPadding(16, 16, 16, 0);


        /*
        for (String var : properties) {
            textView.append(var + " = " + getProperties(this, var) + "\n");
        }

        getAllPackages();
        analysisSystemApp();

        for (String var : regs) {
            maps.remove(var);
        }

//        if (maps.size() > 0) {
        int count = 0;
        Iterator iterator = maps.keySet().iterator();
        int maxVaule = 0;
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (maps.get(key) >= 10) {
                count++;
                if (maxVaule < maps.get(key)) {
                    maxVaule = maps.get(key);
                }
            }
        }

        if (count > 1) {
            textView.append("\n不评分了，运行设备一眼就知道是真机!");
        } else {
            int value = 0;
            systemAppCount = sysAppList.size();
            //系统app数量大于100通常为真机,权重加0.3
            if (systemAppCount > 150) {
                value += 50;
            } else if (systemAppCount >= 120) {
                value += 40;
            } else if (systemAppCount > 100) {
                value += 35;
            } else if (systemAppCount > 80) {
                value += 30;
            } else {
                value += 10;
            }

            scoreMessage = "\n系统应用评分：" + value;

            //当数量评分比例小于比例评分时，就需要调节数量评分值
            //但是不会超过40分
            percentage = maxVaule * 1.0f / systemAppCount;
            if (value < 120 * percentage && value < 40) {
                value = (int) (120 * percentage);
                if (value > 40) {
                    value = 40;
                }
                scoreMessage += "\n系统应用重新调整评分：" + value;
            }
            int tempScore = value;

            //摄像头配置信息值不为both通常为真机，权重加0.1
            if (!getProperties(this, properties[1]).contains("both")) {
                value += 10;
            }

            //如果指纹参数包含release-keys默认为真机,权重加0.1
            if (getProperties(this, properties[0]).contains("release-keys")) {
                value += 10;
            }

            //如果host不等于ubuntu,权重中0.1
            if (!getProperties(this, properties[2]).equals("ubuntu")) {
                value += 10;
            }

            //如果ro.kernel.qemu值不为1(通常模拟器值为1),权重加0.1
            if (!getProperties(this, properties[4]).equals("1")) {
                value += 10;
            }

            //如果电池电量不为0,权重加0.1
            if (getBatteryTemperature(this) > 0) {
                value += 10;
            }

            scoreMessage += "\n配置信息评分：" + (value - tempScore);
            tempScore = value;

            //是否安装网易云音乐或者是酷狗音乐
            if (thirdAppList.contains(commonApps[0]) || thirdAppList.contains(commonApps[1])) {
                value += 2;
            }

            //安装微信
            if (thirdAppList.contains(commonApps[2])) {
                value += 1;
            }

            //安装qq
            if (thirdAppList.contains(commonApps[3])) {
                value += 1;
            }

            //安装支付宝
            if (thirdAppList.contains(commonApps[4])) {
                value += 2;
            }

            //安装百度地图或者高德地图
            if (thirdAppList.contains(commonApps[5]) || thirdAppList.contains(commonApps[6])) {
                value += 3;
            }

            //安装万能钥匙
            if (thirdAppList.contains(commonApps[7])) {
                value += 4;
            }

            //安装新浪微博
            if (thirdAppList.contains(commonApps[8])) {
                value += 1;
            }

            //安装头条
            if (thirdAppList.contains(commonApps[9])) {
                value += 2;
            }

            //安装京东
            if (thirdAppList.contains(commonApps[10])) {
                value += 2;
            }

            //安装淘宝
            if (thirdAppList.contains(commonApps[11])) {
                value += 2;
            }

            scoreMessage += "\n常用应用评分：" + (value - tempScore);
            scoreMessage += "\n合计得分：" + value;

            if (value >= 80) {
                textView.append(scoreMessage);
                textView.append("\n运行设备为真机!");
            } else {
                textView.append(scoreMessage);
                textView.append("\n运行设备为模拟器!");
//                    throw new IllegalArgumentException("运行设备为模拟器: value = " + value + ", " + mapMessage);
            }
        }

//        } else {
//            textView.append("\n懒得评分，运行设备一看就知道是模拟器!");
//            throw new IllegalArgumentException("运行设备为模拟器: " + mapMessage);
//        }
*/


        MonitorUtils monitorUtils = new MonitorUtils(this);
        if (!monitorUtils.checkIfRunnungOnMobile()) {
            textView.append(monitorUtils.getScoreMessage());
        } else {
            textView.append("运行环境为真机!");
        }

        if (RootUtils.checkRootPathSU()) {
            RootUtils.checkRootWhichSU();
        }

        if (RootUtils.isAllowMockLocation(this)){
            Log.d("zlt","开启了模拟位置");
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
        return temperature;
    }

}
