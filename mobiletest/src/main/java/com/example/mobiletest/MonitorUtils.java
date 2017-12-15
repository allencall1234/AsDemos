package com.example.mobiletest;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 524202 on 2017/12/6.
 */

public class MonitorUtils {

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
    private Map<String, Integer> maps = new HashMap<>();

    private List<String> sysAppList = null; //系统app列表
    private List<String> thirdAppList = null;//第三方app列表

    private double percentage = 1.0f;

    private String scoreMessage = "";

    private Context context;

    private int maxAvailableStringRepeatCount; //包名解析后有效字符串重复出现的最大次数
    private int maxAvailableStringCount;  //包名解析后出现次数大于10次的有效字符串数量

    public MonitorUtils(Context context) {
        this.context = context;
        sysAppList = new ArrayList<>();
        thirdAppList = new ArrayList<>();
    }

    /**
     * 检查app运行环境是否是真机
     *
     * @return
     */
    public boolean checkIfRunnungOnMobile() {

        initAppLists();

        if (sysAppList.size() > 0 && thirdAppList.size() > 0) {

            analysisSystemApp();

            if (maxAvailableStringCount <= 1) {
                int score = 0;
                int systemAppCount = sysAppList.size();
                //系统app数量大于100通常为真机,权重加0.3
                if (systemAppCount > 150) {
                    score += 50;
                } else if (systemAppCount >= 120) {
                    score += 40;
                } else if (systemAppCount > 100) {
                    score += 35;
                } else if (systemAppCount > 80) {
                    score += 30;
                } else {
                    score += 10;
                }

                scoreMessage = "\n系统应用评分：" + score;

                //当数量评分比例小于比例评分时，就需要调节数量评分值
                //但是不会超过40分
                percentage = maxAvailableStringRepeatCount * 1.0f / systemAppCount;
                if (score < 120 * percentage && score < 40) {
                    score = (int) (120 * percentage);
                    if (score > 40) {
                        score = 40;
                    }
                    scoreMessage += "\n系统应用重新调整评分：" + score;
                }
                int tempScore = score;

                //摄像头配置信息值不为both通常为真机，权重加0.1
                if (!getProperties(properties[1]).contains("both")) {
                    score += 10;
                }

                //如果指纹参数包含release-keys默认为真机,权重加0.1
                if (getProperties(properties[0]).contains("release-keys")) {
                    score += 10;
                }

                //如果host不等于ubuntu,权重中0.1
                if (!getProperties(properties[2]).equals("ubuntu")) {
                    score += 10;
                }

                //如果ro.kernel.qemu值不为1(通常模拟器值为1),权重加0.1
                if (!getProperties(properties[4]).equals("1")) {
                    score += 10;
                }

                //如果电池电量不为0,权重加0.1
                if (getBatteryTemperature() > 0) {
                    score += 10;
                }

                scoreMessage += "\n配置信息评分：" + (score - tempScore);
                tempScore = score;

                //是否安装网易云音乐或者是酷狗音乐
                if (thirdAppList.contains(commonApps[0]) || thirdAppList.contains(commonApps[1])) {
                    score += 2;
                }

                //安装微信
                if (thirdAppList.contains(commonApps[2])) {
                    score += 1;
                }

                //安装qq
                if (thirdAppList.contains(commonApps[3])) {
                    score += 1;
                }

                //安装支付宝
                if (thirdAppList.contains(commonApps[4])) {
                    score += 2;
                }

                //安装百度地图或者高德地图
                if (thirdAppList.contains(commonApps[5]) || thirdAppList.contains(commonApps[6])) {
                    score += 3;
                }

                //安装万能钥匙
                if (thirdAppList.contains(commonApps[7])) {
                    score += 4;
                }

                //安装新浪微博
                if (thirdAppList.contains(commonApps[8])) {
                    score += 1;
                }

                //安装头条
                if (thirdAppList.contains(commonApps[9])) {
                    score += 2;
                }

                //安装京东
                if (thirdAppList.contains(commonApps[10])) {
                    score += 2;
                }

                //安装淘宝
                if (thirdAppList.contains(commonApps[11])) {
                    score += 2;
                }

                scoreMessage += "\n常用应用评分：" + (score - tempScore);
                scoreMessage += "\n合计得分：" + score;

                if (score < 80) {
                    //评分小于80分默认为模拟器
                    return false;
                }
            }

        }
        return true;
    }


    public String getScoreMessage() {
        return scoreMessage;
    }

    /**
     * 解析系统应用包名
     */
    private void analysisSystemApp() {

        maps.clear();

        List<String> regsList = Arrays.asList(regs);
        for (String info : sysAppList) {
            //把包名分割并存储在maps中，比如com.text.app,分割就成了"com","text","app"
            String[] vars = info.split("\\.");
            for (String str : vars) {
                //过滤掉android常用字符串
                if (!regsList.contains(str)) {
                    Integer value = maps.get(str);
                    if (value != null) {
                        maps.put(str, value + 1);
                    } else {
                        maps.put(str, 1);
                    }
                }
            }
        }

        Iterator iterator = maps.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            int value = maps.get(key);
            if (value >= 10) {//直接过滤掉包名字段出现次数小于10的
                maxAvailableStringRepeatCount++;
                if (maxAvailableStringCount < value) {
                    maxAvailableStringCount = value;
                }
            }
        }
    }

    /**
     * 通过反射方法查询系统配置属性值
     *
     * @param name
     * @return
     */
    private String getProperties(String name) {
        Class<?> systemProperties = null;
        try {
            ClassLoader classLoader = context.getClassLoader();
            systemProperties = classLoader.loadClass("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class, String.class);
            return (String) get.invoke(systemProperties, name, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取系统app列表和第三方应用App列表
    private void initAppLists() {
        PackageManager packageManager = context.getPackageManager();
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
            e.printStackTrace();
        }
    }

    //从电量对应的电池温度判断是否模拟器
    private int getBatteryTemperature() {
        IntentFilter intentFilter = new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = context.registerReceiver(null, intentFilter);

        int temperature = batteryStatusIntent.getIntExtra("temperature", 99999);
        return temperature;
    }

}
