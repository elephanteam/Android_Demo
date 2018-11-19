package com.elephantgroup.one.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Field;
import java.util.Random;

public class Utils {

    /**
     * Access to program version number
     * */
    public static int getVersionCode(Context mContext) {
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()Is your current class package name. 0 is represented for version information
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 110;
    }

    /**
     * Access to program version name
     * */
    public static String getVersionName(Context mContext) {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    /**
     * Access to mobile phone IMEI number
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context mContext) {
        final TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            try {
                RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
                rxPermissions.request(Manifest.permission.READ_PHONE_STATE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return telephonyManager != null ? telephonyManager.getDeviceId() : null;
    }


    public static String getScreenPixels(Context mContext) {
        if (mContext == null) return "720*1280";
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int height = mContext.getResources().getDisplayMetrics().heightPixels;
        return String.valueOf(width).concat("*").concat(String.valueOf(height));
    }

    /**
     * by : cyt
     * @ param length key length less than or equal to 16
     * @ return String
     * @ TODO immediately generated specifies the length of the key
     */
    public static String makeRandomKey(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        if (length > 16) {
            length = 16;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * Hide the soft keyboard
     */
    public static void hiddenKeyBoard(Activity activity) {

        try {
            if (activity == null) return;
            // Cancel the pop-up dialog box
            InputMethodManager manager = (InputMethodManager) activity.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null && manager.isActive()) { //Only when the keyboard is in the state of the pop-up to hide by: KNothing
                if (activity.getCurrentFocus() == null) {
                    return;
                }
                manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Forced the pop-up
     */
    public static void showKeyBoard(EditText edit) {
        try {
            if (edit == null) return;
            edit.requestFocus();
            InputMethodManager m = (InputMethodManager) edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (m != null) {
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set TabLayout bottom line width
     * @ param tabs TabLayout
     * @ param leftDip distance from the left
     * @ param rightDip distance from the right
     * */
    public static void setIndicator (TabLayout tabs, int leftDip, int rightDip){
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }

}
