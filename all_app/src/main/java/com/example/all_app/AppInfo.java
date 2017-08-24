package com.example.all_app;

import android.graphics.drawable.Drawable;

/**
 * Created by 524202 on 2017/8/14.
 */

public class AppInfo {
    private Drawable icon;
    private CharSequence label;
    private String packageName;

    public AppInfo(){}

    public AppInfo(String label, String packageName, Drawable icon) {
        this.icon = icon;
        this.label = label;
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public CharSequence getLabel() {
        return label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
