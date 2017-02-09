package com.yan.campusbbs.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.HashSet;
import java.util.Set;

/**
 * SharedPreferences工具
 * Created by jensen on 11/19/15.
 */
public class SPUtils {

    public static final String SHARED_PREFERENCE = "shared_preference";
    public static final String SKIN_INDEX = "SkinIndex";

    private SPUtils() {
    }

    // StringSet
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void putStringSet(Context mContext, int mode, String name, String key, Set<String> values) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(Context mContext, int mode, String name, String key) {
        return getStringSet(mContext, mode, name, key, new HashSet<String>());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(Context mContext, int mode, String name, String key, Set<String> def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getStringSet(key, def);
    }

    // String
    public static void putString(Context mContext, int mode, String name, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context mContext, int mode, String name, String key) {
        return getString(mContext, mode, name, key, "");
    }

    public static String getString(Context mContext, int mode, String name, String key, String def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getString(key, def);
    }

    //long
    public static void putLong(Context mContext, int mode, String name, String key, long value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(Context mContext, int mode, String name, String key) {
        return getLong(mContext, mode, name, key, 0l);
    }

    public static long getLong(Context mContext, int mode, String name, String key, long def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getLong(key, def);
    }

    // float
    public static void putFloat(Context mContext, int mode, String name, String key, float value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(Context mContext, int mode, String name, String key) {
        return getFloat(mContext, mode, name, key, 0.0f);
    }

    public static float getFloat(Context mContext, int mode, String name, String key, float def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getFloat(key, def);
    }

    // int
    public static void putInt(Context mContext, int mode, String name, String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context mContext, int mode, String name, String key) {
        return getInt(mContext, mode, name, key, 0);
    }

    public static int getInt(Context mContext, int mode, String name, String key, int def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getInt(key, def);
    }

    // boolean
    public static void putBoolean(Context mContext, int mode, String name, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context mContext, int mode, String name, String key) {
        return getBoolean(mContext, mode, name, key, false);
    }

    public static boolean getBoolean(Context mContext, int mode, String name, String key, boolean def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getBoolean(key, def);
    }
}
