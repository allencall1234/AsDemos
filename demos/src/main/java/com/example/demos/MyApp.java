package com.example.demos;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 524202 on 2017/12/4.
 */

public class MyApp extends Application {

    private int BatteryN;       //目前电量
    private int BatteryV;       //电池电压
    private double BatteryT;        //电池温度
    private String BatteryStatus;   //电池状态
    private String BatteryTemp;     //电池使用情况

    @Override
    public void onCreate() {
        super.onCreate();
        // 注册一个系统 BroadcastReceiver，作为访问电池计量之用这个不能直接在AndroidManifest.xml中注册
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Toast.makeText(this, MonitorChecker.isMonitor(this) ? "app在模拟器上运行" : "app在真机上运行", Toast.LENGTH_LONG).show();
    }

    /* 创建广播接收器 */
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            /*
             * 如果捕捉到的action是ACTION_BATTERY_CHANGED， 就运行onBatteryInfoReceiver()
             */
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                BatteryN = intent.getIntExtra("level", 0);    //目前电量
                BatteryV = intent.getIntExtra("voltage", 0);  //电池电压
                BatteryT = intent.getIntExtra("temperature", 0);  //电池温度

                switch (intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN)) {
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        BatteryStatus = "充电状态";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        BatteryStatus = "放电状态";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        BatteryStatus = "未充电";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        BatteryStatus = "充满电";
                        break;
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        BatteryStatus = "未知道状态";
                        break;
                }

                switch (intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        BatteryTemp = "未知错误";
                        break;
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        BatteryTemp = "状态良好";
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        BatteryTemp = "电池没有电";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        BatteryTemp = "电池电压过高";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        BatteryTemp = "电池过热";
                        break;
                }
                Log.d("zlt", "目前电量为" + BatteryN + "% --- " + BatteryStatus + "\n" + "电压为" + BatteryV + "mV --- " + BatteryTemp + "\n" + "温度为" + (BatteryT * 0.1) + "℃");
            }
        }
    };
}
