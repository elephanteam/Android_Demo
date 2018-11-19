package com.elephantgroup.one;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.elephantgroup.one.entity.UserInfoVo;
import com.elephantgroup.one.language.MultiLanguageUtil;
import com.elephantgroup.one.util.CrashHandler;
import com.elephantgroup.one.util.SDCardCtrl;

public class MyApplication extends MultiDexApplication {

    public static Context mContext;
    public static UserInfoVo myInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        SDCardCtrl.initPath(this);
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }
}
