package com.example.all_app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private static final String TAG = "zlt";
    private List<AppInfo> appInfos = null;
    private DragRecyclerView mRecyclerView = null;
    private RecyclerView.Adapter mAdapter = null;

    private Handler mHandle = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("zlt", "deviceName = " + Build.MANUFACTURER);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_DENIED) {
            Log.d("zlt", "IEMI = " + getIMEI(this));
        }

        requestPermission();
        appInfos = new ArrayList<>();
        mRecyclerView = (DragRecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new AppAdapter(this, this, appInfos));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
        });

        new LoadingThread().start();
    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(this, "需要提供访问权限才能正常登陆!", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 10);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("zlt", "IEMI = " + getIMEI(this));
                }
                return;
            default:
                break;
        }
    }

    public static String getIMEI(Context context) {
        TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ts.getDeviceId();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public List<AppInfo> getAllPackages() {
        PackageManager packageManager = getPackageManager();

        List<AppInfo> myAppInfos = new ArrayList<AppInfo>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }
                AppInfo myAppInfo = new AppInfo();
                myAppInfo.setPackageName(packageInfo.packageName);
//                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
//                    continue;
//                }
                myAppInfo.setLabel(packageInfo.applicationInfo.loadLabel(packageManager));
                myAppInfo.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                myAppInfos.add(myAppInfo);
            }
        } catch (Exception e) {
            Log.e(TAG, "===============获取应用包信息失败");
        }
        return myAppInfos;
    }

    @Override
    public void onItemClick(AppInfo appInfo) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName());
        startActivity(intent);
    }

    private class LoadingThread extends Thread {
        @Override
        public void run() {
            super.run();
            List<AppInfo> list = getAllPackages();
            if (list != null) {
                appInfos.clear();
                appInfos.addAll(list);
                mHandle.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

}
