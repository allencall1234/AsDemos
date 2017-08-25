package com.zlt.icode.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.zlt.icode.IcodeApplication;

/**
 * Created by 524202 on 2017/8/25.
 */

public class SharePreferenceUtil {

    private static SharedPreferences getSharedPreferences() {
        return IcodeApplication.getContext().getSharedPreferences("icode", Context.MODE_PRIVATE);
    }

    public static void setFixPatchVersion(int version) {
        getSharedPreferences().edit().putInt("current_fix_version", version).commit();
    }

    public static int getFixPatchVersion() {
        return getSharedPreferences().getInt("current_fix_version", 0);
    }
}
