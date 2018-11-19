package com.elephantgroup.one.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.elephantgroup.one.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPrefsUtil {

    /*Storing user information*/
    public static final String FILE_USER = "userinfo";

    /*positioning*/
    public static final String KEY_LOCATION = "location";

    /*Personal information*/
    public static final String KEY_LOGIN_USERINFO = "user_userinfo";


    /*Global information is stored*/
    public static final String FILE_APPLICATION = "application";

    /**
     * Language is the key value
     * */
    public static final String KEY_SAVE_LANGUAGE = "key_save_language";


    /**
     * read the String
     * @ param fileName file name
     * @ param key key values
     * */
    public static String readString(Context context, String fileName, String key) {
        if (context == null) return "";
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void write(Context context, String fileName, String key, String value) {
        if (context == null)
            return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void writeLong(Context context, String fileName, final String key, final long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * write a Boolean switch value
     * @ param fileName file name
     * @ param key key values
     * @ param value content
     */
    public static void writeBoolean(Context context, String fileName, String key, boolean value) {
        if (context == null)
            return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * write int value
     * @ param fileName file name
     * @ param key key values
     * @ param value content
     */
    public static void writeInt(Context context, String fileName, final String key, final int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    /**
     * read the String
     * @ param fileName file name
     * @ param key key values
     * */
    public static long readLong(Context context, String fileName, String key) {
        if (context == null) return 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key,0);
    }

    /**
     * read an int value 1 by default
     * @ param fileName file name
     * @ param key key values
     */
    public static int readInt1(Context context, String fileName, String key) {
        if (context == null) return -1;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }

    /**
     * read an int value zero by default
     * @ param fileName file name
     * @ param key key values
     */
    public static int readInt(Context context, String fileName, String key) {
        if (context == null) return 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * read an int value zero by default
     * @ param fileName file name
     * @ param key key values
     */
    public static int readIntDefaultUsd(Context context, String fileName, String key) {
        if (context == null) return 1;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 1);
    }
    /**
     * read the Boolean value of true by default
     * @ param fileName file name
     * @ param key key values
     */
    @Deprecated
    public static boolean readBoolean(Context context, String fileName, String key) {
        if (context == null) return true;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    /**
     * read Boolean false by default
     * @ param fileName file name
     * @ param key key values
     */
    public static boolean readBooleanNormal(Context context, String fileName, String key) {
        if (context == null) return false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * delete key value
     */
    public static void removeCache(Context c, String fileName, String key) {
        if (c == null) return;
        SharedPreferences sharedPreferences = c.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clearUserInfo(Context context) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN_USERINFO, "");
        if (MyApplication.myInfo != null){
            String jsonString  = SharedPrefsUtil.readString(context, SharedPrefsUtil.FILE_USER, MyApplication.myInfo.getLocalId());
            try {
                JSONObject object = new JSONObject(jsonString);
                JSONObject oldObject = object.optJSONObject(MyApplication.myInfo.getLocalId());
                oldObject.put("token","");
                object.put(MyApplication.myInfo.getLocalId(),oldObject);
                SharedPrefsUtil.write(context, SharedPrefsUtil.FILE_USER, MyApplication.myInfo.getLocalId(),object.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.apply();
    }



    /**
     * Delete the key value
     */
    public static void remove(Context c, String key) {
        if (c == null) return;
        SharedPreferences sharedPreferences = c.getSharedPreferences(SharedPrefsUtil.FILE_USER, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


}
