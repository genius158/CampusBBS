package com.yan.campusbbs.utils;

import android.text.TextUtils;

/**
 * Created by yan on 2017/4/8.
 */

public class EmptyUtil {
    private EmptyUtil() {
    }

    public static String textEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    public static String textEmpty(Object str) {
        if (str == null || TextUtils.isEmpty(String.valueOf(str).trim())) {
            return "";
        } else {
            return String.valueOf(str);
        }
    }

    public static Object numObjectEmpty(Object obj) {
        if (obj != null) {
            return Float.valueOf(String.valueOf(obj)).intValue();
        } else {
            return "0";
        }
    }

    public static Float floatEmpty(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Float.parseFloat(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0f;
    }

}
