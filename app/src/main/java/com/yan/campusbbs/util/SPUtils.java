package com.yan.campusbbs.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

/**
 * SharedPreferences工具
 * Created by jensen on 11/19/15.
 */
public class SPUtils {
    private final Context mContext;

    @Inject
    public SPUtils(Context context) {
        mContext = context;
    }

    // StringSet
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void putStringSet(int mode, String name, String key, Set<String> values) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(int mode, String name, String key) {
        return getStringSet(mode, name, key, new HashSet<String>());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(int mode, String name, String key, Set<String> def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getStringSet(key, def);
    }

    // String
    public void putString(int mode, String name, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(int mode, String name, String key) {
        return getString(mode, name, key, "");
    }

    public String getString(int mode, String name, String key, String def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getString(key, def);
    }

    //long
    public void putLong(int mode, String name, String key, long value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(int mode, String name, String key) {
        return getLong(mode, name, key, 0l);
    }

    public long getLong(int mode, String name, String key, long def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getLong(key, def);
    }

    // float
    public void putFloat(int mode, String name, String key, float value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(int mode, String name, String key) {
        return getFloat(mode, name, key, 0.0f);
    }

    public float getFloat(int mode, String name, String key, float def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getFloat(key, def);
    }

    // int
    public void putInt(int mode, String name, String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(int mode, String name, String key) {
        return getInt(mode, name, key, 0);
    }

    public int getInt(int mode, String name, String key, int def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getInt(key, def);
    }

    // boolean
    public void putBoolean(int mode, String name, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(int mode, String name, String key) {
        return getBoolean(mode, name, key, false);
    }

    public boolean getBoolean(int mode, String name, String key, boolean def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getBoolean(key, def);
    }
}
