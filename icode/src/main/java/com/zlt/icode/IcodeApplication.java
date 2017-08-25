package com.zlt.icode;

import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.zlt.update.UpdateManager;

import org.litepal.LitePalApplication;

/**
 * Created by 524202 on 2017/8/25.
 */

public class IcodeApplication extends LitePalApplication {

    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mPatchManager = new PatchManager(this);


        try {
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            mPatchManager.init(version);
        } catch (PackageManager.NameNotFoundException e) {
            mPatchManager.init("1.0");
            e.printStackTrace();
        }

        mPatchManager.loadPatch();

    }
}
